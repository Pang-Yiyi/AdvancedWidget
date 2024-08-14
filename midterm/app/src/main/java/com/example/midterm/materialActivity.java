package com.example.midterm;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class materialActivity extends AppCompatActivity {

    String name, image, content, action;
    int index = 0;
    int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_material);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = this.getIntent().getExtras();
        action = bundle.getString("ACTION");
        if (bundle != null && action.equals("edit")){
            name = String.format(bundle.getString("NAME"));
            amount = bundle.getInt("AMOUNT");
            content = String.format(bundle.getString("CONTENT"));
            index = bundle.getInt("INDEX");



            EditText tvName = (EditText)findViewById(R.id.newName);
            tvName.setText(name);
            EditText tvAmount = findViewById(R.id.newAmount);
            tvAmount.setText(String.valueOf(amount));
            TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.contentTextInputLayout);
            textInputLayout.getEditText().setText(content);
        }
    }

    public void ClickSave(View view) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        String titleText = "確定新增？";
        String contentText = "確定內容並儲存？";
        if(action.equals("edit")){
            titleText = "確定修改？";
            contentText = "確定修改內容並儲存？";
        }
        dialog.setTitle(titleText);
        dialog.setMessage(contentText);
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = ((EditText)findViewById(R.id.newName)).getText().toString();
                String amountText = ((EditText)findViewById(R.id.newAmount)).getText().toString();
                int amount = Integer.parseInt(amountText);
                TextInputLayout textInputLayout = findViewById(R.id.contentTextInputLayout);
                String content = textInputLayout.getEditText().getText().toString();
                String imageName = ((EditText)findViewById(R.id.newImageName)).getText().toString(); // 圖片名稱

                Intent intent = new Intent();
                intent.putExtra("NAME", name);
                intent.putExtra("AMOUNT", amount);
                intent.putExtra("CONTENT", content);
                intent.putExtra("IMAGE_NAME", imageName); // 圖片名稱

                intent.putExtra("ACTION", action);
                if(action.equals("edit")){
                    intent.putExtra("INDEX", index);
                }
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        dialog.setNeutralButton("取消", null);
        dialog.show();
    }


    public void ClickGiveUp(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("確定放棄？");
        dialog.setMessage("確定放棄並返回主畫面？");
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {finish();}
        });
        dialog.setNeutralButton("取消",null);
        dialog.show();
    }
}