package com.tpj.teamproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tpj.teamproject.controller.ParseLogin;
import com.tpj.teamproject.controller.PreferenceManager;

public class LoginManager  extends AsyncTask<Void,Void,Void> {
    protected Activity activity;
    protected String id, pw;

    public LoginManager(Activity activity, String id, String pw){
        this.activity = activity;
        this.id = id;
        this.pw = pw;
        execute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        final ParseLogin parseLogin = ParseLogin.getInstance(id,pw);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(parseLogin.loginCheck()){
                    PreferenceManager.saveAuth(activity,id,pw);
                    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(id+"@mju.ac.kr",pw)
                            .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        PreferenceManager.saveUID(activity,task.getResult().getUser().getUid());

                                        Log.d("firebase login","아이디 생성 성공");
                                        //mFragment.finish();
                                    }else{
                                        Log.d("firebase login","아이디 생성 실패");
                                    }
                                    activity.startActivity(new Intent(activity, MainActivity.class));
                                }
                            });
                }else{
                    Toast.makeText(activity, "로그인 인증에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        return null;
    }
}
