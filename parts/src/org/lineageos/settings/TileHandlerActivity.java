package org.lineageos.settings;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.service.quicksettings.TileService;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import org.lineageos.settings.bypasschrg.BypassChargingActivity;
import org.lineageos.settings.bypasschrg.BypassChargingTile;

public final class TileHandlerActivity extends Activity {
    private static final String TAG = "TileHandlerActivity";
    private static final Map<String, Class<?>> TILE_ACTIVITY_MAP = new HashMap<>();
    static {
        TILE_ACTIVITY_MAP.put(BypassChargingTile.class.getName(), BypassChargingActivity.class);
    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        if (intent == null || !TileService.ACTION_QS_TILE_PREFERENCES.equals(intent.getAction())) {
            Log.e(TAG, "Invalid or null intent received");
            finish();
            return;
        }
        final ComponentName qsTile = intent.getParcelableExtra(Intent.EXTRA_COMPONENT_NAME);
        if (qsTile == null) {
            Log.e(TAG, "No QS tile component found in intent");
            finish();
            return;
        }
        final String qsName = qsTile.getClassName();
        final Intent targetIntent = new Intent();
        if (TILE_ACTIVITY_MAP.containsKey(qsName)) {
            targetIntent.setClass(this, TILE_ACTIVITY_MAP.get(qsName));
        } else {
            final String packageName = qsTile.getPackageName();
            if (packageName == null) {
                finish();
                return;
            }
            targetIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            targetIntent.setData(Uri.fromParts("package", packageName, null));
        }
        targetIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(targetIntent);
        finish();
    }
}
