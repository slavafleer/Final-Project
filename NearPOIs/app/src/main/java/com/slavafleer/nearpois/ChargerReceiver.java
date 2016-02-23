package com.slavafleer.nearpois;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Broadcast receiver that showing message to user when device
 * connected or disconnected from a charger.
 */
public class ChargerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, "Power is connected.", Toast.LENGTH_SHORT).show();

        } else if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Toast.makeText(context, "Power is disconnected.", Toast.LENGTH_SHORT).show();
        }
    }
}
