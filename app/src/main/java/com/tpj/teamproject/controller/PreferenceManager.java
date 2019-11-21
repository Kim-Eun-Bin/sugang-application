package com.tpj.teamproject.controller;

import android.app.Activity;
import android.content.SharedPreferences;

public class PreferenceManager {
    static public void saveAuth(Activity activity, String id, String pw) {
        SharedPreferences auth = activity.getSharedPreferences("auth", Activity.MODE_PRIVATE);
        SharedPreferences.Editor authEditor = auth.edit();
        authEditor.putString("inputID",id);
        authEditor.putString("inputPW",pw);
        authEditor.commit();
    }

    static public void setFirstCome(Activity activity){
        SharedPreferences pref = activity.getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isFirst",true);
        editor.commit();
    }
}