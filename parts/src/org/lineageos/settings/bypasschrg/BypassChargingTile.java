package org.lineageos.settings.bypasschrg;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import org.lineageos.settings.R;

public class BypassChargingTile extends TileService {
    private BypassChargingController mBypassController;
    private boolean mEnabled;
    @Override
    public void onCreate() {
        super.onCreate();
        mBypassController = BypassChargingController.getInstance(this);
    }
    @Override
    public void onStartListening() {
        mEnabled = mBypassController.isBypassChargingEnabled();
        updateTileState();
    }
    @Override
    public void onClick() {
        if (mEnabled == mBypassController.isBypassChargingEnabled()) {
            mEnabled = !mEnabled;
            updateTileState();
            mBypassController.toggleBypassCharging(mEnabled);
        }
    }
    private void updateTileState() {
        Tile tile = getQsTile();
        if (tile == null) return;
        tile.setState(mEnabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.setLabel(getString(R.string.bypass_charging_title));
        tile.setContentDescription(getString(R.string.bypass_charging_summary));
        tile.updateTile();
    }
}
