package com.example.test;

import static android.content.ContentValues.TAG;

import static com.example.test.Supabase.compatibilitySearch;
import static com.example.test.Supabase.getComponents;
import static com.example.test.Supabase.getData;
import static com.example.test.Supabase.getGroups;
import static com.example.test.Supabase.getPlasma;
import static com.example.test.Supabase.getRhesuses;
import static com.example.test.Supabase.search;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.telecom.Call;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.text.Regex;


public class MainActivity extends AppCompatActivity {

    public static Map<String, Object> argument = new HashMap<>();
    EditText searchEditText;
    Spinner componentSpinner, groupSpinner, rhSpinner;
    public static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchText);
        componentSpinner = findViewById(R.id.componentSpinner);
        groupSpinner = findViewById(R.id.groupSpinner);
        rhSpinner = findViewById(R.id.rhSpinner);

        getComponents(this, componentSpinner);
        getRhesuses(this, rhSpinner);
        getGroups(this, groupSpinner);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData(recyclerView, getSupportFragmentManager());

    }

    public void onSearchClick(View view) {
        String filter = searchEditText.getText().toString();
        String compoment = componentSpinner.getSelectedItem().toString();
        String rh = rhSpinner.getSelectedItem().toString();
        String group = groupSpinner.getSelectedItem().toString();
        argument.clear();
        if (compoment != "Не выбрано" && group != "Не выбрано") {
            if (rh != "Не выбрано" && compoment.equals("Кровь")) {
                argument.put("grp", group);
                argument.put("resus", rh);
                compatibilitySearch(argument, recyclerView, getSupportFragmentManager());
                return;
            }
            if (compoment.equals("Плазма")) {
                argument.put("grp", group);
                getPlasma(argument, recyclerView, getSupportFragmentManager());
                return;
            }
        }
        if (filter.length() != 0) {
            argument.put("arg", filter);
            search(argument, recyclerView, getSupportFragmentManager());
        }
        else
        {
            getData(recyclerView, getSupportFragmentManager());
        }
    }
    public void onAddClick(View view){
        AddDialogFragment fragment = new AddDialogFragment();
        fragment.show(getSupportFragmentManager(), "custom");
    }
}