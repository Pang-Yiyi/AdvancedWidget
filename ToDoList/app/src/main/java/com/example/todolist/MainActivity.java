package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private ArrayList<Todo> ToDoArrayList = new ArrayList<Todo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getData() != null && o.getResultCode() == Activity.RESULT_OK){
                            String action = o.getData().getStringExtra("ACTION");

                            if(action.equals("new")){
                                String newTitle = o.getData().getStringExtra("TITLE");
                                String newContent = o.getData().getStringExtra("CONTENT");
                                Todo newData = new Todo(newTitle, newContent);
                                ToDoArrayList.add(newData);
                            }else if (action.equals("edit")){
                                int index = o.getData().getIntExtra("INDEX",0);
                                String newTitle = o.getData().getStringExtra("TITLE");
                                String newContent = o.getData().getStringExtra("CONTENT");

                                ToDoArrayList.get(index).setTitle(newTitle);
                                ToDoArrayList.get(index).setContent(newContent);
                            }else{
                                int removeIndex = o.getData().getIntExtra("INDEX", 0);
                                ToDoArrayList.remove(removeIndex);
                            }
                        }
                    }
                }
        );

        ListView ToDoListView = (ListView)findViewById(R.id.toDolv);

        if(ToDoArrayList.isEmpty()){
            Log.d("Test", "ToDoArrayList empty.");
            ArrayList<String> empty = new ArrayList<String>();
            empty.add("還沒有待辦事項，趕快去新增！");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empty);
            ToDoListView.setAdapter(adapter);
        }else{
            ToDoAdapter adapter = new ToDoAdapter(this, ToDoArrayList);
            ToDoListView.setAdapter(adapter);
            ToDoListView.setOnItemClickListener(this);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        ListView ToDoListView = (ListView)findViewById(R.id.toDolv);
        if(ToDoArrayList.isEmpty()){
            Log.d("Test", "ToDoArrayList empty.");
            ArrayList<String> empty = new ArrayList<String>();
            empty.add("還沒有待辦事項，趕快去新增！");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empty);
            ToDoListView.setAdapter(adapter);
        }else{
            ToDoAdapter adapter = new ToDoAdapter(this, ToDoArrayList);
            ToDoListView.setAdapter(adapter);
            ToDoListView.setOnItemClickListener(this);
        }
    }

    public void GoNewToDo (View view){
        Intent intent = new Intent(this, NewToDo.class);
        //設定一個bundle來放資料
        Bundle bundle = new Bundle();
        bundle.putString("ACTION","new");

        //利用intent攜帶bundle資料
        intent.putExtras(bundle);
        intentActivityResultLauncher.launch(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Todo item = ToDoArrayList.get(i);
        String title = item.getTitle();
        String content = item.getContent();

        Intent intent = new Intent(this, NewToDo.class);
        //設定一個bundle來放資料
        Bundle bundle = new Bundle();
        bundle.putString("ACTION","edit");
        bundle.putString("TITLE", title);
        bundle.putString("CONTENT", content);
        bundle.putInt("INDEX", i);

        //利用intent攜帶bundle資料
        intent.putExtras(bundle);
        intentActivityResultLauncher.launch(intent);
    }
}