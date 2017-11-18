package com.example.myapplication.imoxford;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shivanshu on 11/5/2017.
 */

class GeTextAccuracy {
    @Expose
    @SerializedName("score")
    int score;
    @Expose
    @SerializedName("error")
    ErrorHandlingClass errorHandlingClass;

    public int getScore() {
        return score;
    }

    private class ErrorHandlingClass {

    }
}
