package com.example.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView textView;
    private FloatingActionButton FBA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.show);
        FBA = (FloatingActionButton) findViewById(R.id.fba);
        FBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = textView.getText().toString();
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);

            }
        });
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                textView.setText(year+"年"+month+1+"月"+dayOfMonth+"日");
            }
        });
    }
    }
