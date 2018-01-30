package com.example.myapplication.imoxford.AssetManagement;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.myapplication.imoxford.FriendClasses.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static com.example.myapplication.imoxford.FriendClasses.Constants.keyList;

/**
 * Created by Harshit on 27-01-2018.
 */

public class InitiateAllWords {
    public Context context;
    public InitiateAllWords(Context context){
        this.context=context;
       // initiateKeys();
        initiateWordDefinitions();
    }
    /*public void initiateKeys(){
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open("words.txt");
            Scanner sc = new Scanner(is);
            int t = sc.nextInt();
            sc.nextLine();

            while (t-- != 0) {
                keyList.add(sc.nextLine());
            }
            Constants.keyList=keyList;
        }catch(Exception e){
            Log.d("Harshit",e.toString());
        }
    }*/
    public void initiateWordDefinitions(){
        AssetManager am = context.getAssets();
                try {
                    InputStream is = am.open("wordMeanings1.txt");
                    BufferedReader br=new BufferedReader(new InputStreamReader(is));
                    ArrayList<String> keyList=new ArrayList<String>();
                    HashMap<String,String> hashMap=new HashMap<String,String>();
                    int t=Integer.parseInt(br.readLine());
                    while(t--!=0) {
                        String s = br.readLine();
                        int len = s.length(), i;
                        String word = "";
                        for (i = 0; s.charAt(i) != '@'; i++) {
                            word += s.charAt(i);
                        }
                        keyList.add(word);
                        hashMap.put(word, s.substring(i + 1,len-1));
                }
                    Constants.hashMap=hashMap;
                    Constants.keyList=keyList;
            } catch (IOException e) {
                Log.d("Harshit",e.toString());
            }
    }
}
