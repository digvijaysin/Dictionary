package com.example.myapplication.imoxford;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivanshu on 11/2/2017.
 */

public class GetTextResponse {
    @Expose
    @SerializedName("results")
    List<GetResult> list=new ArrayList<>();

    public List<GetResult> getList() {
        return list;
    }

    @Expose
    @SerializedName("error")
    GetError getError;

    public class GetError{
        @Expose
        @SerializedName("message")
        String error;

        public String getError() {
            return error;
        }
    }

    public GetError getGetError() {
        return getError;
    }
}
