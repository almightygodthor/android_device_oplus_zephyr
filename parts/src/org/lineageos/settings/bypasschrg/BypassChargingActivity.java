package org.lineageos.settings.bypasschrg;

import android.os.Bundle;
import android.view.MenuItem;
import com.android.settingslib.collapsingtoolbar.CollapsingToolbarBaseActivity;

public class BypassChargingActivity extends CollapsingToolbarBaseActivity {
    private static final String TAG_BYPASS_CHARGING_ACTIVITY = "bypass_charging_activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(
                com.android.settingslib.collapsingtoolbar.R.id.content_frame,
                new BypassChargingSettings(), TAG_BYPASS_CHARGING_ACTIVITY).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
