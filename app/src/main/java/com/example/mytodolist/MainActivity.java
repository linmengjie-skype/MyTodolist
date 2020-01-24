package com.example.mytodolist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView textView;
    private FloatingActionButton FBA;
    private ListView listView;
    private ArrayList<TodoItem> mData = null;
    private ListViewAdapter mAdapter = null;
    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.show);
        listView=(ListView) findViewById(R.id.lv);
        mContext = MainActivity.this;
        dbHelper = new DataBaseHelper(getApplicationContext());
        //点fba跳转
        FBA = (FloatingActionButton) findViewById(R.id.fba);
        FBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = textView.getText().toString();
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                //传值
                intent.putExtra("date", date);
                startActivity(intent);

            }
        });
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //内置方法获取日期
                textView.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
                //获取tasklist
                mData = getTaskList();
                mAdapter = new ListViewAdapter(mData, mContext);
                listView.setAdapter(mAdapter);


            }
        });
    }

    protected void onResume() {
        super.onResume();
        mData = getTaskList();
        mAdapter = new ListViewAdapter(mData, mContext);
        listView.setAdapter(mAdapter);
    }
    public ArrayList<TodoItem> getTaskList() {
        ArrayList<TodoItem> taskList = new ArrayList<TodoItem>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("task",  new String[] {"TIME", "ID", "TaskName"}, "TIME=?", new String[]{textView.getText().toString()}, null, null, null);
        if (cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                TodoItem todoItem = new TodoItem();
                //遍历Cursor对象，取出数据并打印
                String id= cursor.getString(cursor.getColumnIndex("ID"));
                String name = cursor.getString(cursor.getColumnIndex("TaskName"));
                todoItem.setId(id);
                todoItem.setTaskName(name);
                taskList.add(todoItem);
            }
        }
        cursor.close();
        db.close();
        return taskList;
    }

}
