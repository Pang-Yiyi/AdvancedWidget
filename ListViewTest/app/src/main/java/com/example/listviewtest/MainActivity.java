package com.example.listviewtest;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView cityListView;
    private List<String> cities = new ArrayList<String>();

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
//        ListView lv = (ListView) findViewById(R.id.cityListView);
//        lv.setOnItemClickListener(this);
        cityListView = (ListView) findViewById(R.id.cityListView);
        setCities();
        cityListView.setOnItemClickListener(this);
    }

    private void setCities(){
        cities = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cities)));
        cities.add("Hualien");
        cities.add("Taitung");
        cities.add("Changhua");
        //new ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,cities);
        cityListView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TextView tv = (TextView) findViewById(R.id.textView);
//        String[] citiesArray = getResources().getStringArray(R.array.cities);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("城市");
        dialog.setMessage("您選擇的是：" + cities.get(i));
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定",null);
        dialog.setNeutralButton("取消",null);
        dialog.setNegativeButton("放棄",null);
        dialog.show();

//        tv.setText("您選擇的是：" + citiesArray[i]);
//        Toast.makeText(this,"您選擇的是：" + citiesArray[i],Toast.LENGTH_SHORT).show();
    }
}