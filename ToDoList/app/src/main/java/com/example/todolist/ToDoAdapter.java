package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ToDoAdapter extends ArrayAdapter<Todo> {

    public ToDoAdapter(@NonNull Context context, ArrayList<Todo> ToDos) {
        super(context, 0, ToDos);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        //找到哪一筆資料
        Todo todo = getItem(position);
        if(converView == null){
            converView = LayoutInflater.
                    from(getContext()).
                    inflate(R.layout.list_sample,
                            parent,false);
        }
        //將資料塞入設計好的樣式
        TextView tv_title = (TextView) converView.findViewById(R.id.listTvTitle);
        TextView tv_content = (TextView) converView.findViewById(R.id.listTvContent);

        tv_title.setText(todo.getTitle());
        String contentText = String.valueOf(todo.getContent());
        if(contentText.length() >= 10){
            contentText = contentText.substring(0,10) + "...";
        }
        tv_content.setText(contentText);

        return converView;
    }
}
