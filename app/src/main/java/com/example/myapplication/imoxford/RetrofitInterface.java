package com.example.myapplication.imoxford;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by shivanshu on 4/29/2017.
 */

public interface RetrofitInterface {
    @GET("wordlist/en/lexicalCategory=noun,adverb,adjective?limit=100")
   public Call<FetchWordList> getList(@Header("app_id")String s, @Header("app_key") String y);
    @GET("entries/en/{word_id}/definitions")
    public Call<GetWordMeaning> getWord(@Header("app_id")String s, @Header("app_key") String y, @Path("word_id") String z);
}
