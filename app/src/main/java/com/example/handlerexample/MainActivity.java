package com.example.handlerexample;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Брояч на натисканията на бутона
    private int buttonPressCount = 0;

    // Брояч на времето
    private long sTime = 0L;

    // Обработчик на нишката - обновява информацията за времето
    private final Handler handler = new Handler();

    // Компонент за представяне информацията за времето
    TextView tvInfo;

    // Бутон за отброяване на натисканията
    Button btStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tvInfo);
        btStart = findViewById(R.id.btStart);

        if (sTime == 0L) {
            sTime = SystemClock.uptimeMillis();
            handler.removeCallbacks(timeUpdater);
            //Добавяме Runnable обекта timeUpdater, който ще бъде стартиран след 150 мс
            handler.postDelayed(timeUpdater, 150);
        }

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pressCountStr = String.valueOf(++buttonPressCount);
                btStart.setText(pressCountStr);
            }
        });
    }

    // Това е фоновата задача (Runnable)
    private final Runnable timeUpdater = new Runnable() {
        @Override
        public void run() {
            // Изчисляваме времето
            final long start = sTime;
            long millis = SystemClock.uptimeMillis() - start;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            // Показваме времето
            tvInfo.setText("" + minutes + ":" + String.format("%02d", seconds));

            // Закъснение от 300 мс
            handler.postDelayed(this, 300);
        }
    };

    @Override
    protected void onPause() {
        //Изтриваме Runnable обекта
        handler.removeCallbacks(timeUpdater);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Добавяме Runnable обекта
        handler.postDelayed(timeUpdater, 150);
    }
}