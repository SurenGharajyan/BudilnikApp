package com.example.user.budilnikapp;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_recurrent_alarm_clock, button_seceding, button_one_time;
    int myMinute = 0, myHour = 0;
    final int DIALOG_CONST=123;
    AlarmRendering alarm;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager rec_layoutManager;
    List<ResourceTimeSetText> list_recyclerview;
    final int SETVALUE = 123;
    AdapterTimeSet adapterTimeSet;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        recyclerview_realization();
    }

    //realization buttons and alarm
    private void init() {
        alarm = new AlarmRendering();
        //button_recurrent_alarm_clock = (Button) findViewById(R.id.repeating);
       // button_recurrent_alarm_clock.setOnClickListener(this);
        button_one_time = (Button) findViewById(R.id.One_Time);
        button_one_time.setOnClickListener(this);
        button_seceding = (Button) findViewById(R.id.clean_All);
        button_seceding.setOnClickListener(this);
        relativeLayout=(RelativeLayout) findViewById(R.id.activity_main);
    }

    //realization recycler view
    private void recyclerview_realization() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        rec_layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rec_layoutManager);
        recyclerView.setHasFixedSize(true);
        list_recyclerview = new ArrayList<>();
        adapterTimeSet = new AdapterTimeSet(list_recyclerview, this);
        recyclerView.setAdapter(adapterTimeSet);

    }


    //alarm OnClick
    @Override
    public void onClick(View v) {
        Context context = this.getApplicationContext();

        switch (v.getId()) {
            case R.id.clean_All:
                if (alarm != null) {
                    alarm.cancelAllAlarms();
                } else {
                    Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    private String construntTime(int hour, int minute) {
        return (String.valueOf(hour).length() > 1 ? hour : "0" + hour) + ":" + (String.valueOf(minute).length() > 1 ? minute : "0" + minute);
    }
    int hour;
    int minute;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETVALUE && resultCode == RESULT_OK) {
            hour = data.getIntExtra("KeyHour", 0);
            minute = data.getIntExtra("KeyMinute", 0);
            list_recyclerview.add(new ResourceTimeSetText(construntTime(hour, minute)));
            adapterTimeSet.notifyDataSetChanged();
        }

    }

     public int getHour(){
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    //Add-Delete menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_alarm:
                showDialog(DIALOG_CONST);
                break;
        }
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
          case DIALOG_CONST:
            TimePickerDialog TPD = new TimePickerDialog(this, myTimeSet, myMinute, myHour, true);
            return TPD;
        }
        return super.onCreateDialog(id);
    }


    TimePickerDialog.OnTimeSetListener myTimeSet = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myMinute = minute;
            myHour = hourOfDay;
            list_recyclerview.add(new ResourceTimeSetText(construntTime(myHour, myMinute)));
            adapterTimeSet.notifyDataSetChanged();
        }
    };

}