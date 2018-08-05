package com.mtw.diego.jokesonthego.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.mtw.diego.jokesonthego.helper.database.AppDatabase;
import com.mtw.diego.jokesonthego.helper.network.JokesService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Utils {
    private static String TAG = "JokesUtils";

    public static void applyPreferences(Context c) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(c);
        HashSet<String> dHost = new HashSet<String>();
        dHost.add("0");
        HashSet<String> hosts = (HashSet<String>) SP.getStringSet("selected_hosts", dHost);
        int maxBuffer = Integer.parseInt(SP.getString("max_buffer", "50"));
        int jokesOld = Integer.parseInt(SP.getString("max_read", "3"));
        AppDatabase.MAX_NEW_JOKES = maxBuffer;
        AppDatabase.JOKES_OLD = jokesOld;
        JokesService.HOSTS = Utils.getValues(hosts);
    }

    public static int countIntegers(Set<String> vals) {
        int c = 0;
        Iterator<String> it = vals.iterator();
        while (it.hasNext()) {
            if (isNumeric(it.next())) {
                ++c;
            }
        }
        return c;
    }

    public static ArrayList<Integer> getValues(Set<String> vals) {
        ArrayList<Integer> vs = new ArrayList<>();

        Iterator<String> it = vals.iterator();
        while (it.hasNext()) {
            String t = it.next();
            if (isNumeric(t)) {
                vs.add(Integer.parseInt(t));
            }
        }

        return vs;
    }

    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}
