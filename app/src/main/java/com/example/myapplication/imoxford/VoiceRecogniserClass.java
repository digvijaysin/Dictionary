package com.example.myapplication.imoxford;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shivanshu on 11/2/2017.
 */

public class VoiceRecogniserClass {
    static final String BASE_URL1="https://speech.googleapis.com/v1/";
    static final String BASE_URL2="https://api.textgears.com/";
    public static VoiceRecogniserInterface getRetrofitObject(int count) {
        if (count == 0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(VoiceRecogniserInterface.class);
        }
        else
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(VoiceRecogniserInterface.class);

        }
    }
}
