package com.cansmart.easydldemo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @GET
    Observable<TokenEntity> getToken(@Url String url);

    @POST("ai_custom/v1/classification/device_recognition")
    Observable<ResultEntity> getResult(@Query("access_token") String token, @Body RequestBody requestBody);
}
