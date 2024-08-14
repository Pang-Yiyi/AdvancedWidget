package com.example.midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class storeAdapter extends ArrayAdapter<material> {

    public storeAdapter(@NonNull Context context, ArrayList<material> materials) {
        super(context, 0, materials);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 確保每次都處理正確的 convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sample_view, parent, false);
        }

        // 正確取得該位置的 material 資料
        material currentMaterial = getItem(position);

        // 填充視圖中的資料
        TextView tv_name = convertView.findViewById(R.id.tv_name);
        TextView tv_amount = convertView.findViewById(R.id.tv_amount);
        TextView tv_content = convertView.findViewById(R.id.tv_content);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        // 設定資料
        tv_name.setText(currentMaterial.getName());
        tv_amount.setText(String.valueOf(currentMaterial.getAmount()));
        tv_content.setText(currentMaterial.getContent());

        // 根據圖片名稱加載圖片
        int imageResId = getContext().getResources().getIdentifier(currentMaterial.getImageName(), "drawable", getContext().getPackageName());
        if (imageResId != 0) {
            imageView.setImageResource(imageResId);
        }

        return convertView;
    }
}

