package com.example.sppinnercalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private double num1, num2;
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
        EditText etNum1 = (EditText) findViewById(R.id.num1);
        etNum1.setText("0");
        EditText etNum2 = (EditText) findViewById(R.id.num2);
        etNum2.setText("0");

        Spinner op = (Spinner) findViewById(R.id.spinner);
        op.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //取得數字
        EditText etNum1 = (EditText)findViewById(R.id.num1);
        num1 = Double.parseDouble(String.valueOf(etNum1.getText()));
        EditText etNum2 = (EditText)findViewById(R.id.num2);
        num2 = Double.parseDouble(String.valueOf(etNum2.getText()));

        //取的操作方法的字串
        String[] operation = getResources().getStringArray(R.array.operation);
        double answer = 0.0;
        switch(operation[i]){
            case "+":
                answer = num1 + num2;
                break;
            case"-":
                answer = num1 - num2;
                break;
            case"*":
                answer = num1 * num2;
                break;
            case"/":
                answer = num1 / num2;
                break;
        }
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(String.valueOf(answer));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}