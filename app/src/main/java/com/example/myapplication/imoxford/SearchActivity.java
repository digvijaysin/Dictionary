package com.example.myapplication.imoxford;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class SearchActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static List<WordList> list2=new ArrayList<>();
    static List<WordList> wordList=new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SearchView searchView;
    RecycleAdapter adapter;
    public String WordName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AlertDialog.Builder alertDialogBuider=new AlertDialog.Builder(this);
        alertDialogBuider.setView(R.layout.fore_ground_layout);
        alertDialog=alertDialogBuider.create();
        alertDialogBuider.setCancelable(false);
        searchView=(SearchView)findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getWordMeaning(query);
                alertDialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText=newText.toLowerCase();
                List<WordList> newList=new ArrayList<>();
                for(WordList blogDetailsModalClass:wordList)
                {
                    if(blogDetailsModalClass.getWord().toLowerCase().contains(newText)) {
                        newList.add(blogDetailsModalClass);

                    }
                }
                adapter.SetFilter(newList);


                return false;
            }
        });
        adapter=new RecycleAdapter(list2);
    }
    public class  RecycleAdapter extends RecyclerView.Adapter<RecycleViewHolder>
    {
        public RecycleAdapter(List<WordList> list)
        {
            list2=list;
        }
        Animation animation;
        @Override
        public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context=parent.getContext();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.recler_view,parent,false);
            return new RecycleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecycleViewHolder holder, int position) {


            holder.textView.setText(list2.get(position).getWord());
            holder.wordId(list2.get(position).getWordId());

        }

        @Override
        public int getItemCount() {
            return list2.size();
        }
        public void SetFilter(List<WordList> list)
        {
            list2=new ArrayList<>();
            list2.addAll(list);
            notifyDataSetChanged();
        }
    }
    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        String WordId="";
        TextView textView;
        public RecycleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.text);
            textView.setOnClickListener(this);
        }
        public void wordId(String s)
        {
            WordId=s;
        }

        @Override
        public void onClick(View v) {
            WordName=WordId.toUpperCase();
            getWordMeaning(WordId);
            alertDialog.show();
        }


    }

    public  void getWordMeaning(String WordId) {

    }


}

