package com.example.task2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecordPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_custom_dialog);
        TextView timeText = findViewById(R.id.record_time);
        TextView stepText = findViewById(R.id.record_step);
        SharedPreferences timeSharedPreferences = getSharedPreferences("time",MODE_PRIVATE);
        SharedPreferences stepSharedPreferences = getSharedPreferences("step",MODE_PRIVATE);
        SharedPreferences prevTimeSharedPref  = getSharedPreferences("prevTime",MODE_PRIVATE);
        SharedPreferences prevStepSharedPref = getSharedPreferences("prevStep",MODE_PRIVATE);
        String time = timeSharedPreferences.getString("time","04:15");
        int step = stepSharedPreferences.getInt("step",200);
        String oldTime = timeText.getText().toString();
        int oldStep = Integer.parseInt(stepText.getText().toString());
        if (prevTimeSharedPref.getString("prevTime","").equals("")){
            timeText.setText(time);
            stepText.setText(String.valueOf(step));
            prevTimeSharedPref.edit().putString("prevTime",time).commit();
            prevStepSharedPref.edit().putInt("prevStep",step).commit();
        }
        else if(formatSecond(prevTimeSharedPref.getString("prevTime",""))>formatSecond(time)&&
        prevStepSharedPref.getInt("prevStep",0)>=step){
            timeText.setText(time);
            stepText.setText(String.valueOf(step));
            prevTimeSharedPref.edit().putString("prevTime",time).commit();
            prevStepSharedPref.edit().putInt("prevStep",step).commit();
        }

        else{
            timeText.setText(prevTimeSharedPref.getString("prevTime",""));
            stepText.setText(String.valueOf(prevStepSharedPref.getInt("prevStep",0)));
        }

        /*
        1- time : 04:15 , step 200
        2- time : 00:02 , step 3
        3-

         **/

//        String str = "old time : "+oldTime+" step : "+oldStep+"\ntime : "+time+" step : "+step;
//        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
//        prevTimeSharedPref.edit().putString("prevTime",time).commit();
//        prevStepSharedPref.edit().putInt("prevStep",step).commit();
    }



    public String timeFormat(long secondTime) {
        long minute = (long) (secondTime / 60);
        long second = secondTime % 60;
        String minuteFormat = String.valueOf(minute);
        String secondFormat = String.valueOf(second);
        if (minute < 10) {
            minuteFormat = "0" + minute;
        }

        if (second < 10) {
            secondFormat = "0" + second;
        }

        return minuteFormat + ":" + secondFormat;

    }

    public long formatSecond(String time){
        long minute =Long.parseLong(time.substring(0,time.indexOf(':')));
        long second = 60*minute+Long.parseLong(time.substring(time.indexOf(':')+1));
        return second;
    }
}
