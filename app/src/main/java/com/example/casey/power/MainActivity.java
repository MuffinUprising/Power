package com.example.casey.power;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    PowerReceiver mPowerReceiver;
    IntentFilter mDisconnectIntentFilter;
    IntentFilter mConnectIntentFilter;

    final static String CHARGER_CONNECTED = "Charger is ** connected **";
    final static String CHARGER_DISCONNECTED = "Charger is ** disconnected **";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPowerReceiver = new PowerReceiver();
        mDisconnectIntentFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        mConnectIntentFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);

        //Determine current state so can display appropriate message on start (or when device is rotated)
        TextView powerTV = (TextView)findViewById(R.id.power);

        //Need to create a filter for the type of Intents we'd like to receive
        IntentFilter batteryStatusFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //Don't need to specify a receiver - for sticky intents, anything can read them at any time
        //registerReceiver will return the Intent for the filter we specify; can read data from this Intent
        Intent batteryStatus = registerReceiver(null, batteryStatusFilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        if(status == BatteryManager.BATTERY_PLUGGED_AC) {
            powerTV.setText(CHARGER_CONNECTED);
        } else {
            powerTV.setText(CHARGER_DISCONNECTED);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Register receivers. One BroadcastReceiver will handle both types of events
        registerReceiver(mPowerReceiver, mDisconnectIntentFilter);
        registerReceiver(mPowerReceiver,mConnectIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Unregister receivers here - no point being notified if the Activity is paused
        //One call will unregister all broadcast receivers
        unregisterReceiver(mPowerReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
