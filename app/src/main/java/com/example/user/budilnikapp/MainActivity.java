package com.example.user.budilnikapp;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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



    @Override
    public void onClick(View v) {
        Context context= this.getApplicationContext();
        switch (v.getId()) {
            case R.id.repeating:

                if(alarm!=null){
                    Log.d("tag","Mi qani angam");
                    alarm.SetAlarm(context);
                }else{
                    Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.clean_All:

                if(alarm!=null){
                    Log.d("tag","chexyal");
                    alarm.CancelAlarm(MainActivity.this);
                }else{
                    Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.One_Time:
                if(alarm!=null){
                    Log.d("tag","Mi angam");
                    alarm.SetOneAlarm(MainActivity.this);
                }else{
                    Toast.makeText(context,"Alarm is null", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
