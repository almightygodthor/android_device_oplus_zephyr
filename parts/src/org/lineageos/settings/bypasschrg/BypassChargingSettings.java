package org.lineageos.settings.bypasschrg;

import android.os.Bundle;
import androidx.preference.PreferenceFragment;
import androidx.preference.TwoStatePreference;
import org.lineageos.settings.R;

public class BypassChargingSettings extends PreferenceFragment {
    private static final String KEY_BYPASS_CHARGING = "bypass_charging";
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.bypass_charging_settings, rootKey);
        BypassChargingController controller =
                BypassChargingController.getInstance(getContext());
        boolean supported = controller.isBypassChargingSupported();
        TwoStatePreference preference = findPreference(KEY_BYPASS_CHARGING);
        preference.setEnabled(supported);
        if (supported) {
            preference.setChecked(controller.isBypassChargingEnabled());
            preference.setOnPreferenceChangeListener((pref, newValue) -> {
                controller.toggleBypassCharging((boolean) newValue);
                return true;
            });
        } else {
            preference.setSummary(R.string.bypass_charging_unavailable);
        }
    }
}
