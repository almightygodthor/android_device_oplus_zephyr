package org.lineageos.settings.bypasschrg;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import org.lineageos.settings.utils.FileUtils;

public class BypassChargingController {
    private static final String TAG = "BypassChargingController";
    private static final String BYPASS_CHARGING_NODE =
            "/sys/class/oplus_chg/battery/mmi_charging_enable";
    private static final String BYPASS_CHARGING_ENABLED = "0";
    private static final String BYPASS_CHARGING_DISABLED = "1";
    private static final int MODE_AUTO = 1;
    private static final int MODE_LIMIT = 3;
    private static final int CC_LIMIT_MIN = 10;
    private static final int CC_LIMIT_MAX = 100;
    private static final int CC_LIMIT_DEF = 80;
    private static final String KEY_CHARGING_CONTROL_ENABLED =
            "charging_control_enabled";
    private static final String KEY_CHARGING_CONTROL_MODE =
            "charging_control_mode";
    private static final String KEY_CHARGING_CONTROL_LIMIT =
            "charging_control_charging_limit";
    private static BypassChargingController sInstance;
    private final ContentResolver mContentResolver;

    public static synchronized BypassChargingController getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BypassChargingController(context.getApplicationContext());
        }
        return sInstance;
    }

    private BypassChargingController(Context context) {
        mContentResolver = context.getContentResolver();
    }

    public boolean isBypassChargingSupported() {
        return FileUtils.isFileReadable(BYPASS_CHARGING_NODE);
    }

    public boolean isBypassChargingEnabled() {
        return BYPASS_CHARGING_ENABLED.equals(FileUtils.readOneLine(BYPASS_CHARGING_NODE));
    }

    public void toggleBypassCharging(boolean enable) {
        if (enable) {
            setChargingControlEnabled(true);
            setChargingControlMode(MODE_LIMIT);
            setChargingControlLimit(CC_LIMIT_MIN);
            writeToNode(BYPASS_CHARGING_ENABLED);
        } else {
            setChargingControlLimit(CC_LIMIT_DEF);
            setChargingControlMode(MODE_AUTO);
            setChargingControlEnabled(false);
            writeToNode(BYPASS_CHARGING_DISABLED);
        }
    }

    private boolean writeToNode(String value) {
        if (!FileUtils.writeLine(BYPASS_CHARGING_NODE, value)) {
            Log.e(TAG, "Failed to write bypass charging node");
            return false;
        }
        return true;
    }

    private void setChargingControlEnabled(boolean enabled) {
        Settings.System.putInt(mContentResolver, KEY_CHARGING_CONTROL_ENABLED, enabled ? 1 : 0);
    }

    private void setChargingControlMode(int mode) {
        Settings.System.putInt(mContentResolver, KEY_CHARGING_CONTROL_MODE, mode);
    }

    private void setChargingControlLimit(int limit) {
        if (limit >= CC_LIMIT_MIN && limit <= CC_LIMIT_MAX) {
            Settings.System.putInt(mContentResolver, KEY_CHARGING_CONTROL_LIMIT, limit);
        }
    }
}
