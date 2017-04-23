package com.example.user.budilnikapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_knknvox_budilnik,button_anjatvoxner,button_mek_angamya;
    AlarmRendering alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    void init(){
    alarm=new AlarmRendering();
        button_knknvox_budilnik=(Button) findViewById(R.id.repeating);
        button_knknvox_budilnik.setOnClickListener(this);
        button_mek_angamya=(Button) findViewById(R.id.One_Time);
        button_mek_angamya.setOnClickListener(this);
        button_anjatvoxner=(Button) findViewById(R.id.clean_All);
        button_anjatvoxner.setOnClickListener(this);
    }


    public void startRepeatingTimer(View view){
        Context context= this.getApplicationContext();
        if(alarm!=null){
            alarm.SetAlarm(context);
        }else{
            Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelRepeatingTimer(View view){
        Context context= this.getApplicationContext();
        if(alarm!=null){
            alarm.CancelAlarm(context);
        }else{
            Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void oneTimeAlarm(View view){
        Context context= this.getApplicationContext();
        if(alarm!=null){
            alarm.SetOneAlarm(context);
        }else{
            Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repeating:
                startRepeatingTimer(v);
                break;
            case R.id.clean_All:
                cancelRepeatingTimer(v);
                break;
            case R.id.One_Time:
                oneTimeAlarm(v);
                break;
        }
    }
}
