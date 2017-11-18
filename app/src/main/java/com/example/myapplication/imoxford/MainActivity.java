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


public class MainActivity extends AppCompatActivity implements Callback<FetchWordList>, View.OnClickListener, MiddleFragment.OnFragmentInteractionListener,Button1Fragment.OnFragmentInteractionListener,Button2Fragment.OnFragmentInteractionListener,Button3Fragment.OnFragmentInteractionListener,Button4Fragment.OnFragmentInteractionListener,GameFragment.OnFragmentInteractionListener,DefinitionsFragment.OnFragmentInteractionListener {

    FrameLayout frameLayout;
    FragmentManager manager;
    ImageButton Home;
    ImageButton search;
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
linearLayout=(LinearLayout)findViewById(R.id.loding_view);
        relativeLayout=(RelativeLayout)findViewById(R.id.detail_view);
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


    manager = getSupportFragmentManager();
        MiddleFragment middleFragment = MiddleFragment.newInstance();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.frame, middleFragment);
        fragmentTransaction.commit();


    }
    public void _visibleProgressBar()
    {
        linearLayout.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);
    }
    public void _hideProgressBar()
    {
        linearLayout.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onFragmentInteraction(View view) {

        if (view.getId() == R.id.button) {

            Call<FetchWordList> fetchWordListCall= RetrofitObject.getRetrofitObject().getList("051aa347","a4477df5e09ac56de5f14f8657c86bf2");
            fetchWordListCall.enqueue(this);
         _visibleProgressBar();


        }else if (view.getId() == R.id.button2) {

            Button2Fragment fragment1 = Button2Fragment.newInstance();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment1);
            fragmentTransaction.commit();

        } else if (view.getId() == R.id.button3) {

            Button3Fragment fragment1 = Button3Fragment.newInstance();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment1);
            fragmentTransaction.commit();

        } else if (view.getId() == R.id.button4) {

            Button4Fragment fragment1 = Button4Fragment.newInstance();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment1);
            fragmentTransaction.commit();
        }
        else if (view.getId() == R.id.games) {

            GameFragment fragment1 = GameFragment.newInstance();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment1);
            fragmentTransaction.commit();
        }
        else if(view.getId()==R.id.grammer)
        {
            Intent intent=new Intent(MainActivity.this,VoiceRecogniser.class);
            startActivity(intent);

        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.homebutton)
        {
            MiddleFragment middleFragment = MiddleFragment.newInstance();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, middleFragment);
            fragmentTransaction.commit();

        }
        if(v.getId()==R.id.searchbutton)
        {
            Call<FetchWordList> fetchWordListCall= RetrofitObject.getRetrofitObject().getList("051aa347","a4477df5e09ac56de5f14f8657c86bf2");
            fetchWordListCall.enqueue(this);
            _visibleProgressBar();
        }
        if(v.getId()==R.id.sharebutton)
        {
            Button4Fragment fragment1 = Button4Fragment.newInstance();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment1);
            fragmentTransaction.commit();
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

    @Override
    public void onResponse(Call<FetchWordList> call, Response<FetchWordList> response) {

FetchWordList list=response.body();
for(WordList list1:list.wordLists)
{
list2.add(list1);
}
        Button1Fragment fragment1 = Button1Fragment.newInstance(list2);
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment1);
        fragmentTransaction.commit();
        _hideProgressBar();



    }

    @Override
    public void onFailure(Call<FetchWordList> call, Throwable t) {

    }

    @Override
    public void onFragmentInteraction(List<String> s,String WordName,String WordCategory) {
        DefinitionsFragment fragment1 = DefinitionsFragment.newInstance(s,WordName,WordCategory);
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment1);
        fragmentTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            speech.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);


        }
    }
}