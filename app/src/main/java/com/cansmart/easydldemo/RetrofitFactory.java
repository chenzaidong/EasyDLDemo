package com.cansmart.easydldemo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static final int TIME_OUT = 30;
    private ApiService mApiService;

    private static class SingleInstance {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }

    public static RetrofitFactory getInstance() {
        return SingleInstance.INSTANCE;
    }

    private RetrofitFactory() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://aip.baidubce.com/rpc/2.0/")
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return mApiService;
    }


}
