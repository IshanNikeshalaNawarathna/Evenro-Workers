package com.evenro.evenroworkers.ui.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.evenro.evenroworkers.MainActivity;
import com.evenro.evenroworkers.ui.errorPage.OfflineModeActivity;


public class AirplaneModeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean b = intent.getBooleanExtra("state", false);
        if (b) {
            Toast.makeText(context, "Offline", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, OfflineModeActivity.class);
            context.startActivity(i);
        } else {
            Toast.makeText(context, "Online", Toast.LENGTH_SHORT).show();
            Intent io = new Intent(context, MainActivity.class);
            context.startActivity(io);
        }

    }
}
