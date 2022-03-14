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
        SharedPreferences timeSharedPreferences = getSharedPreferences("time", MODE_PRIVATE);
        SharedPreferences stepSharedPreferences = getSharedPreferences("step", MODE_PRIVATE);
        SharedPreferences prevTimeSharedPref = getSharedPreferences("prevTime", MODE_PRIVATE);
        SharedPreferences prevStepSharedPref = getSharedPreferences("prevStep", MODE_PRIVATE);
        String time = timeSharedPreferences.getString("time", "04:15");
        int step = stepSharedPreferences.getInt("step", 200);
        String oldTime = timeText.getText().toString();
        int oldStep = Integer.parseInt(stepText.getText().toString());
        if (prevTimeSharedPref.getString("prevTime", "").equals("")) {
            timeText.setText(time);
            stepText.setText(String.valueOf(step));
            prevTimeSharedPref.edit().putString("prevTime", time).commit();
            prevStepSharedPref.edit().putInt("prevStep", step).commit();
        } else if (formatSecond(prevTimeSharedPref.getString("prevTime", "")) > formatSecond(time) &&
                prevStepSharedPref.getInt("prevStep", 0) >= step) {
            timeText.setText(time);
            stepText.setText(String.valueOf(step));
            prevTimeSharedPref.edit().putString("prevTime", time).commit();
            prevStepSharedPref.edit().putInt("prevStep", step).commit();
        } else {
            timeText.setText(prevTimeSharedPref.getString("prevTime", ""));
            stepText.setText(String.valueOf(prevStepSharedPref.getInt("prevStep", 0)));
        }

    }




    public long formatSecond(String time) {
        long minute = Long.parseLong(time.substring(0, time.indexOf(':')));
        long second = 60 * minute + Long.parseLong(time.substring(time.indexOf(':') + 1));
        return second;
    }
}
