package com.example.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.button_play).setOnClickListener(v -> playGame());
        findViewById(R.id.button_about).setOnClickListener(v -> aboutApp());
        findViewById(R.id.button_exit).setOnClickListener(v -> exitApp());
        findViewById(R.id.button_settings).setOnClickListener(v -> settingsGame());
        SharedPreferences rememberSharedPref = getSharedPreferences("remember",MODE_PRIVATE);
        rememberSharedPref.edit().putInt("remember",0).commit();
    }

    private void aboutApp() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void exitApp() {
        finish();

    }


    private void playGame() {
        startActivity(new Intent(this, MainActivity.class));
    }


    private void settingsGame() {
        Intent intent = new Intent(this, RecordPage.class);
        startActivity(intent);

    }
}