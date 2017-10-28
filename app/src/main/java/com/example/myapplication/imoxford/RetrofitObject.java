package com.example.myapplication.imoxford;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shivanshu on 4/29/2017.
 */

public class RetrofitObject {
    static final String BASE_URL="https://od-api.oxforddictionaries.com/api/v1/";
    public static RetrofitInterface getRetrofitObject()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitInterface.class);
    }

}
