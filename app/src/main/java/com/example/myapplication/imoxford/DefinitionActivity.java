package com.example.myapplication.imoxford;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.imoxford.FriendClasses.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefinitionActivity extends AppCompatActivity implements Callback<GetWordMeaning> {
    AlertDialog alertDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextToSpeech textToSpeech;
    static List<String> list2=new ArrayList<>();
    private static String wordName;
    SearchView searchView;
    TextView Word;
    RecycleAdapter recycleAdapter;
    TextView WordCategory;
    static int context;
    static String wordCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                    textToSpeech.setSpeechRate(1/2);

                }
            }
        });
        AlertDialog.Builder alertDialogBuider=new AlertDialog.Builder(this);
        alertDialogBuider.setView(R.layout.fore_ground_layout);
        alertDialog=alertDialogBuider.create();
        alertDialogBuider.setCancelable(false);
        Word=(TextView)findViewById(R.id.word_name);
        WordCategory=(TextView)findViewById(R.id.word_category);

        ImageView imageView=(ImageView)findViewById(R.id.speak_word);
        Word.setText(wordName);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakYourWord(wordName);

            }
        });
        searchView=(SearchView)findViewById(R.id.search_view1);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(context==1)
        {
            getWordMeaningFromServer(wordName);
            alertDialog.show();
        }
        recycleAdapter=new RecycleAdapter();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.definition_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recycleAdapter);

    }

    public static Intent getDefinitionActivityIntent(Context c,String WordKey,int Count)
    {
        context=Count;
        Intent intent=new Intent(c,DefinitionActivity.class);
        wordName=WordKey;
        list2.clear();
        if(Count==0) {
            setWordDefinition();
        }
        return intent;
    }
    public static  void setWordDefinition() {
        String s= Constants.hashMap.get(wordName);
        int index=3;
        String tempStr="";
        int len=s.length();
        for(int i=3;i<len-2;i++){
            if(i+4<len && (s.substring(i,i+4)).equals("', '")){
                list2.add(tempStr);
                Log.d("Harshit",tempStr);
                tempStr="";
                i+=4;
            }
            else{
                tempStr+=s.charAt(i);
            }
        }
        if(!tempStr.equals("")){
            list2.add(tempStr);
            Log.d("Harshit",tempStr);
        }
    }
    public class  RecycleAdapter extends RecyclerView.Adapter<RecycleViewHolder>
    {
        Animation animation;
        @Override
        public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context=parent.getContext();
            animation= AnimationUtils.loadAnimation(context,
                    R.anim.move);
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.definition_recyclerview,parent,false);
            return new RecycleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecycleViewHolder holder, int position) {


            holder.textView.setText(list2.get(position));
            holder.setText(holder.textView.getText().toString());



        }

        @Override
        public int getItemCount() {
            return list2.size();
        }
    }
    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        String WordId="";
        TextView textView;
        public RecycleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.word_meaning);
            textView.setOnClickListener(this);
        }
        public void setText(String s)
        {
            WordId=s;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),WordId,Toast.LENGTH_LONG).show();

           speakYourWord(WordId);
        }
    }
    public   void getWordMeaningFromServer(String WordId) {
        Call<GetWordMeaning> fetchWordListCall = RetrofitObject.getRetrofitObject().getWord("051aa347", "a4477df5e09ac56de5f14f8657c86bf2", WordId);
        fetchWordListCall.enqueue(this);
    }
    @Override
    public void onResponse(Call<GetWordMeaning> call, Response<GetWordMeaning> response) {
        List<String> List = new ArrayList<>();

        if (response.code() != 404) {
            GetWordMeaning meaning = response.body();

            for (GetWordMeaning.LexicalEntries lexicalEntries : meaning.lexiclaEntries) {
                for (GetWordMeaning.Entries entries : lexicalEntries.entries) {
                    for (GetWordMeaning.Sense sense : entries.sense) {
                        for (GetWordMeaning.Definitions definitions : sense.definitions) {
                            for (String s : definitions.definitions) {
                                List.add(s);
                            }
                        }
                    }
                }
            }
            list2.clear();
            list2 = List;
            recycleAdapter.notifyDataSetChanged();
            alertDialog.dismiss();

        } else {
            Toast.makeText(getApplicationContext(), "The Word that you want to search is not found. Please check your word ", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
            finish();
        }
    }



    @Override
    public void onFailure(Call<GetWordMeaning> call, Throwable t) {

    }
    public void speakYourWord(String Word)
    {
        if(textToSpeech.isSpeaking())
        {
            textToSpeech.stop();
        }
        textToSpeech.speak(Word, TextToSpeech.QUEUE_ADD, null);
    }


    @Override
    protected void onPause() {
        super.onPause();
        textToSpeech.shutdown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.shutdown();
    }
}
