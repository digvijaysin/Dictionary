package com.example.myapplication.imoxford;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Thread th = new Thread() {
            @Override
            public void run() {


                try {

                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent in = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(in);
                }
            }
        };
        th.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

