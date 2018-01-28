package com.example.myapplication.imoxford;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.imoxford.AssetManagement.InitiateAllWords;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(in);

    }

    @Override
    protected void onPause() {
        super.onPause();
        InitiateAllWords initiateAllWords=new InitiateAllWords(this);
        finish();
    }
}

