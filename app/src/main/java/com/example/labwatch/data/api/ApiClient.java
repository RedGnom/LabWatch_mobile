package com.example.labwatch.data.api;

import com.example.labwatch.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

        // Заглушка на время разработки
        private static final String baseUrl = BuildConfig.API_BASE_URL;

    private static volatile LabWatchApi instance;

    // Singleton для retrofit
    public static LabWatchApi getApi() {
        if (instance == null) {
            synchronized (ApiClient.class) {
                if (instance == null) {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create()) // Парсинг через GSON
                            .build();

                    instance = retrofit.create(LabWatchApi.class);
                }
            }
        }
        return instance;
    }
}