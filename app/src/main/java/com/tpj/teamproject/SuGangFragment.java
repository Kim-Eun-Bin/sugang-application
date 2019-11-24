package com.tpj.teamproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tpj.teamproject.controller.SuGangDTO;
import com.tpj.teamproject.controller.SugangManager;

import java.util.ArrayList;


public class SuGangFragment extends Fragment {
    private static String DB_MSC = "msc";
    private static String DB_MAJOR = "major";
    private static String DB_SUPER = "super";

    String currentDB;

    View mView;

    RecyclerView sugangListRecyclerView;
    LinearLayoutManager sugangListLayoutManager;
    SuGangListAdapter noCompleteAdapter;

    FirebaseDatabase database;
    DatabaseReference reference;

    TextView textMajor, textMSC, textSuper, textTotalScore;
    Button buttonSave;

    double totalScore = 0.0;
    double totalTime = 0.0;

    boolean scoreFlag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_su_gang, container, false);

        String uid = getActivity().getSharedPreferences("uid",Context.MODE_PRIVATE).getString("uid",null);

        if(uid == null){ return mView; }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("user").child(uid);

        buttonSave = mView.findViewById(R.id.btn_sugang_save);
        textMajor = mView.findViewById(R.id.text_sugang_major);
        textMSC = mView.findViewById(R.id.text_sugang_msc);
        textSuper = mView.findViewById(R.id.text_sugang_super);
        textTotalScore = mView.findViewById(R.id.text_sugang_total_score);

        boolean isFirst = getActivity().getSharedPreferences("isFirst",Context.MODE_PRIVATE).getBoolean("isFirst",true);
        if(isFirst){
            initUserDatabase();
        }

        getList(DB_MAJOR);
        setToTalScore();
        textMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList(DB_MAJOR);
            }
        });

        textMSC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getList(DB_MSC);
            }
        });

        textSuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList(DB_SUPER);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI();
            }

        });
        return mView;
    }

    public void initUserDatabase(){
        final ArrayList<SuGangDTO> listMajor = SugangManager.initMajorList();
        final ArrayList<SuGangDTO> listMsc = SugangManager.initMSCList();
        final ArrayList<SuGangDTO> listSuper = SugangManager.initSuper_RefinementList();

        for(SuGangDTO sugang : listMajor){
            System.out.println(sugang.name);
            reference.child(DB_MAJOR).child(sugang.name).setValue(sugang.toMap());
        }
        for(SuGangDTO sugang : listMsc){
            System.out.println(sugang.name);
            reference.child(DB_MSC).child(sugang.name).setValue(sugang.toMap());
        }
        for(SuGangDTO sugang : listSuper){
            System.out.println(sugang.name);
            reference.child(DB_SUPER).child(sugang.name).setValue(sugang.toMap());
        }
        getActivity().getSharedPreferences("isFirst",Context.MODE_PRIVATE).edit().putBoolean("isFirst",false).apply();
    }

    public ArrayList<SuGangDTO> getList(String where){
        currentDB = where;
        final ArrayList<SuGangDTO>list = new ArrayList<>();
        reference.child(where).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sugangListRecyclerView = mView.findViewById(R.id.recycler_yet_sugang_list);
                sugangListRecyclerView.setHasFixedSize(true);

                sugangListLayoutManager = new LinearLayoutManager(getContext());
                sugangListRecyclerView.setLayoutManager(sugangListLayoutManager);

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    SuGangDTO sugang = postSnapshot.getValue(SuGangDTO.class);
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

    public void setToTalScore(){
        if(!scoreFlag) return;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalScore = 0;
                totalTime = 0;

                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MAJOR).getChildren()){
                    SuGangDTO sugang = postSnapshot.getValue(SuGangDTO.class);
                    if(sugang.isComplete){
                        totalScore += (sugang.grade*sugang.time);
                        totalTime += (sugang.time);
                    }
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MSC).getChildren()){
                    SuGangDTO sugang = postSnapshot.getValue(SuGangDTO.class);
                    if(sugang.isComplete){
                        totalScore += sugang.grade*sugang.time;
                        totalTime += sugang.time;
                    }
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_SUPER).getChildren()){
                    SuGangDTO sugang = postSnapshot.getValue(SuGangDTO.class);
                    if(sugang.isComplete){
                        totalScore += sugang.grade*sugang.time;
                        totalTime += sugang.time;
                    }
                }
                System.out.println("total : "+totalScore);
                if(textTotalScore != null){
                    totalScore = totalScore / totalTime;
                    textTotalScore.setText(String.valueOf(totalScore));
                    scoreFlag = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateUI(){
        for(SuGangDTO sugang : noCompleteAdapter.mDataset){
            reference.child(currentDB).child(sugang.name).setValue(sugang.toMap());
        }
        setToTalScore();
        scoreFlag = true;
    }
}
