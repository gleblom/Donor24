package com.example.test;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Supabase {
    public static void getData(RecyclerView recyclerView, FragmentManager manager){
        SupabaseService service = SupabaseClient.getInstance(); // Полчаем экземпляр для реализации методов из SupabaseService
        List<Data> dataList = new ArrayList<>(); //Создаём список для сохранения каждой строчки из ответа
        service.getDataView().enqueue(new Callback<List<Map<String, Object>>>() { // Вызывем запрос
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if (response.isSuccessful()) //Если ответ положительный, то обрабатываем его
                {
                    //Проходим по полученным данным и сохраняем их в класс, а затем добавляем в список
                    for(Map<String, Object> item: response.body()){
                        int id = ((Double) item.get("Id")).intValue();
                        String status = (String) item.get("status");
                        String component = (String) item.get("component");
                        String donationData = (String) item.get("Donation_data");
                        String conserveData = (String) item.get("Conserve_data");
                        String group = (String) item.get("group");
                        String rh = (String) item.get("rhesus-factor");
                        String uuid = (String) item.get("DataId");
                        int StatusId = ((Double) item.get("StatusId")).intValue();
                        int ComponentId = ((Double) item.get("ComponentId")).intValue();
                        int GroupId = ((Double) item.get("GroupId")).intValue();
                        int RhesusId = ((Double) item.get("RhesusId")).intValue();
                        dataList.add(new Data(id, new ItemModel(StatusId, status),
                                new ItemModel(ComponentId, component), donationData, conserveData,
                                new ItemModel(GroupId, group),
                                new ItemModel(RhesusId, rh), uuid));

                    }
                    DataAdapter adapter = new DataAdapter(dataList, manager);
                    recyclerView.setAdapter(adapter);
                    Log.d(TAG, "Успешно получены данные: " + response.body());
                }
                else //Если ответ отрицательный, логируем код и ошибку
                {

                    Log.e(TAG, "Ошибка при получении данных. Код: " + response.code() +
                            ", Сообщение: " + response.message());
                }
            }
            //Если ответа нет вообще, то выводим ошибку в логи
            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public static void updateData(Map<String, Object> params, FragmentManager manager, Map<String, Object> argument){
        SupabaseService service = SupabaseClient.getInstance();
        service.updateView(params).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Данные успешно обновлены");
                    if(argument.get("resus") != null){
                        compatibilitySearch(argument, MainActivity.recyclerView, manager);
                        return;
                    }
                    if(argument.get("arg") != null){
                        search(argument, MainActivity.recyclerView, manager);
                        return;
                    }
                    if(argument.get("resus") == null && argument.get("grp") != null){
                        getPlasma(argument, MainActivity.recyclerView, manager);
                    }
                    else {
                        getData(MainActivity.recyclerView, manager);
                    }
                }
                else {
                    Log.d(TAG, "Ошибка при обновлении данных!" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public static void getStatuses(Context context, Spinner spinner, ItemModel model){
        SupabaseService service = SupabaseClient.getInstance();
        ItemModel[] statuses = new ItemModel[5];
        service.getStatus().enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if(response.isSuccessful())
                {
                    int i = 0;
                    for (Map<String, Object> item: response.body())
                    {
                        int id = ((Double) item.get("statusId")).intValue();
                        String status = (String) item.get("status");
                        ItemModel s = new ItemModel(id, status);
                        statuses[i] = s;
                        i++;
                    }
                    ArrayAdapter<ItemModel> adapter = new
                            ArrayAdapter<ItemModel>(context, android.R.layout.simple_spinner_dropdown_item, statuses);
                    spinner.setAdapter(adapter);
                    int position = adapter.getPosition(model);
                    spinner.setSelection(position);
                    Log.d(TAG, "Данные успешно получены: " + response.body());
                }
                else
                {
                    Log.d(TAG, "Ошибка при получении данных " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t)
            {
                t.printStackTrace();
            }
        });
    }
    public static void search(Map<String, Object> params, RecyclerView recyclerView, FragmentManager manager){
        SupabaseService service = SupabaseClient.getInstance();
        List<Data> dataList = new ArrayList<>();
        service.search(params).enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response)
            {
                if(response.isSuccessful()){
                    for(Map<String, Object> item: response.body()){
                        int id = ((Double) item.get("Id")).intValue();
                        String status = (String) item.get("status");
                        String component = (String) item.get("component");
                        String donationData = (String) item.get("Donation_data");
                        String conserveData = (String) item.get("Conserve_data");
                        String group = (String) item.get("group");
                        String rh = (String) item.get("rhesus-factor");
                        String uuid = (String) item.get("DataId");
                        int StatusId = ((Double) item.get("StatusId")).intValue();
                        int ComponentId = ((Double) item.get("ComponentId")).intValue();
                        int GroupId = ((Double) item.get("GroupId")).intValue();
                        int RhesusId = ((Double) item.get("RhesusId")).intValue();
                        dataList.add(new Data(id, new ItemModel(StatusId, status),
                                new ItemModel(ComponentId, component), donationData, conserveData,
                                new ItemModel(GroupId, group),
                                new ItemModel(RhesusId, rh), uuid));
                    }
                    DataAdapter adapter = new DataAdapter(dataList, manager);
                    recyclerView.setAdapter(adapter);
                    Log.d(TAG, "Данные получены успешно!" + response.body());
                }
                else {
                    Log.d(TAG, "Ошибка при получении данных!" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {

            }
        });
    }
    public static void compatibilitySearch(Map<String, Object> params, RecyclerView recyclerView, FragmentManager manager){
        SupabaseService service = SupabaseClient.getInstance();
        List<Data> dataList = new ArrayList<>();
        service.compatibilitySearch(params).enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response)
            {
                if(response.isSuccessful())
                {
                    for(Map<String, Object> item: response.body())
                    {
                        int id = ((Double) item.get("Id")).intValue();
                        String status = (String) item.get("status");
                        String component = (String) item.get("component");
                        String donationData = (String) item.get("Donation_data");
                        String conserveData = (String) item.get("Conserve_data");
                        String group = (String) item.get("group");
                        String rh = (String) item.get("rhesus-factor");
                        String uuid = (String) item.get("DataId");
                        int StatusId = ((Double) item.get("StatusId")).intValue();
                        int ComponentId = ((Double) item.get("ComponentId")).intValue();
                        int GroupId = ((Double) item.get("GroupId")).intValue();
                        int RhesusId = ((Double) item.get("RhesusId")).intValue();
                        dataList.add(new Data(id, new ItemModel(StatusId, status),
                                new ItemModel(ComponentId, component), donationData, conserveData,
                                new ItemModel(GroupId, group),
                                new ItemModel(RhesusId, rh), uuid));
                    }
                    DataAdapter adapter = new DataAdapter(dataList, manager);
                    recyclerView.setAdapter(adapter);
                    Log.d(TAG, "Данные получены успешно!" + response.raw());
                }
                else
                {
                    Log.d(TAG, "Ошибка при получении данных!" + response.raw());
                }

            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t)
            {

            }
        });
    }
    public static void getPlasma(Map<String, Object> params, RecyclerView recyclerView, FragmentManager manager){
        SupabaseService service = SupabaseClient.getInstance();
        List<Data> dataList = new ArrayList<>();
        service.compatibilitySearchPlasma(params).enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if(response.isSuccessful())
                {
                    for(Map<String, Object> item: response.body())
                    {
                        int id = ((Double) item.get("Id")).intValue();
                        String status = (String) item.get("status");
                        String component = (String) item.get("component");
                        String donationData = (String) item.get("Donation_data");
                        String conserveData = (String) item.get("Conserve_data");
                        String group = (String) item.get("group");
                        String rh = (String) item.get("rhesus-factor");
                        String uuid = (String) item.get("DataId");
                        int StatusId = ((Double) item.get("StatusId")).intValue();
                        int ComponentId = ((Double) item.get("ComponentId")).intValue();
                        int GroupId = ((Double) item.get("GroupId")).intValue();
                        int RhesusId = ((Double) item.get("RhesusId")).intValue();
                        dataList.add(new Data(id, new ItemModel(StatusId, status),
                                new ItemModel(ComponentId, component), donationData, conserveData,
                                new ItemModel(GroupId, group),
                                new ItemModel(RhesusId, rh), uuid));
                    }
                    DataAdapter adapter = new DataAdapter(dataList, manager);
                    recyclerView.setAdapter(adapter);
                    Log.d(TAG, "Данные получены успешно!" + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {

            }
        });
    }
    public static void deleteDonation(Map<String, Object> arg, FragmentManager manager, Map<String, Object> argument){
        SupabaseService service = SupabaseClient.getInstance();
        service.deleteDonation(arg).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Данные успешно обновлены");
                    if(argument.get("resus") != null){
                        compatibilitySearch(argument, MainActivity.recyclerView, manager);
                        return;
                    }
                    if(argument.get("arg") != null){
                        search(argument, MainActivity.recyclerView, manager);
                        return;
                    }
                    if(argument.get("resus") == null && argument.get("grp") != null){
                        getPlasma(argument, MainActivity.recyclerView, manager);
                    }
                    else {
                        getData(MainActivity.recyclerView, manager);
                    }
                }
                else
                {
                    Log.d(TAG, "" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    public static void getComponents(Context context, Spinner spinner){
        SupabaseService service = SupabaseClient.getInstance();
        ItemModel[] components = new ItemModel[3];
        service.getComponents().enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if(response.isSuccessful())
                {
                    int i = 0;
                    for(Map<String, Object> item: response.body())
                    {
                        int id = ((Double) item.get("componentId")).intValue();
                        String name = (String) item.get("component");
                        ItemModel component = new ItemModel(id, name);
                        components[i] = component;
                        i++;

                    }
                    Log.d(TAG, "Данные поулчены!" + response.body());
                    ArrayAdapter<ItemModel> adapter =
                            new ArrayAdapter<ItemModel>(context, android.R.layout.simple_spinner_dropdown_item, components);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(2);
                }
                else
                {
                    Log.d(TAG, "" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {

            }
        });
    }
    public static void getGroups(Context context, Spinner spinner){
        SupabaseService service = SupabaseClient.getInstance();
        ItemModel[] groups = new ItemModel[5];
        service.getGroups().enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if(response.isSuccessful())
                {
                    int i = 0;
                    for(Map<String, Object> item: response.body())
                    {
                        int id = ((Double) item.get("groupId")).intValue();
                        String name = (String) item.get("group");
                        ItemModel group = new ItemModel(id, name);
                        groups[i] = group;
                        i++;

                    }
                    Log.d(TAG, "Данные поулчены!" + response.body());
                    ArrayAdapter<ItemModel> adapter =
                            new ArrayAdapter<ItemModel>(context, android.R.layout.simple_spinner_dropdown_item, groups);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(4);

                }
                else
                {
                    Log.d(TAG, "" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {

            }
        });
    }
    public static void getRhesuses(Context context, Spinner spinner){
        SupabaseService service = SupabaseClient.getInstance();
        ItemModel[] rhesuses = new ItemModel[3];
        service.getRhesuses().enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if(response.isSuccessful())
                {
                    int i = 0;
                    for(Map<String, Object> item: response.body())
                    {
                        int id = ((Double) item.get("rhesusId")).intValue();
                        String name = (String) item.get("rhesus-factor");
                        ItemModel rhesus = new ItemModel(id, name);
                        rhesuses[i] = rhesus;
                        i++;

                    }
                    Log.d(TAG, "Данные поулчены!" + response.body());
                    ArrayAdapter<ItemModel> adapter =
                            new ArrayAdapter<ItemModel>(context, android.R.layout.simple_spinner_dropdown_item, rhesuses);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(2);
                }
                else
                {
                    Log.d(TAG, "" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {

            }
        });
    }
    public static void getComponents(Context context, Spinner spinner, ItemModel model){
        SupabaseService service = SupabaseClient.getInstance();
        List<ItemModel> components = new ArrayList<ItemModel>();
        service.getComponents().enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if(response.isSuccessful())
                {
                    int i = 0;
                    for(Map<String, Object> item: response.body())
                    {
                        int id = ((Double) item.get("componentId")).intValue();
                        String name = (String) item.get("component");
                        if(Objects.equals(name, "Не выбрано")){
                            continue;
                        }
                        ItemModel component = new ItemModel(id, name);
                        components.add(i, component);
                        i++;

                    }
                    Log.d(TAG, "Данные поулчены!" + response.body());
                    ArrayAdapter<ItemModel> adapter =
                            new ArrayAdapter<ItemModel>(context, android.R.layout.simple_spinner_dropdown_item, components);
                    spinner.setAdapter(adapter);
                    int position = adapter.getPosition(model);
                    spinner.setSelection(position);
                }
                else
                {
                    Log.d(TAG, "" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {

            }
        });
    }
    public static void getGroups(Context context, Spinner spinner, ItemModel model){
        SupabaseService service = SupabaseClient.getInstance();
        List<ItemModel> groups = new ArrayList<ItemModel>();
        service.getGroups().enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if(response.isSuccessful())
                {
                    int i = 0;
                    for(Map<String, Object> item: response.body())
                    {
                        int id = ((Double) item.get("groupId")).intValue();
                        String name = (String) item.get("group");
                        if(Objects.equals(name, "Не выбрано")){
                            continue;
                        }
                        ItemModel group = new ItemModel(id, name);
                        groups.add(i, group);
                        i++;

                    }
                    Log.d(TAG, "Данные поулчены!" + response.body());
                    ArrayAdapter<ItemModel> adapter =
                            new ArrayAdapter<ItemModel>(context, android.R.layout.simple_spinner_dropdown_item, groups);
                    spinner.setAdapter(adapter);
                    int position = adapter.getPosition(model);
                    spinner.setSelection(position);

                }
                else
                {
                    Log.d(TAG, "" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {

            }
        });
    }
    public static void getRhesuses(Context context, Spinner spinner, ItemModel model){
        SupabaseService service = SupabaseClient.getInstance();
        List<ItemModel> rhesuses = new ArrayList<ItemModel>();
        service.getRhesuses().enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if(response.isSuccessful())
                {
                    int i = 0;
                    for(Map<String, Object> item: response.body())
                    {
                        int id = ((Double) item.get("rhesusId")).intValue();
                        String name = (String) item.get("rhesus-factor");
                        if(Objects.equals(name, "Не выбрано")){
                            continue;
                        }
                        ItemModel rhesus = new ItemModel(id, name);
                        rhesuses.add(i, rhesus);
                        i++;

                    }
                    Log.d(TAG, "Данные поулчены!" + response.body());
                    ArrayAdapter<ItemModel> adapter =
                            new ArrayAdapter<ItemModel>(context, android.R.layout.simple_spinner_dropdown_item, rhesuses);
                    spinner.setAdapter(adapter);
                    int position = adapter.getPosition(model);
                    spinner.setSelection(position);
                }
                else
                {
                    Log.d(TAG, "" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {

            }
        });
    }
    public static void addDonation(Map<String, Object> params, FragmentManager manager){
        SupabaseService service = SupabaseClient.getInstance();
        service.addDonation(params).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "Данные успешно добавлены!");
                    getData(MainActivity.recyclerView, manager);
                }
                else {
                    Log.e(TAG, "При добавлении данных произшла ошибка!" + response.raw());
                }

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
}
