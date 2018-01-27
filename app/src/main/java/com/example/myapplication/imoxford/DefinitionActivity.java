package com.example.myapplication.imoxford;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DefinitionActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static List<String> list2=new ArrayList<>();
  private   static String WordName;
    SearchView searchView;
    TextView Word;
    RecycleAdapter recycleAdapter;
    TextView WordCategory;
    static String wordCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        AlertDialog.Builder alertDialogBuider=new AlertDialog.Builder(this);
        alertDialogBuider.setView(R.layout.fore_ground_layout);
        alertDialog=alertDialogBuider.create();
        alertDialogBuider.setCancelable(false);
        Word=(TextView)findViewById(R.id.word_name);
        WordCategory=(TextView)findViewById(R.id.word_category);

        ImageView imageView=(ImageView)findViewById(R.id.speak_word);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        searchView=(SearchView)findViewById(R.id.search_view1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Word.setText(query.toUpperCase());
               // getWordMeaning(query);
                alertDialog.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        recycleAdapter=new RecycleAdapter();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.definition_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recycleAdapter);

    }

    public static Intent getDefinitionActivityIntent(Context c,String WordKey)
    {
        Intent intent=new Intent(c,DefinitionActivity.class);
        WordName=WordKey;
        list2.clear();
        setWordDefinition();

return intent;
    }
    public static  void setWordDefinition()
    {
        
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
            Toast.makeText(v.getContext(),WordId.substring(13),Toast.LENGTH_LONG).show();

           // onButtonPressed(WordId.substring(13));
        }
    }
   /* public  void getWordMeaning(String WordId) {
        Call<GetWordMeaning> fetchWordListCall = RetrofitObject.getRetrofitObject().getWord("051aa347", "a4477df5e09ac56de5f14f8657c86bf2", WordId);
        fetchWordListCall.enqueue(this);
    }
    @Override
    public void onResponse(Call<GetWordMeaning> call, Response<GetWordMeaning> response) {
        List<String> List=new ArrayList<>();


        GetWordMeaning meaning=response.body();

        for( GetWordMeaning.LexicalEntries lexicalEntries:meaning.lexiclaEntries)
        {

            WordCategory.setText("( "+  lexicalEntries.getWordType()+" )");
            for(GetWordMeaning.Entries entries:lexicalEntries.entries)
            {
                for(GetWordMeaning.Sense sense:entries.sense)
                {
                    for(GetWordMeaning.Definitions definitions:sense.definitions)
                    {
                        for(String s:definitions.definitions)
                        {
                            List.add(s);
                        }
                    }
                }
            }
        }
        list2.clear();
        list2=List;
        recycleAdapter.notifyDataSetChanged();
        alertDialog.dismiss();

    }



    @Override
    public void onFailure(Call<GetWordMeaning> call, Throwable t) {

    }*/



}
