package com.example.myapplication.imoxford;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.key;

/**
 * Created by shivanshu on 11/2/2017.
 */

public interface VoiceRecogniserInterface {
    @POST("./speech:recognize?key=AIzaSyByQmYntxyEVKIRKjbeWRaWkAr3BvhfMag")
    Call<GetTextResponse>getText( @Body String object);
    @GET("check.php")
    Call<GeTextAccuracy> getTextAccuracy(@Query("text") String text,@Query("key") String key);
}
