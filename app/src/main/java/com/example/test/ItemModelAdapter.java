package com.example.test;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ItemModelAdapter extends android.widget.ArrayAdapter<ItemModel> {
    public ItemModelAdapter(@NonNull Context context, List<ItemModel> statuses) {
        super(context, android.R.layout.simple_spinner_dropdown_item, statuses);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
    @Override
    public View getView(int positon, View convertView, ViewGroup parent){
        TextView textView = (TextView) super.getView(positon, convertView, parent);
        textView.setText(getItem(positon).getItemModelName());
        return textView;
    }
    @Override
    public View getDropDownView(int positon, View convertView, ViewGroup parent){
        TextView textView = (TextView) super.getDropDownView(positon, convertView, parent);
        textView.setText(getItem(positon).getItemModelName());
        return textView;
    }
}
