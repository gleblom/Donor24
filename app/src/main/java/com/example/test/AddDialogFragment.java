package com.example.test;

import static com.example.test.Supabase.addDonation;
import static com.example.test.Supabase.getComponents;
import static com.example.test.Supabase.getGroups;
import static com.example.test.Supabase.getRhesuses;
import static com.example.test.Supabase.getStatuses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AddDialogFragment extends DialogFragment {
    EditText donorIdText;
    Spinner compSpinner, grpSpinner, rhesusSpinner;
    String component, group, rhesus, donorId;
    ItemModel componentItem, groupItem, rhesusItem;
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Добавить информацию о донации");

        View view = getLayoutInflater().inflate(R.layout.put_data, null);
        builder.setView(view);

        compSpinner = view.findViewById(R.id.compSpinner);
        grpSpinner = view.findViewById(R.id.grpSpinner);
        rhesusSpinner = view.findViewById(R.id.rhesusSpinner);
        donorIdText = view.findViewById(R.id.donorIdText);

        getComponents(getActivity(), compSpinner);
        getGroups(getActivity(), grpSpinner);
        getRhesuses(getActivity(), rhesusSpinner);

        builder.setPositiveButton("Добавить", ((dialog, which) -> addClick()));
        builder.setNegativeButton("Отмена", ((dialog, which) -> dismiss()));

        return builder.create();
    }
    public void addClick(){
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String formattedDate = sdf.format(current);
        component = compSpinner.getSelectedItem().toString();
        group = grpSpinner.getSelectedItem().toString();
        rhesus = rhesusSpinner.getSelectedItem().toString();
        donorId = donorIdText.getText().toString();
        componentItem = (ItemModel) compSpinner.getSelectedItem();
        groupItem = (ItemModel) grpSpinner.getSelectedItem();
        rhesusItem = (ItemModel) rhesusSpinner.getSelectedItem();
        if(!Objects.equals(component, "Не выбрано") &&
                !Objects.equals(group, "Не выбрано") &&
                !Objects.equals(rhesus, "Не выбрано")) {
            if(!donorId.isEmpty() && donorId.length() < 7){
                Map<String, Object> newData = new HashMap<>();
                newData.put("DonorId", Integer.parseInt(donorId));
                newData.put("Resus", componentItem.getItemModelId());
                newData.put("Grp", groupItem.getItemModelId());
                newData.put("DonationData", formattedDate);
                newData.put("Comp", componentItem.getItemModelId());
                newData.put("StatusId", 1);
                addDonation(newData, getParentFragmentManager());
            }
            else
            {
                Toast toast = Toast.makeText(getActivity(),
                        "Поле ID не должно быть пустым, а также не должно превышать 6 цифр",
                        Toast.LENGTH_LONG);
                        toast.show();
            }
        }
        else
        {
            Toast toast = Toast.makeText(getActivity(), "Заполните все поля!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
