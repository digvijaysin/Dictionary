package com.example.myapplication.imoxford;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MiddleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MiddleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiddleFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Animation animation;
    Animation animation1;
    Button englishtohindi;
    Button hinditoenglish;
    Button synonyms;
    Button share;
    Button game;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MiddleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MiddleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MiddleFragment newInstance() {
        MiddleFragment fragment = new MiddleFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        final View view=inflater.inflate(R.layout.fragment_fragment1, container, false);

        englishtohindi=(Button)view.findViewById(R.id.button);
        englishtohindi.setOnClickListener(this);
        hinditoenglish=(Button)view.findViewById(R.id.button2);
        hinditoenglish.setOnClickListener(this);
         synonyms=(Button)view.findViewById(R.id.button3);
        synonyms.setOnClickListener(this);
         share=(Button)view.findViewById(R.id.button4);
        share.setOnClickListener(this);
        game=(Button)view.findViewById(R.id.games);
        game.setOnClickListener(this);
        animation= AnimationUtils.loadAnimation(getContext(),
                R.anim.lefttoright);
        animation1= AnimationUtils.loadAnimation(getContext(),
                R.anim.righttoleft);
        return view;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view);
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

    @Override
    public void onClick(View v) {
        v.startAnimation(animation);
        if(v.getId()==R.id.button)
        {
         hinditoenglish.startAnimation(animation1);
         synonyms.startAnimation(animation1);
         share.startAnimation(animation1);
         game.startAnimation(animation1);
        }
        if(v.getId()==R.id.button2)
        {

        }
        if(v.getId()==R.id.button3)
        {

        }
        if(v.getId()==R.id.button4)
        {

        }
        if(v.getId()==R.id.games)
        {

        }

        onButtonPressed(v);
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
        void onFragmentInteraction(View view);
    }
}
