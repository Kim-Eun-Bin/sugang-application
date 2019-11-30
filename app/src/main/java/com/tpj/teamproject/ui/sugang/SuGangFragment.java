package com.tpj.teamproject.ui.sugang;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tpj.teamproject.R;
import com.tpj.teamproject.controller.database.SuGang;

import java.util.ArrayList;


public class SuGangFragment extends Fragment {
    private static String DB_MSC = "msc";
    private static String DB_MAJOR = "major";
    private static String DB_SUPER = "super";

    private String currentDB;

    private View mView;

    private RecyclerView sugangListRecyclerView;
    private LinearLayoutManager sugangListLayoutManager;
    private SuGangListAdapter noCompleteAdapter;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private TextView textMajor, textMSC, textSuper, textTotalScore, textReset;

    private double totalScore = 0.0;
    private double totalTime = 0.0;

    private boolean scoreFlag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_su_gang, container, false);

        String uid = getActivity().getSharedPreferences("uid",Context.MODE_PRIVATE).getString("uid",null);

        if(uid == null){ return mView; }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("user").child(uid);

        textMajor = mView.findViewById(R.id.text_sugang_major);
        textMSC = mView.findViewById(R.id.text_sugang_msc);
        textSuper = mView.findViewById(R.id.text_sugang_super);
        textTotalScore = mView.findViewById(R.id.text_sugang_total_score);
        textReset = mView.findViewById(R.id.text_reset);

        boolean isFirst = getActivity().getSharedPreferences("isFirst",Context.MODE_PRIVATE).getBoolean("isFirst",true);
        if(isFirst){
            initUserDatabase();
        }
        currentDB = DB_MAJOR;
        getList(DB_MAJOR);
        setToTalScore();
        textMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDB = DB_MAJOR;
                getList(DB_MAJOR);
            }
        });

        textMSC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                currentDB = DB_MSC;
                getList(DB_MSC);
            }
        });

        textSuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDB = DB_SUPER;
                getList(DB_SUPER);
            }
        });

        textReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initUserDatabase();
                updateUI();
            }
        });

        return mView;
    }

    private void initUserDatabase(){
        final ArrayList<SuGang> listMajor = SugangManager.initMajorList();
        final ArrayList<SuGang> listMsc = SugangManager.initMSCList();
        final ArrayList<SuGang> listSuper = SugangManager.initSuper_RefinementList();

        for(SuGang sugang : listMajor){
            reference.child(DB_MAJOR).child(sugang.name).setValue(sugang.toMap());
        }

        for(SuGang sugang : listMsc){
            reference.child(DB_MSC).child(sugang.name).setValue(sugang.toMap());
        }

        for(SuGang sugang : listSuper){
            reference.child(DB_SUPER).child(sugang.name).setValue(sugang.toMap());
        }

        getActivity().getSharedPreferences("isFirst",Context.MODE_PRIVATE).edit().putBoolean("isFirst",false).apply();
    }

    private ArrayList<SuGang> getList(String where){
        final ArrayList<SuGang>list = new ArrayList<>();
        reference.child(where).orderByChild("semester").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sugangListRecyclerView = mView.findViewById(R.id.recycler_yet_sugang_list);
                sugangListRecyclerView.setHasFixedSize(true);

                sugangListLayoutManager = new LinearLayoutManager(getContext());
                sugangListRecyclerView.setLayoutManager(sugangListLayoutManager);

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    SuGang sugang = postSnapshot.getValue(SuGang.class);
                    list.add(sugang);
                }

                noCompleteAdapter = new SuGangListAdapter(SuGangFragment.this,list);
                sugangListRecyclerView.setAdapter(noCompleteAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    /**
    * static으로 선언된 DB_MSC, DB_MAJOR, DB_SUPER 도 같이 가져댜가 밑에 함수 응용해서 홈 화면에 가져다가 쓰면 됩니다.
    * 파이어베이스 작동 방식이 비동기로 진행되기 때문에 onDataChange에서 ui 변경하는 작업도 같이 해줘야 합니다.
    *
    * @author 고승화
    * */
    public void setToTalScore(){
        //if(!scoreFlag) return;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalScore = 0;
                totalTime = 0;

                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MAJOR).getChildren()){
                    SuGang sugang = postSnapshot.getValue(SuGang.class);
                    if(sugang.isComplete){
                        totalScore += (sugang.grade*sugang.time);
                        totalTime += (sugang.time);
                    }
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MSC).getChildren()){
                    SuGang sugang = postSnapshot.getValue(SuGang.class);
                    if(sugang.isComplete){
                        totalScore += sugang.grade*sugang.time;
                        totalTime += sugang.time;
                    }
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_SUPER).getChildren()){
                    SuGang sugang = postSnapshot.getValue(SuGang.class);
                    if(sugang.isComplete){
                        totalScore += sugang.grade*sugang.time;
                        totalTime += sugang.time;
                    }
                }
                if(textTotalScore != null){
                    totalScore = totalScore / totalTime;
                    textTotalScore.setText(String.format("%.2f", totalScore));
                    scoreFlag = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateUI(){
        for(SuGang sugang : noCompleteAdapter.mDataset){
            reference.child(currentDB).child(sugang.name).setValue(sugang.toMap());
        }
        setToTalScore();
        scoreFlag = true;
    }
}
