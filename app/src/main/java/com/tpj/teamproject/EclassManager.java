package com.tpj.teamproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

public class EclassManager extends AsyncTask<Void,Void,Void> {
    Fragment activity;
    String id, pw;

    ProgressDialog asyncDialog;
    public EclassManager(Fragment activity, String id, String pw){
        this.id = id;
        this.pw = pw;
        this.activity = activity;
        execute();
    }

    @Override
    protected void onPreExecute() {
        asyncDialog = new ProgressDialog(activity.getContext());
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("로그인 중 입니다~");
        asyncDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        final ParseEclass eclass = new ParseEclass(id,pw);
        eclass.init();


        //courses = (HashMap<String, Course>) getActivity().getIntent().getSerializableExtra("courses");


        activity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((HomeFragment)activity).setCourse(eclass.getCourse());
            }
        });



        //activity.startActivity(new Intent(activity.getContext(),EclassCourseActivity.class).putExtra("courses",eclass.getCourse()));
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        asyncDialog.dismiss();
        super.onPostExecute(result);
    }
}