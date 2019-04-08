package fr.etudes.prepademoihm.models;

import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * If you decide to use MVC design pattern,
 * this is the Model (buisiness class) for the APP
 * all methods are static, so this class is a singleton !
 */
public class Kindergarten {
    public static final String TEAM1 = "one";
    public static final String TEAM2 = "two";
    private static List<Pair<String,String>> completeList = new ArrayList<>();


    public static void init (){
        Log.d("PERSON", "init()");
        completeList.clear();
        completeList.add(new Pair(TEAM1, "marco"));
        completeList.add(new Pair(TEAM1, "eric"));
        completeList.add(new Pair(TEAM1, "noé"));
        completeList.add(new Pair(TEAM1, "james"));
        completeList.add(new Pair(TEAM1, "jules"));
        completeList.add(new Pair(TEAM1, "bill"));
        completeList.add(new Pair(TEAM1, "ugo"));
        completeList.add(new Pair(TEAM2, "marie"));
        completeList.add(new Pair(TEAM2, "julia"));
        completeList.add(new Pair(TEAM2, "luna"));
        completeList.add(new Pair(TEAM2, "émilie"));
        completeList.add(new Pair(TEAM2, "karine"));
        completeList.add(new Pair(TEAM2, "chloé"));
        completeList.add(new Pair(TEAM2, "emma"));

    }

    public static void add(String team, String name) {
        if ( team.equals(TEAM1) || team.equals(TEAM2) ) {
            completeList.add(new Pair(team, name));
        }
    }

    public static List<String> getListOne() {
        List<String> listOne = new ArrayList<>();
        for (Pair child : completeList) {
            if (child.first.equals(TEAM1)) listOne.add((String)(child.second));
        }
        return listOne;
    }

    public static List<String> getListTwo() {
        List<String> listTow= new ArrayList<>();
        for (Pair child : completeList) {
            if (child.first.equals(TEAM2)) listTow.add((String)(child.second));
        }
        return listTow;
    }

    public static int sizeOfTwo() {
       int size = 0;
        for (Pair person : completeList) {
            if (person.first.equals(TEAM2)) size++;
        }
        return size;
    }

    public static int sizeOfOne() {
        int size = 0;
        for (Pair person : completeList) {
            if (person.first.equals(TEAM1)) size++;
        }
        return size;
    }

    public static int size() {
        return completeList.size();
    }
}
