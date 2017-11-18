package com.example.myapplication.imoxford;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivanshu on 11/3/2017.
 */

class GetResult {
@Expose
    @SerializedName("alternatives")
List<GetText> getText= new ArrayList<>();

        public class GetText{
         @Expose
            @SerializedName("transcript")
            String text;

            public String getText() {
                return text;
            }
        }

    public  List<GetText> getGetText() {
        return getText;
    }
}

