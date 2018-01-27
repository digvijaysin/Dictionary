package com.example.myapplication.imoxford.FriendClasses;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Harshit on 27-01-2018.
 */

public class Constants {
    public static HashMap<String,String> hashMap;
    public static ArrayList<String> keyList;

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
