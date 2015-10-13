package com.example.casey.power;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

/**
 * Created by casey on 10/12/15.
 */
public class PowerReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        //Context should ne the current activity on the screen
        MainActivity activity = (MainActivity) context;

        TextView powerTV = (TextView)activity.findViewById(R.id.power);

        if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            powerTV.setText(MainActivity.CHARGER_DISCONNECTED);
        }
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            powerTV.setText(MainActivity.CHARGER_CONNECTED);
        }
    }
}
