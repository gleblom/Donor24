package com.example.test;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SupabaseService
{
    //URL проекта
    String BASE_URL = "https://jjyjaahtlyokojguaxji.supabase.co/rest/v1/";
    // API-ключ
    String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImpqeWphYWh0bHlva29qZ3VheGppIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzgxNDYyODIsImV4cCI6MjA1MzcyMjI4Mn0.tbThAofXtRa-7ULUnmXQLOEvLKFxuiYR9LU_tQyl8dQ";

    // Заголовок запроса (ОБЯЗАТЕЛЬНО)
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    // GET-запрос для получения данных из таблицы(в данном случае представления) dataview
    // Т.к в в ответе нам придёт JSON с данными, в Call пишем List<Map<String, Object>>
    @GET("dataview")
    Call<List<Map<String, Object>>> getDataView();
    //Аналогично предыдущему
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    @GET("Status")
    Call<List<Map<String, Object>>> getStatus();
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    //Другой вариант запроса. В данном случае была создана хранимая процедура(в Supabase - Function) для обновления данных.
    //Все хранимые процедуры сохраняются по пути rpc/название процедуры
    //Чтобы вызвать процедуру нужно сделать запрос POST
    //Т.к процедура ничего не возвращает, в Call пишем Void
    //Но на этот раз мы передаём данные, поэтому в скобках пишем тэг @Body - тело нашего запроса
    //Тело запроса тоже JSON, поэтому Map<String, Object>
    @POST("rpc/updateView")
    Call<Void> updateView(@Body Map<String, Object> params);
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    //Тут всё аналогично предыдущему запросу, только на этот раз есть и тело запроса и ответ.
    @POST("rpc/find")
    Call<List<Map<String, Object>>> search(@Body Map<String, Object> arg);
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    @POST("rpc/getBlood")
    Call<List<Map<String, Object>>> compatibilitySearch(@Body Map<String, Object> arg);
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    @POST("rpc/getPlasma")
    Call<List<Map<String, Object>>> compatibilitySearchPlasma(@Body Map<String, Object> arg);
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    @POST("rpc/deletedonation")
    Call<Void> deleteDonation(@Body Map<String, Object> arg);
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    @POST("rpc/getComponents")
    Call<List<Map<String, Object>>> getComponents();
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    @POST("rpc/getGroups")
    Call<List<Map<String, Object>>> getGroups();
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    @POST("rpc/getRhesuses")
    Call<List<Map<String, Object>>> getRhesuses();
    @Headers({
            "apikey: " + API_KEY,
            "Authorization: Bearer " + API_KEY,
            "Content-Type: application/json"
    })
    @POST("rpc/AddDonation")
    Call<Void> addDonation(@Body Map<String, Object> arg);
}
