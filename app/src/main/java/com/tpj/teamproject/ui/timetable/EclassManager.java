package com.tpj.teamproject.ui.timetable;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import androidx.fragment.app.Fragment;

import com.tpj.teamproject.ui.home.HomeFragment;
import com.tpj.teamproject.controller.parser.ParseEclass;

public class EclassManager extends AsyncTask<Void,Void,Void> {
    private Fragment activity;
    private String id, pw;

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

        activity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((HomeFragment)activity).setCourse(eclass.getCourse());
            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        asyncDialog.dismiss();
        super.onPostExecute(result);
    }
}