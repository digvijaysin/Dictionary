package com.example.myapplication.imoxford;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.R.style.Animation;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FragmentManager manager;
    ImageButton Home;
    ImageButton search;
    Button WordMeaning;
    Button Grammer;
    Button PlayGames;
    Button ShareIt;

    ImageButton Share;
    ImageButton Exit;
    static int flag=0;
    TextToSpeech speech;
AlertDialog alertDialog;
    List<WordList> list2=new ArrayList<>();
LinearLayout linearLayout;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Home=(ImageButton)findViewById(R.id.homebutton);
        search=(ImageButton)findViewById(R.id.searchbutton);
        Share=(ImageButton)findViewById(R.id.sharebutton);
        Exit=(ImageButton)findViewById(R.id.exit);
        Home.setOnClickListener(this);
        search.setOnClickListener(this);
        Share.setOnClickListener(this);
        Exit.setOnClickListener(this);
        speech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speech.setLanguage(Locale.UK);
                }
            }
        });

WordMeaning=(Button)findViewById(R.id.search_word);
        WordMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);

            }
        });
        Grammer=(Button)findViewById(R.id.grammer);
        Grammer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,VoiceRecogniser.class);
                startActivity(intent);

            }
        });
        PlayGames=(Button)findViewById(R.id.games);
        PlayGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ShareIt=(Button)findViewById(R.id.share);




    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.homebutton)
        {

        }
        if(v.getId()==R.id.searchbutton)
        {
            Intent intent=new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.sharebutton)
        {

        }
        if(v.getId()==R.id.exit)
        {
            if(flag==0)
            {
                Toast.makeText(this,"Please Press again to exit",Toast.LENGTH_SHORT).show();
                flag++;
            }
            else {

                finish();
            }
        }

    }
}
