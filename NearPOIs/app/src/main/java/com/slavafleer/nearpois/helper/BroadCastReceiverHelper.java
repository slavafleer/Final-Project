package com.slavafleer.nearpois.helper;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.slavafleer.nearpois.ChargerReceiver;

/**
 * Helper for toggling ChargerReceiver
 */
public class BroadCastReceiverHelper {

    private static ChargerReceiver chargerReceiver;

    public static void toggleChargerReceiver(Context context) {

        // Register the broadcast receiver:
        if (chargerReceiver == null) {

            // Create an intent filter for describing the needed broadcast receiver:
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
            intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

            // Create the broadcast receiver object:
            chargerReceiver = new ChargerReceiver();

            // Register the broadcast receiver:
            context.registerReceiver(chargerReceiver, intentFilter);

        } else { // Un register the broadcast receiver.
            context.unregisterReceiver(chargerReceiver);
            chargerReceiver = null;
        }
    }
}
