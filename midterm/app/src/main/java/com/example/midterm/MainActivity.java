package com.example.midterm;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private ArrayList<material> materialArrayList = new ArrayList<material>();

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
                                String newName = o.getData().getStringExtra("NAME");
                                int newAmount = o.getData().getIntExtra("AMOUNT",0);
                                String newContent = o.getData().getStringExtra("CONTENT");
                                String imageName = o.getData().getStringExtra("IMAGE_NAME");
                                material newData = new material(newName, newAmount,newContent, imageName);
                                materialArrayList.add(newData);
                            }else if (action.equals("edit")){
                                int index = o.getData().getIntExtra("INDEX",0);
                                String newName = o.getData().getStringExtra("NAME");
                                int newAmount = o.getData().getIntExtra("AMOUNT",0);
                                String newContent = o.getData().getStringExtra("CONTENT");
                                String imageName = o.getData().getStringExtra("IMAGE_NAME");

                                materialArrayList.get(index).setName(newName);
                                materialArrayList.get(index).setAmount(newAmount);
                                materialArrayList.get(index).setContent(newContent);
                                materialArrayList.get(index).setImageName(imageName);
                            }else{
                                int removeIndex = o.getData().getIntExtra("INDEX", 0);
                                materialArrayList.remove(removeIndex);
                            }

                            refreshPartListView(MainActivity.this); // 刷新列表視圖
                        }
                    }
                }
        );

        ListView materialListView = (ListView)findViewById(R.id.lv);

        if(materialArrayList.isEmpty()){
            Log.d("Test", "materialArrayList empty.");
            ArrayList<String> empty = new ArrayList<String>();
            empty.add("目前無任何料件！");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empty);
            materialListView.setAdapter(adapter);
        }else{
            storeAdapter adapter = new storeAdapter(this, materialArrayList);
            materialListView.setAdapter(adapter);
            materialListView.setOnItemClickListener(this);
        }
    }

    protected void refreshPartListView(Context context) {
        ListView materialListView = findViewById(R.id.lv);

        if(materialArrayList.isEmpty()){
            Log.d("Test", "materialArrayList empty.");
            ArrayList<String> empty = new ArrayList<>();
            empty.add("目前無任何料件！");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, empty);
            materialListView.setAdapter(adapter);
        } else {
            storeAdapter adapter = new storeAdapter(context, materialArrayList);
            materialListView.setAdapter(adapter);
            materialListView.setOnItemClickListener(this);

            materialListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    String titleText = "確定刪除？";
                    String contentText = "確定刪除這個料件？";
                    dialog.setTitle(titleText);
                    dialog.setMessage(contentText);
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            materialArrayList.remove(itemIndex);
                            refreshPartListView(context); // 傳遞 context
                        }
                    });
                    dialog.setNeutralButton("取消", null);
                    dialog.show();
                    return true;
                }
            });
        }
    }



    public void GoNew (View view){
        Intent intent = new Intent(this, materialActivity.class);
        //設定一個bundle來放資料
        Bundle bundle = new Bundle();
        bundle.putString("ACTION","new");

        //利用intent攜帶bundle資料
        intent.putExtras(bundle);
        intentActivityResultLauncher.launch(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        material item = materialArrayList.get(i);
        String name = item.getName();
        int amount = item.getAmount();
        String content = item.getContent();

        Intent intent = new Intent(this, materialActivity.class);
        //設定一個bundle來放資料
        Bundle bundle = new Bundle();
        bundle.putString("ACTION","edit");
        bundle.putString("NAME", name);
        bundle.putInt("AMOUNT", amount);
        bundle.putString("CONTENT", content);
        bundle.putInt("INDEX", i);

        //利用intent攜帶bundle資料
        intent.putExtras(bundle);
        intentActivityResultLauncher.launch(intent);
    }
}