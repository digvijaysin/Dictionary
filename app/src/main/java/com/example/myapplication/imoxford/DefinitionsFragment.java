package com.example.myapplication.imoxford;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
public class DefinitionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
  static  List<String> list2=new ArrayList<>();


    private OnFragmentInteractionListener mListener;

    public DefinitionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefinitionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefinitionsFragment newInstance(List<String> list) {
        DefinitionsFragment fragment = new DefinitionsFragment();
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

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.definition_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new RecycleAdapter());
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


            holder.textView.append(""+(position+1)+":- "+list2.get(position));
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

            onButtonPressed(WordId);
    }
}
}
