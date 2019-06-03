package com.example.timerapplication;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView time;
    int interval = 1;
    long timeInSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView start = (TextView) findViewById(R.id.start);
        time = findViewById(R.id.time);
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                timeInSeconds += interval;
                if (timeInSeconds >= 0)
                    formatSeconds();
                else {
                    timeInSeconds = 0;
                    formatSeconds();
                }
                handler.postDelayed(this, 1000);
            }
        };

        findViewById(R.id.start).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (start.getText().toString().equalsIgnoreCase("start")) {
                            start.setText("Pause");
                            handler.postDelayed(runnable, 1000);
                        } else if (start.getText().toString().equalsIgnoreCase("pause")) {
                            start.setText("Resume");
                            handler.removeCallbacksAndMessages(null);
                        } else if (start.getText().toString().equalsIgnoreCase("resume")) {
                            start.setText("Pause");
                            handler.postDelayed(runnable, 1000);
                        }
                    }
                });

        findViewById(R.id.stop).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start.setText("start");
                        timeInSeconds = 0;
                        handler.removeCallbacksAndMessages(null);
                        start.setText("Start");
                        time.setText("00:00:00");
                    }
                });

        findViewById(R.id.inc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interval < 10) {
                    interval++;
                    ((TextView) findViewById(R.id.txt_inc_by)).setText(String.valueOf(interval));
                }
            }
        });
        findViewById(R.id.dec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interval > -10) {
                    interval--;
                    ((TextView) findViewById(R.id.txt_inc_by)).setText(String.valueOf(interval));
                }
            }
        });
    }

    public void formatSeconds() {
        long millis = timeInSeconds * 1000;
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        time.setText(hms);
    }

}