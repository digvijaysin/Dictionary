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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.imoxford.FriendClasses.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class SearchActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static ArrayList<String> list2 = new ArrayList<>();
    static ArrayList<String> wordList = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SearchView searchView;
    RecycleAdapter adapter;
    public String SearchWord;
    public String WordName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(this);
        alertDialogBuider.setView(R.layout.fore_ground_layout);
        alertDialog = alertDialogBuider.create();
        alertDialogBuider.setCancelable(false);
        searchView = (SearchView) findViewById(R.id.search_view);
        adapter = new RecycleAdapter(Constants.keyList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText=Constants.toTitleCase(newText);
                ArrayList<String> newList=new ArrayList<>();
                if(newText.equals(""))
                {
                    newList.addAll(Constants.sortList);
                }
                else {
                    Log.d("Harshit", "Before Binary Search");
                    int startIndex = Constants.binarySearch(newText);
                    Log.d("Harshit", "After Binary Search");
                    int endIndex=newText.length()-1;
                    if (startIndex != -1) {
                        char alphabet = newText.charAt(endIndex);
                        alphabet += 1;
                        int keyListSize = Constants.keyList.size();
                        Log.d("Harshit", "start index not -1");
                        for (int i = startIndex; i < keyListSize; i++) {
                            try{
                                char c = Constants.keyList.get(i).charAt(endIndex);
                                if(c <= alphabet)
                                    newList.add(Constants.keyList.get(i));
                                else
                                    break;
                            }catch (Exception e){

                            }
                        }
                    }
                    if (newList.isEmpty()) {
                        newList.add("Word Not in List Click to Search online");
                        SearchWord = newText;
                    }
                }
                adapter.SetFilter(newList);


                return false;
            }
        });
        // adapter=new RecycleAdapter(Constants.keyList);
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleViewHolder> {
        public RecycleAdapter(ArrayList<String> list) {
            list2 = list;
        }
        Animation animation;

        @Override
        public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.recler_view, parent, false);
            return new RecycleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecycleViewHolder holder, int position) {


            holder.textView.setText(list2.get(position));
            holder.wordId(list2.get(position));

        }

        @Override
        public int getItemCount() {
            return list2.size();
        }

        public void SetFilter(ArrayList<String> list) {
            list2 = new ArrayList<>();
            list2.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        String WordId = "";
        TextView textView;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            textView.setOnClickListener(this);
        }

        public void wordId(String s) {
            WordId = s;
        }

        @Override
        public void onClick(View v) {
            WordName = WordId.toUpperCase();
            getWordMeaning(WordId);
            //alertDialog.show();
        }


    }

    public void getWordMeaning(String WordId) {
        if (WordId.equals("Word Not in List Click to Search online")) {

            Intent intent = DefinitionActivity.getDefinitionActivityIntent(getApplicationContext(), SearchWord,1);
            startActivity(intent);
        } else {
            Intent intent = DefinitionActivity.getDefinitionActivityIntent(getApplicationContext(), WordId,0);
            startActivity(intent);
        }
    }
}

