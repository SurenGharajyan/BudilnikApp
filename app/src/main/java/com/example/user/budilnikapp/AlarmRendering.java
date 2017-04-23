package com.example.user.budilnikapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.util.Date;

public class AlarmRendering extends BroadcastReceiver {
    final public static String ONE_TIME="one_time";
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TAG");
        wakeLock.acquire();

        Bundle bundle=intent.getExtras();
        StringBuilder stringBuilder=new StringBuilder();

        if(bundle!=null && bundle.getBoolean(ONE_TIME, Boolean.FALSE)){
            stringBuilder.append("jamanak ");
        }
        Format format=new SimpleDateFormat("hh:mm:ss a");
        stringBuilder.append(format.format(new Date()));

        MediaPlayer mediaPlayer=MediaPlayer.create(context,R.raw.success);
        mediaPlayer.start();
        Toast.makeText(context, stringBuilder, Toast.LENGTH_LONG).show();
        wakeLock.release();
    }



    //5 varkyany mek krknutyun
    public void SetAlarm(Context context) {
        Intent intent=new Intent(context, AlarmRendering.class);
        intent.putExtra(ONE_TIME,Boolean.TRUE);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intent,0);
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),1000 * 5,pendingIntent);
    }


    //jnjir sax
    public void CancelAlarm(Context context) {
        Intent intent=new Intent(context,AlarmRendering.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intent,0);
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }


//mi angam
    public void SetOneAlarm(Context context) {
        Intent intent=new Intent(context,AlarmRendering.class);
        intent.putExtra(ONE_TIME,Boolean.TRUE);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intent,0);
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent);
    }

}
