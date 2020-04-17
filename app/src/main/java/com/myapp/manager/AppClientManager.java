package com.myapp.manager;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.myapp.BuildConfig.BASE_URL;

public class AppClientManager {
    public static final int requestApiTimeOut = 30;
    private static final String TAG = "AppClientManager";
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    public AppClientManager(){
        okHttpClient = createOkHttpClient();
        retrofit = createClient();
    }

    public AppClientManager(String url){
        okHttpClient = createOkHttpClient();
        retrofit = createClient(url);
    }

    private OkHttpClient createOkHttpClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
            /*if (message.contains("AuthToken") || message.contains("200 OK")) {
                Log.i("OkHttp", " http:" + message);
            }*/
            Log.i(TAG, "OkHttp:" + message);
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(requestApiTimeOut, TimeUnit.SECONDS)
                .writeTimeout(requestApiTimeOut, TimeUnit.SECONDS)
                .readTimeout(requestApiTimeOut, TimeUnit.SECONDS)
                .build();
    }

    public Retrofit createClient(){
        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
    }

    public Retrofit createClient(String url){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public Retrofit getClient(){
        return retrofit;
    }
}
