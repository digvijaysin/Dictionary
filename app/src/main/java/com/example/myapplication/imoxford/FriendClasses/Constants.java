package com.example.myapplication.imoxford.FriendClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harshit on 27-01-2018.
 */

public class Constants {
    public static HashMap<String,String> hashMap;
    public static ArrayList<String> keyList;
    public static List<String> sortList;

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
    public static int binarySearch(String x) {
        if(x=="")
            return -1;
        ArrayList<String> a=keyList;
        int low = 0;
        int high = a.size() - 1;
        int mid;
        int lenIndex=x.length();
        while (low <= high) {
            mid = (low + high) / 2;
            int compareValue;
            if(lenIndex+1<=a.get(mid).length())
                compareValue=(a.get(mid).substring(0,lenIndex)).compareTo(x);
            else
                compareValue=(a.get(mid)).compareTo(x);
            if (compareValue < 0) {
                low = mid + 1;
            } else if (compareValue > 0) {
                high = mid - 1;
            } else {
                int bin=binarySearch(x+" ");
                if(bin==-1)
                    return mid;
                else
                    return bin;
            }
        }

        return -1;
    }
}
