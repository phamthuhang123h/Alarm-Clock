package com.example.alarmclock;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        boolean inRunning = false;
        String string = intent.getExtras().getString("extra");

        ActivityManager manager = (android.app.ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))

        Log.e("Toi Trong Receiver","Xin chao");
        String chuoi_string = intent.getExtras().getString("extra");
        Log.e("Ban Truyen Key", chuoi_string);

        Intent myIntent = new Intent(context,Music.class);
        myIntent.putExtra("extra",chuoi_string);
        context.startService(myIntent);
    }
}
