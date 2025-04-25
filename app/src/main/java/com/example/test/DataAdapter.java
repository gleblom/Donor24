package com.example.test;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private List<Data> dataList;
    private FragmentManager manager;

    public DataAdapter(List<Data> dataList, FragmentManager manager){
        this.dataList = dataList;
        this.manager = manager;
    }
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dataview, parent, false);
        return new DataViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int positon){
        Data data = dataList.get(positon);
        holder.idTextView.setText("ID:" + data.getId());
        holder.statusTextView.setText("Статус: " + data.getStatus());
        holder.componentTextView.setText("Компонент: " + data.getComponent());
        holder.donationDataTextView.setText("Дата донации: " + data.getDonationData());
        holder.conserveDataTextView.setText("Дата консервации: " + data.getConserveData());
        holder.groupTextView.setText("Группа крови: " + data.getGroup());
        holder.rhTextView.setText("Резус-фактор: " + data.getRh());
        holder.imageButton.setOnClickListener(v -> showEditDialog(data));
    }
    @Override
    public int getItemCount(){
        return dataList.size();
    }
    static class DataViewHolder extends RecyclerView.ViewHolder{
        TextView idTextView, statusTextView, componentTextView, donationDataTextView, conserveDataTextView, groupTextView, rhTextView;
        ImageButton imageButton;

        public DataViewHolder(@NonNull View itemView){
            super(itemView);
            idTextView = itemView.findViewById(R.id.Id);
            statusTextView = itemView.findViewById(R.id.Status);
            componentTextView = itemView.findViewById(R.id.Component);
            donationDataTextView = itemView.findViewById(R.id.donationData);
            conserveDataTextView = itemView.findViewById(R.id.conserveData);
            groupTextView = itemView.findViewById(R.id.group);
            rhTextView = itemView.findViewById(R.id.rh);
            imageButton = itemView.findViewById(R.id.buttonEdit);
        }
    }
    private void showEditDialog(Data data) {
        EditDialogFragment editDialogFragment = new EditDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Data", data);
        editDialogFragment.setArguments(bundle);
        editDialogFragment.show(manager, "custom");
    }
}
