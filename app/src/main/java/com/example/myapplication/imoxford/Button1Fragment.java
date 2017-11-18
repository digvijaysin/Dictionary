package com.example.myapplication.imoxford;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Button1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Button1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Button1Fragment extends Fragment implements Callback<GetWordMeaning>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
    private OnFragmentInteractionListener mListener;
public String WordName;
    public String WordCategory;
    public Button1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Button1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Button1Fragment newInstance(List<WordList> list) {
        Button1Fragment fragment = new Button1Fragment();
       // args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
wordList=list;
        list2=list;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_button1, container, false);
        AlertDialog.Builder alertDialogBuider=new AlertDialog.Builder(v.getContext());
        alertDialogBuider.setView(R.layout.fore_ground_layout);
        alertDialog=alertDialogBuider.create();
        alertDialogBuider.setCancelable(false);
        searchView=(SearchView)v.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                WordName=query;
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
        RecyclerView recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(List<String> s,String WordName,String WordCategory) {
        if (mListener != null) {
            mListener.onFragmentInteraction(s,WordName,WordCategory);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(List<String> s,String WordName,String WordCategory);
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
        Call<GetWordMeaning> fetchWordListCall = RetrofitObject.getRetrofitObject().getWord("051aa347", "a4477df5e09ac56de5f14f8657c86bf2", WordId);
        fetchWordListCall.enqueue(this);
    }
    @Override
    public void onResponse(Call<GetWordMeaning> call, Response<GetWordMeaning> response) {
        if (response.code() == 200) {
            List<String> List = new ArrayList<>();

            GetWordMeaning meaning = response.body();

            for (GetWordMeaning.LexicalEntries lexicalEntries : meaning.lexiclaEntries) {
               WordCategory= lexicalEntries.getWordType();
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
            alertDialog.dismiss();
            onButtonPressed(List, WordName,WordCategory);

        }
        else if(response.code()==404)
        {
            Toast.makeText(getContext(),"Word Not Found!",Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
        }
    }


    @Override
    public void onFailure(Call<GetWordMeaning> call, Throwable t) {

    }

    }
