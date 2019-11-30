package com.tpj.teamproject.controller;

import android.app.Activity;
import android.content.SharedPreferences;

public class PreferenceManager {
    static public void saveAuth(Activity activity, String id, String pw) {
        SharedPreferences auth = activity.getSharedPreferences("auth", Activity.MODE_PRIVATE);
        SharedPreferences.Editor authEditor = auth.edit();
        authEditor.putString("inputID",id);
        authEditor.putString("inputPW",pw);
        authEditor.apply();
    }

    public static void saveUID(Activity activity, String uid) {
        SharedPreferences pref = activity.getSharedPreferences("uid", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("uid",uid);
        editor.apply();
    }
}