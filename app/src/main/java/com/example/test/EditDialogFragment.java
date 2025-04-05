package com.example.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class EditDialogFragment extends DialogFragment
{
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Редактировать запись");

        View view = getLayoutInflater().inflate(R.layout.dialog_edit_dataview, null);
        builder.setView(view);

        EditText idText = view.findViewById(R.id.id);
        Spinner statusSpinner = view.findViewById(R.id.status);
        Spinner componentSpinner = view.findViewById(R.id.component);
        EditText donationDataText = view.findViewById(R.id.donData);
        EditText conservationData = view.findViewById(R.id.conData);
        Spinner groupSpinner = view.findViewById(R.id.group);
        Spinner rhSpinner = view.findViewById(R.id.rh);


        return builder.create();
    }

}
