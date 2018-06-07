package com.cansmart.easydldemo;

import android.annotation.SuppressLint;
import android.app.Application;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.cansmart.easydldemo.MainActivity.TOKEN_URL;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initToken();
    }

    @SuppressLint("CheckResult")
    private void initToken() {
        RetrofitFactory.getInstance().getApiService()
                .getToken(TOKEN_URL).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tokenEntity -> HttpConfig.getInstance().setToken(tokenEntity.getAccess_token()));
    }
}
