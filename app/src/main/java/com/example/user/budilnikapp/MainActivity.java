package com.example.user.budilnikapp;

import android.app.AlarmManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_recurrent_alarm_clock,button_seceding,button_one_time;
    int l=0;
    AlarmRendering alarm;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager rec_layoutManager;
    SharedPreferences SPref;
    List<Resource_Time_Set_Text> list_recyclerview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        recyclerview_realization();

    }
//realization buttons and alarm
    private void init(){
        alarm=new AlarmRendering();
        button_recurrent_alarm_clock=(Button) findViewById(R.id.repeating);
        button_recurrent_alarm_clock.setOnClickListener(this);
        button_one_time=(Button) findViewById(R.id.One_Time);
        button_one_time.setOnClickListener(this);
        button_seceding=(Button) findViewById(R.id.clean_All);
        button_seceding.setOnClickListener(this);
    }

    //realization recycler view


    private void recyclerview_realization() {
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        rec_layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rec_layoutManager);
        recyclerView.setHasFixedSize(true);
        list_recyclerview=new ArrayList<>();

    }

    // method set list_recyclerview items
    @Override
    protected void onResume() {
        super.onResume();
        l++;
        if(l>1) {
            if(getSharedPreferenceMinute() < 10 && getSharedPreferenceHours() < 10) {
                list_recyclerview.add(new Resource_Time_Set_Text("0"+getSharedPreferenceHours() + ":0" + getSharedPreferenceMinute()));
            } else if (getSharedPreferenceHours() < 10) {
                list_recyclerview.add(new Resource_Time_Set_Text("0"+getSharedPreferenceHours() + ":" + getSharedPreferenceMinute()));
            } else if (getSharedPreferenceMinute() < 10) {
                list_recyclerview.add(new Resource_Time_Set_Text(getSharedPreferenceHours() + ":0" + getSharedPreferenceMinute()));
            } else {
                list_recyclerview.add(new Resource_Time_Set_Text(getSharedPreferenceHours() + ":" + getSharedPreferenceMinute()));
            }
        }
        AdapterTimeSet adapterTimeSet=new AdapterTimeSet(list_recyclerview,this);
        recyclerView.setAdapter(adapterTimeSet);
    }

    int getSharedPreferenceHours() {
        SPref= PreferenceManager.getDefaultSharedPreferences(this);
        int hour=SPref.getInt("Key_hour",0);
        return hour;
    }

    int getSharedPreferenceMinute() {
        SPref=PreferenceManager.getDefaultSharedPreferences(this);
        int minute=SPref.getInt("Key_minute",0);
        return minute;
    }

    //alarm OnClick
    @Override
    public void onClick(View v) {
        Context context= this.getApplicationContext();

        switch (v.getId()) {
            case R.id.repeating:

                if(alarm!=null){
                    //Log.d("tag","Mi qani angam");
                    alarm.SetAlarm(context);
                }else{
                    Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.clean_All:

                if(alarm!=null){
                    //Log.d("tag","chexyal");
                    alarm.CancelAlarm(MainActivity.this);
                }else{
                    Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.One_Time:
                if(alarm!=null){
                    //Log.d("tag","Mi angam");
                    //alarm.SetOneAlarm(MainActivity.this);
                    alarm.setTimeRecyclerView(context);
                }else{
                    Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }




//Add-Delete menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_delete,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_alarm:
                final AlertDialog alertDialog= new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Do you want create alarm?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(MainActivity.this,TimeSetActivity.class);
                    startActivity(intent);
                }
            });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
            case R.id.delete_alarm:
                list_recyclerview.remove(list_recyclerview.size()-1);
                break;
        }
        return false;
    }

}