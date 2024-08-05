package com.example.spinner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(this);
    }
    public void buttonClick(View view){
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        //找到選項的array
        String[] fruits = getResources().getStringArray(R.array.fruit);
        int index = sp.getSelectedItemPosition();

        TextView result = (TextView) findViewById(R.id.tvResult);
        result.setText("您選的是：" + fruits[index]);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String[] fruits = getResources().getStringArray(R.array.fruit);
        TextView result = (TextView) findViewById(R.id.tvResult);
        result.setText("您選的是：" + fruits[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}