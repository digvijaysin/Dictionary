package com.example.myapplication.imoxford;

import android.hardware.SensorEvent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shivanshu on 5/8/2017.
 */

public class GetWordMeaning {
    @Expose
    @SerializedName("results")
    List<LexicalEntries> lexiclaEntries;

    public class LexicalEntries
            {
                @Expose
                @SerializedName("lexicalEntries")
               List<Entries> entries;
                @Expose
                @SerializedName("lexicalCategory")
                String WordType;

                public String getWordType() {
                    return WordType;
                }
            }
    public class Entries
    {
        @Expose
        @SerializedName("entries")
        List<Sense> sense;

    }
    public class Sense
    {
        @Expose
        @SerializedName("senses")
        List<Definitions> definitions;

    }
    public class Definitions
    {
        @Expose
        @SerializedName("definitions")
        List<String> definitions;
    }
}
