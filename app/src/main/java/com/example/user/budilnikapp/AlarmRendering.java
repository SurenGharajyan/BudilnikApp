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
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AlarmRendering extends BroadcastReceiver {
    final public static String ONE_TIME="one_time";
    int TimerHour=0;
    int TimerMinuteInHour=0;
    boolean t=false;
    int TimerMinute=0;
    int l=0;
    MainActivity mainActivity=new MainActivity();
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TAG");
        wakeLock.acquire();

        Bundle bundle=intent.getExtras();
        StringBuilder stringBuilder=new StringBuilder();

        if(bundle!=null && bundle.getBoolean(ONE_TIME, Boolean.FALSE)){
            stringBuilder.append("Time has ");
        }
        Format format=new SimpleDateFormat("hh:mm:ss a");
        stringBuilder.append(format.format(new Date()));

        MediaPlayer mediaPlayer=MediaPlayer.create(context,R.raw.alarm);
        mediaPlayer.start();
        Toast.makeText(context, stringBuilder, Toast.LENGTH_LONG).show();
        wakeLock.release();
    }

    AlarmManager[] alarmManager = new AlarmManager[24];
    ArrayList<PendingIntent> intentArray = new ArrayList<>();
    //mi angam
    public void SetOneAlarm(Context context,int AdapterHour,int AdapterMinute) {

        Calendar c = Calendar.getInstance();
        int CalendarMinute = c.get(Calendar.MINUTE);
        int CalendarHour = c.get(Calendar.HOUR_OF_DAY);


            Intent intent = new Intent(context, AlarmRendering.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, l, intent, 0);
            alarmManager[l] = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            if (CalendarHour - AdapterHour < 0) {
                Toast.makeText(context, "Calendar.HOUR="+CalendarHour, Toast.LENGTH_SHORT).show();
                TimerHour = Math.abs(CalendarHour - AdapterHour);
            } else if (CalendarHour - AdapterHour > 0) {
                TimerHour = 24 - (CalendarHour - AdapterHour);
            } else if (CalendarHour == AdapterHour + 12) {
                TimerHour = 12;
            } else if (CalendarHour == AdapterHour) {
                if (CalendarMinute < AdapterMinute) {
                    TimerHour = 0;
                    TimerMinuteInHour = Math.abs(AdapterMinute - CalendarMinute);
                } else if (CalendarMinute > AdapterMinute) {
                    TimerHour = 23;
                    TimerMinuteInHour = 60 - (CalendarMinute - AdapterMinute);
                }
                t=true;
            }

    if (CalendarMinute < AdapterMinute) {
        TimerMinute = AdapterMinute-CalendarMinute;
    } else if (CalendarMinute > AdapterMinute) {
        if(TimerHour!=23) {
            TimerHour--;
            TimerMinute = 60 - (CalendarMinute - AdapterMinute);
        }
    } else if (CalendarMinute == AdapterMinute) {
        TimerMinute = 0;
    }


            if (t) {
                Toast.makeText(context, "Wait for=" + TimerHour + " hour and=" + TimerMinuteInHour + " minute", Toast.LENGTH_LONG).show();
                if (TimerHour == 0) {
                    TimerHour = 1;
                }
                if (TimerMinuteInHour == 0) {
                    TimerMinuteInHour = 1;
                }

                alarmManager[l].set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + TimerHour * TimerMinuteInHour * 60 * 1000, pendingIntent);
            } else {
                Toast.makeText(context, "Wait for=" + TimerHour + " hour and=" + TimerMinute + " minute", Toast.LENGTH_LONG).show();
                if (TimerHour == 0) {
                    TimerHour = 1;
                }
                if (TimerMinute == 0) {
                    TimerMinute = 1;
                }
                alarmManager[l].set(AlarmManager.ELAPSED_REALTIME_WAKEUP,//SystemClock.elapsedRealtime()+25*1000,pendingIntent);
                        SystemClock.elapsedRealtime() + TimerHour * TimerMinute * 60 * 1000, pendingIntent);
            }
            intentArray.add(pendingIntent);
        Toast.makeText(context, "intentArray,length="+intentArray.size(), Toast.LENGTH_SHORT).show();
        l++;
        }


    //5 varkyany mek krknutyun
    public void SetAlarm(Context context) {
            Intent intent=new Intent(context, AlarmRendering.class);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intent,0);
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime()+13*1000,13*1000,pendingIntent);
    }

    //Delete All
    protected void cancelAllAlarms(){
        Toast.makeText(mainActivity, "intentArray.size()="+intentArray.size(), Toast.LENGTH_SHORT).show();
        if(intentArray.size()>0){
            for(int i=0; i<intentArray.size(); i++){
                alarmManager[i].cancel(intentArray.get(i));
            }
            intentArray.clear();
            mainActivity.list_recyclerview.clear();
            mainActivity.adapterTimeSet.notifyDataSetChanged();
        }

    }

    //Delete Alarm With Index
    protected void CancelAlarmI(int position) {
        //alarmManager[position].cancel(intentArray.get(position));
        //intentArray.remove(position);
    }
}



