package com.example.mytodolist;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    private ArrayList<TodoItem> mData = null;
    private ListViewAdapter mAdapter = null;
    private Context mContext;
    private ListView listView;
    private EditText editText;
    private Button btn;
    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private TextView time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        //通过Activity.getIntent()获取当前页面接收到的Intent。
        Intent intent = getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        String date = intent.getStringExtra("date");

        mContext = TaskActivity.this;
        dbHelper = new DataBaseHelper(getApplicationContext());
        time = (TextView) findViewById(R.id.time);
        time.setText(date);


        editText = (EditText) findViewById(R.id.edit_text);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = dbHelper.getWritableDatabase();
                //存储数据
                ContentValues values = new ContentValues();
                values.put("TaskName", editText.getText().toString());
                values.put("TIME", time.getText().toString());
                db.insert("task", null, values);
                TodoItem todoItem=new TodoItem();
                todoItem.setTaskName(editText.getText().toString());
                mData.add(todoItem);
                db.close();
            }

        });
        listView = (ListView) findViewById(R.id.list_view);
        initListNoteListener();
    }
// 刷新数据
    protected void onResume() {
        super.onResume();
        mData = getTaskList();
        mAdapter = new ListViewAdapter(mData, mContext);
        listView.setAdapter(mAdapter);
    }


    public ArrayList<TodoItem> getTaskList() {
        //todoitem对象的数据传入arraylist
        ArrayList<TodoItem> taskList = new ArrayList<TodoItem>();
        //读取数据
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("task", new String[] {"TIME", "ID", "TaskName"},
                "TIME=?", new String[]{time.getText().toString()}, null, null, null);
        //如果能读取到第一行的话（就是数据库不为空的话）再读取每行数据库
        if (cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                TodoItem todoItem = new TodoItem();
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
//删除
    private void initListNoteListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                new AlertDialog.Builder(TaskActivity.this)
                        .setTitle("Alert")
                        .setMessage("Do you want to delete this task ?")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        TodoItem item = mData.get(position);
                                        String id = item.getId();
                                        dbHelper.deleteTask(id);
                                        //删除后刷新列表
                                        mData.remove(position);
                                        Toast.makeText(
                                                TaskActivity.this,
                                                "successfully deleted",
                                                Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }).setNegativeButton("no", null).show();
                return true;
            }
        });
    }


}

