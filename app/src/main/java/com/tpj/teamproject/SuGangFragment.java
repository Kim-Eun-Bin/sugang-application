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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tpj.teamproject.controller.SuGangDTO;
import com.tpj.teamproject.controller.SugangManager;

import java.util.ArrayList;


public class SuGangFragment extends Fragment {
    View mView;

    RecyclerView completeRecyclerView, noCompleteRecyclerView;
    LinearLayoutManager completeLayoutManager, noCompleteLayoutManager;
    //SuGangCompleteListAdapter completeAdapter;
    SuGangListAdapter noCompleteAdapter;

    FirebaseDatabase database;
    DatabaseReference reference;

    Button buttonSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_su_gang, container, false);

        String uid = getActivity().getSharedPreferences("uid",Context.MODE_PRIVATE).getString("uid",null);


        if(uid == null){
            System.out.println("is null");
            return mView;
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("user").child(uid);

        buttonSave = mView.findViewById(R.id.btn_sugang_save);


        boolean isFirst = getActivity().getSharedPreferences("isFirst",Context.MODE_PRIVATE).getBoolean("isFirst",true);
        if(isFirst){
            final ArrayList<SuGangDTO> list = SugangManager.initSuGangList();

            for(SuGangDTO sugang : list){
                System.out.println(sugang.name);
                reference.child(sugang.name).setValue(sugang.toMap());
            }
            getActivity().getSharedPreferences("isFirst",Context.MODE_PRIVATE).edit().putBoolean("isFirst",false).apply();
        }

        //setComplete();
        setNoComplete();
        //completeAdapter.noAdapter = noCompleteAdapter;
        //noCompleteAdapter.compAdapter = completeAdapter;
        // Inflate the layout for this fragment

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(SuGangDTO sugang : noCompleteAdapter.mDataset){
                    reference.child(sugang.name).setValue(sugang.toMap());
                }
            }

        });

        return mView;
    }

    private void setNoComplete(){
        getList();
    }

    public ArrayList<SuGangDTO> getList(){
        final ArrayList<SuGangDTO>list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noCompleteRecyclerView = mView.findViewById(R.id.recycler_yet_sugang_list);
                noCompleteRecyclerView.setHasFixedSize(true);

                noCompleteLayoutManager = new LinearLayoutManager(getContext());
                noCompleteRecyclerView.setLayoutManager(noCompleteLayoutManager);

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    SuGangDTO sugang = postSnapshot.getValue(SuGangDTO.class);
                    list.add(sugang);
                }
                noCompleteAdapter = new SuGangListAdapter(list);
                noCompleteRecyclerView.setAdapter(noCompleteAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return list;
    }
}
