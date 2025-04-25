package com.example.test;

import static android.content.ContentValues.TAG;
import static com.example.test.Supabase.getComponents;
import static com.example.test.Supabase.getData;
import static com.example.test.Supabase.getGroups;
import static com.example.test.Supabase.getRhesuses;
import static com.example.test.Supabase.getStatuses;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditDialogFragment extends DialogFragment
{
    EditText idText, donationDataText, conservationData;
    Spinner groupSpinner, rhSpinner, statusSpinner, componentSpinner;
    Button donationButton, conserveButton;
    DatePickerDialog dialog;
    ItemModel status, component, group, rhesus;
    DatePickerDialog.OnDateSetListener fd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String formattedDate = sdf.format(newCalendar.getTime());
            if(!checkDates(formattedDate, conservationData.getText().toString())){
                Toast toast = Toast.makeText(getActivity(),
                        "Дата донации не может быть больше даты консервации",
                        Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            donationDataText.setText(formattedDate);
        }
    };
    DatePickerDialog.OnDateSetListener ld = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String formattedDate = sdf.format(newCalendar.getTime());
            if(!checkDates(formattedDate, donationDataText.getText().toString())){
                Toast toast = Toast.makeText(getActivity(),
                        "Дата донации не может быть больше даты консервации",
                        Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            conservationData.setText(formattedDate);
        }
    };

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Редактировать запись");

        View view = getLayoutInflater().inflate(R.layout.dialog_edit_dataview, null);
        builder.setView(view);


        Data data = getArguments().getParcelable("Data");

        idText = view.findViewById(R.id.id);
        statusSpinner = view.findViewById(R.id.status);
        componentSpinner = view.findViewById(R.id.component);
        donationDataText = view.findViewById(R.id.donData);
        conservationData = view.findViewById(R.id.conData);
        groupSpinner = view.findViewById(R.id.group);
        rhSpinner = view.findViewById(R.id.rh);
        conserveButton = view.findViewById(R.id.conserveDataButton);
        donationButton = view.findViewById(R.id.donationDataButton);

        getComponents(getActivity(), componentSpinner, data.getComponent());
        getGroups(getActivity(), groupSpinner, data.getGroup());
        getRhesuses(getActivity(), rhSpinner, data.getRh());
        getStatuses(getActivity(), statusSpinner, data.getStatus());

        idText.setText(String.valueOf(data.id));
        donationDataText.setText(data.donationData);
        conservationData.setText(data.conserveData);

        setListeners();

        builder.setPositiveButton("Сохранить", ((dialog, which) -> updateData(data)));
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
        builder.setNeutralButton("Удалить", ((dialog, which) -> deleteData(data)));

        return builder.create();
    }
    public void deleteData(Data data){
        FragmentManager manager = getParentFragmentManager();
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(getActivity());
        confirmDialog.setTitle("Подтверждение");
        confirmDialog.setMessage("Вы точно хотите удалить запись?");
        confirmDialog.setNegativeButton("Отмена", ((dialog1, which1) -> dialog1.dismiss()));
        confirmDialog.setPositiveButton("Удалить", ((dialog1, which1) -> {
            Map<String, Object> params = new HashMap<>();
            params.put("DeleteId", data.getUuid());
            Supabase.deleteDonation(params, manager, MainActivity.argument);
        }));
        confirmDialog.create().show();
    }
    public void updateData(Data data){
        status = (ItemModel)statusSpinner.getSelectedItem();
        component = (ItemModel)componentSpinner.getSelectedItem();
        group = (ItemModel)groupSpinner.getSelectedItem();
        rhesus = (ItemModel)rhSpinner.getSelectedItem();
        String donationData = donationDataText.getText().toString();
        String conserveData = conservationData.getText().toString();
        int id = Integer.parseInt(idText.getText().toString());
        if (!idText.getText().toString().isEmpty()) {
                Map<String, Object> updateData = new HashMap<>();
                updateData.put("updateid",  data.getUuid());
                updateData.put("donorid", id);
                updateData.put("statusid", status.getItemModelId());
                updateData.put("componentid", component.getItemModelId());
                updateData.put("donationdata", donationData);
                updateData.put("conservedata", conserveData);
                updateData.put("groupid", group.getItemModelId());
                updateData.put("rhesusid", rhesus.getItemModelId());
                Supabase.updateData(updateData, getParentFragmentManager(), MainActivity.argument);
        }
        else{
            Toast toast = Toast.makeText(getActivity(), "Заполните поле Id!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void setListeners(){
        donationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = donationDataText.getText().toString();
                Calendar calendar = getCalendarFromDateString(date);
                if(calendar != null){
                    dialog = new DatePickerDialog(getActivity(), fd,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                }
                else
                {
                   dialog = new DatePickerDialog(getActivity());
                   dialog.setOnDateSetListener(fd);
                   dialog.show();
                }
            }
        });

        conserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = conservationData.getText().toString();
                Calendar calendar = getCalendarFromDateString(date);
                if(calendar != null){
                    dialog = new DatePickerDialog(getActivity(), ld,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                }
                else
                {
                    dialog = new DatePickerDialog(getActivity());
                    dialog.setOnDateSetListener(ld);
                    dialog.show();
                }
            }
        });
    }
    private Calendar getCalendarFromDateString(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        try
        {
            Date date = sdf.parse(dateString);
            if (date != null)
            {
                calendar.setTime(date);
                return calendar;
            }
            else
            {
                return null;
            }
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    private boolean checkDates(String firstDate, String secondDate){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        try
        {
            Date fdate = sdf.parse(firstDate);
            Date sdate = sdf.parse(secondDate);
            if(sdate.before(fdate)){
                return false;
            }
        }
        catch (ParseException e)
        {
            return false;
        }
        return true;
    }
}
