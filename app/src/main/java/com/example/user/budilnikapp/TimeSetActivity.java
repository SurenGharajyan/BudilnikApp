package com.example.user.budilnikapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class TimeSetActivity extends AppCompatActivity {
    TimePicker timePicker;
    Button button_finish;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_set);
        timePicker=(TimePicker) findViewById(R.id.timepicker);
        button_finish=(Button) findViewById(R.id.btn_finish);
        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInformation();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
    private void AddInformation(){
        sPref= PreferenceManager.getDefaultSharedPreferences(TimeSetActivity.this);
        SharedPreferences.Editor editor=sPref.edit();
        editor.putInt("Key_hour", timePicker.getHour());
        editor.putInt("Key_minute", timePicker.getMinute());
        editor.apply();

    }

}
