package com.example.myapplication.imoxford;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shivanshu on 4/29/2017.
 */

public class WordList {
  @Expose
  @SerializedName("id")
  String WordId;
@Expose
@SerializedName("word")
String Word;
    public String getWord() {
        return Word;
    }
    public String getWordId() {
        return WordId;
    }
}
