package com.example.myapplication.imoxford;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shivanshu on 4/29/2017.
 */

public class FetchWordList {
    @Expose
    @SerializedName("results")
    public List<WordList> wordLists;

}
