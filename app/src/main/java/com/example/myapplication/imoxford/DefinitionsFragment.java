package com.example.myapplication.imoxford;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DefinitionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DefinitionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefinitionsFragment extends Fragment implements Callback<GetWordMeaning>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
AlertDialog alertDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
  static  List<String> list2=new ArrayList<>();
static String WordName;
    SearchView searchView;
    TextView Word;
    RecycleAdapter recycleAdapter;
    TextView WordCategory;
    static String wordCategory;


    private OnFragmentInteractionListener mListener;

    public DefinitionsFragment() {
        // Required empty public constructor
    }
    public static DefinitionsFragment newInstance(List<String> list,String s,String WordCategory) {
        DefinitionsFragment fragment = new DefinitionsFragment();
       WordName=s;
        wordCategory=WordCategory;
        list2.clear();
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
        View view=inflater.inflate(R.layout.fragment_definitions, container, false);
        AlertDialog.Builder alertDialogBuider=new AlertDialog.Builder(view.getContext());
        alertDialogBuider.setView(R.layout.fore_ground_layout);
        alertDialog=alertDialogBuider.create();
        alertDialogBuider.setCancelable(false);
Word=(TextView) view.findViewById(R.id.word_name);
        WordCategory=(TextView)view.findViewById(R.id.word_category);
        WordCategory.setText("( "+wordCategory+" )");
        Word.setText(WordName);
        ImageView imageView=(ImageView)view.findViewById(R.id.speak_word);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(Word.getText().toString());

            }
        });
        searchView=(SearchView)view.findViewById(R.id.search_view1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Word.setText(query.toUpperCase());
                getWordMeaning(query);
                alertDialog.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        recycleAdapter=new RecycleAdapter();
                RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.definition_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(recycleAdapter);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String s) {
        if (mListener != null) {
            mListener.onFragmentInteraction(s);
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
        void onFragmentInteraction(String s);
    }
    public class  RecycleAdapter extends RecyclerView.Adapter<DefinitionsFragment.RecycleViewHolder>
    {
        Animation animation;
        @Override
        public DefinitionsFragment.RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context=parent.getContext();
            animation= AnimationUtils.loadAnimation(context,
                    R.anim.move);
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.definition_recyclerview,parent,false);
            return new DefinitionsFragment.RecycleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DefinitionsFragment.RecycleViewHolder holder, int position) {


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

            onButtonPressed(WordId.substring(13));
    }
}
    public  void getWordMeaning(String WordId) {
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

    }

}
