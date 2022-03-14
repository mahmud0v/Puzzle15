package com.example.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private Coordinate empty;
    private Button[][] buttons;
    private ArrayList<String> numbers;
    private TextView textScore;
    private MediaPlayer mediaPlayer;
    private SharedPreferences sharedPreferences;
    private int score = 0;
    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();
        initNumbers();
        loadNumbers();
        mediaPlayer = MediaPlayer.create(this, R.raw.relax_music);
        sharedPreferences = getSharedPreferences("time", MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("step", MODE_PRIVATE);


    }

    private void initButtons() {
        ViewGroup group = findViewById(R.id.container);
        textScore = findViewById(R.id.textScore);
        chronometer = findViewById(R.id.chronometer);
        int count = group.getChildCount();
        buttons = new Button[4][4];
        empty = new Coordinate(3, 3);
        for (int i = 0; i < count; i++) {
            Button button = (Button) group.getChildAt(i);
            int x = i / 4;
            int y = i % 4;
            buttons[x][y] = button;
            Coordinate coordinate = new Coordinate(x, y);
            button.setTag(coordinate);
            button.setOnClickListener(v -> clickGameItem(button));
        }
    }

    private void initNumbers() {
        numbers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            numbers.add(String.valueOf(i));
        }
    }

    private void loadNumbers() {
        Collections.shuffle(numbers);
        for (int i = 0; i < 15; i++) {
            int x = i / 4;
            int y = i % 4;
            Button button = buttons[x][y];
            button.setText(numbers.get(i));
            button.setBackgroundResource(R.drawable.bg_button);

        }

        setDefaultValues();
    }

    private void setDefaultValues() {
        empty.setX(3);
        empty.setY(3);
        buttons[3][3].setBackgroundResource(R.drawable.empty_item);
        buttons[3][3].setText("");
        textScore.setText("0");
        score = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private long pauseTime;
    private long beginTime;

    private void clickGameItem(Button button) {
        Coordinate c = (Coordinate) button.getTag();
        int dx = abs(empty.getX() - c.getX());
        int dy = abs(empty.getY() - c.getY());
        if (dx + dy == 1) {
            score++;
            Button emptyButton = buttons[empty.getX()][empty.getY()];
            emptyButton.setText(button.getText());
            emptyButton.setBackgroundResource(R.drawable.bg_button);
            button.setBackgroundResource(R.drawable.empty_item);
            button.setText("");
            empty.setX(c.getX());
            empty.setY(c.getY());
            textScore.setText(String.valueOf(score));
        }
        winGame();
    }

    public void clickRestart(View view) {
        loadNumbers();
        winGame();
    }

    public void winGame() {
        int winNum = 0;
        for (int i = 0; i < 15; i++) {
            if (buttons[i / 4][i % 4].getText().toString().equals(String.valueOf(i + 1))) {
                winNum++;
            }
        }
        if (winNum == 15) {
            Toast.makeText(this, "Yutdingiz, mazzami:)", Toast.LENGTH_SHORT).show();
            saveDB(chronometer.getText().toString(), score);
            onStop();
        }
    }

    public void clickFinish(View view) {
        mediaPlayer.stop();
        finish();
    }

    public void saveDB(String time, int score) {
        getSharedPreferences("time", MODE_PRIVATE).edit().putString("time", time).commit();
        getSharedPreferences("step", MODE_PRIVATE).edit().putInt("step", score).commit();

    }


    @Override
    protected void onStop() {
        super.onStop();
        pauseTime = chronometer.getBase() - SystemClock.elapsedRealtime();
        chronometer.stop();
        mediaPlayer.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (pauseTime != 0) {
            chronometer.setBase(pauseTime + SystemClock.elapsedRealtime());
            chronometer.start();
        }
    }

    public void mediaPlayer(View v) {
        number++;

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        ImageView imageView = (ImageView) v;
        if (number % 2 == 1) {
            mediaPlayer.start();
            imageView.setImageResource(R.drawable.ic_volume);
        } else {
            mediaPlayer.pause();
            imageView.setImageResource(R.drawable.ic_mute);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
