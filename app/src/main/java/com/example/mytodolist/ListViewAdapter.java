package com.example.mytodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {
    private ArrayList<TodoItem> mData;
    private Context mContext;

    public ListViewAdapter(ArrayList<TodoItem> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }




    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_adapter,parent,false);
        TextView task = (TextView) convertView.findViewById(R.id.task_list);

        task.setText(mData.get(position).getTaskName());
        return convertView;
    }
}
