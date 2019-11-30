package com.tpj.teamproject.ui.mypage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tpj.teamproject.R;
import com.tpj.teamproject.controller.database.SuGang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class MyPageFragment extends Fragment {
    View mView;


    private int m_time = 0;
    private Switch switch1;
    Button buttonSetAlarm;
    private boolean switchOnOff;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static  final String SWITCH1 = "switch1";
    public static final String ALARM_TIME = "alarmtime";

    private static String DB_MSC = "msc";
    private static String DB_MAJOR = "major";
    private static String DB_SUPER = "super";

    private static double GOAL_MAJOR_TIME  = 84;
    private static double GOAL_MSC_TIME    = 19;
    private static double GOAL_SUPER_TIME  = 21;


    boolean finishFlag = false;

    private ArrayList<SuGang> listMajor, listMsc, listSuper;

    private EditText editScore;
    private TextView textSave, textRecomend, textRemainMajor, textRemainMSC, textRemainSuper, textTotalTime, textRetake;
    private String uid;
    private RecyclerView recyclerOffer, recyclerOfferRetake;

    private LinearLayoutManager offerListLayoutManager, offerRetakeListLayoutManager;
    private SuGangOfferListAdapter offerAdapter, offerRetakeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        uid = getActivity().getSharedPreferences("uid", Context.MODE_PRIVATE).getString("uid",null);

        mView =  inflater.inflate(R.layout.fragment_my_page, container, false);
        editScore = mView.findViewById(R.id.edit_my_page_score);

        textSave = mView.findViewById(R.id.text_my_page_score_save);
        textRecomend = mView.findViewById(R.id.text_my_page_recomend);
        textRetake = mView.findViewById(R.id.text_my_page_retake);

        textRemainMajor = mView.findViewById(R.id.text_my_page_remain_major);
        textRemainMSC = mView.findViewById(R.id.text_my_page_remain_msc);
        textRemainSuper = mView.findViewById(R.id.text_my_page_remain_super);

        textTotalTime = mView.findViewById(R.id.text_my_page_total_time);
        switch1 = mView.findViewById(R.id.switch1);
        buttonSetAlarm = mView.findViewById(R.id.btn_my_page_set_alarm);

        finishFlag = false;

        textSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(uid).child("config").child("goal_score");
                ref.setValue(Double.valueOf(editScore.getText().toString()));
                Toast.makeText(getContext(), "목표 학점이 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerOffer = mView.findViewById(R.id.recycler_my_page_offer);
        recyclerOffer.setHasFixedSize(true);

        offerListLayoutManager = new LinearLayoutManager(getContext());
        recyclerOffer.setLayoutManager(offerListLayoutManager);

        recyclerOfferRetake = mView.findViewById(R.id.recycler_my_page_offer_retake);
        recyclerOfferRetake.setHasFixedSize(true);

        offerRetakeListLayoutManager = new LinearLayoutManager(getContext());
        recyclerOfferRetake.setLayoutManager(offerRetakeListLayoutManager);

        FirebaseDatabase.getInstance().getReference("user").child(uid).child("config").child("goal_score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(editScore != null && dataSnapshot.exists())
                    editScore.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarmDialog();
            }
        });

        textRecomend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finishFlag) {
                    ArrayList<SuGang> arrayList = recomendList();
                    ArrayList<SuGang> filter = new ArrayList<>();
                    if(arrayList.size() > 0){
                        for(SuGang dto : arrayList){
                            if(arrayList.get(0).semester == dto.semester)
                                filter.add(dto);
                        }
                    }else{

                    }

                    offerAdapter = new SuGangOfferListAdapter(filter);
                    recyclerOffer.setVisibility(View.VISIBLE);
                    recyclerOffer.setAdapter(offerAdapter);
                }
            }
        });

        textRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finishFlag) {
                    ArrayList<SuGang> arrayList = retakeList();
                    offerRetakeAdapter = new SuGangOfferListAdapter(arrayList);
                    recyclerOfferRetake.setVisibility(View.VISIBLE);
                    recyclerOfferRetake.setAdapter(offerRetakeAdapter);
                }
            }
        });

        setToTalScore();
        getNoComplete();

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmWork(m_time);
                }
                saveData();
            }
        });
        loadDate();
        updateViews();

        new PushAlarmManager(this, System.currentTimeMillis());

        return mView;
    }


    public void getNoComplete(){
        listMajor = new ArrayList<>();
        listMsc = new ArrayList<>();
        listSuper = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference()
                .child("user").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MAJOR).getChildren()){
                    SuGang suGang = postSnapshot.getValue(SuGang.class);
                        listMajor.add(suGang);
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MSC).getChildren()){
                    SuGang suGang = postSnapshot.getValue(SuGang.class);
                        listMsc.add(suGang);
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_SUPER).getChildren()){
                    SuGang suGang = postSnapshot.getValue(SuGang.class);
                        listSuper.add(suGang);
                }
                finishFlag = true;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public ArrayList<SuGang> recomendList(){
        ArrayList<SuGang> result = new ArrayList<>();

        for(SuGang suGang : listMajor){
            if(!suGang.isComplete) {
                result.add(suGang);
            }
        }

        for(SuGang suGang : listMsc){
            if(!suGang.isComplete) {
                result.add(suGang);
            }
        }

        for(SuGang suGang : listSuper){
            if(!suGang.isComplete) {
                result.add(suGang);
            }
        }

        Collections.sort(result);
        return result;
    }

    public ArrayList<SuGang> retakeList(){
        ArrayList<SuGang> result = new ArrayList<>();

        for(SuGang suGang : listMajor){
            if(suGang.grade <=2.5 && suGang.grade!=-1){
                result.add(suGang);
            }
        }

        for(SuGang suGang : listMsc){
            if(suGang.grade <=2.5 && suGang.grade!=-1){
                result.add(suGang);
            }
        }

        for(SuGang suGang : listSuper){
            if(suGang.grade <=2.5 && suGang.grade!=-1){
                result.add(suGang);
            }
        }
        Collections.sort(result);

        return result;
    }


    public void setToTalScore(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(uid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double scoreMajor = 0;
                double scoreMSC = 0;
                double scoreSuper = 0;
                double timeMajor = 0;
                double timeMSC = 0;
                double timeSuper = 0;

                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MAJOR).getChildren()){
                    SuGang sugang = postSnapshot.getValue(SuGang.class);
                    if(sugang.isComplete){
                        scoreMajor += (sugang.grade*sugang.time);
                        timeMajor += sugang.time;
                    }
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MSC).getChildren()){
                    SuGang sugang = postSnapshot.getValue(SuGang.class);
                    if(sugang.isComplete){
                        scoreMSC += sugang.grade*sugang.time;
                        timeMSC += sugang.time;
                    }
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_SUPER).getChildren()){
                    SuGang sugang = postSnapshot.getValue(SuGang.class);
                    if(sugang.isComplete){
                        scoreSuper += sugang.grade*sugang.time;
                        timeSuper += sugang.time;
                    }
                }
                updateTotalScore(scoreMajor,scoreMSC,scoreSuper,timeMajor,timeMSC,timeSuper);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void updateTotalScore(double scoreMajor, double scoreMSC, double scoreSuper, double timeMajor, double timeMSC, double timeSuper){
        textTotalTime.setText(timeMajor+timeMSC+timeSuper+"학점");

        textRemainMajor.setText("전공 : " +  (GOAL_MAJOR_TIME - timeMajor) + "학점");
        textRemainMSC.setText("MSC : " + (GOAL_MSC_TIME -timeMSC) + "학점");
        textRemainSuper.setText("전문교양 : " + (GOAL_SUPER_TIME - timeSuper)  + "학점");

    }
    public void saveData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences .Editor editor = sharedPreferences.edit();

        editor.putInt(ALARM_TIME, m_time);
        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();
    }

    public void loadDate(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
        m_time = sharedPreferences.getInt(ALARM_TIME, 0);
    }

    public void updateViews(){
        switch1.setChecked(switchOnOff);
    }


    public void alarmWork(int time){
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        AlarmManager am = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 1000, pendingIntent);
        Toast.makeText(getContext(), "Alarm set in" + time + "seconds", Toast.LENGTH_SHORT).show();
    }

    private void setAlarmDialog(){
        final List<String> Items = new ArrayList<>();
        Items.add("15분전");
        Items.add("10분전");
        Items.add("5분전");
        Items.add("3분전");
        Items.add("1분전");
        final String[] items = Items.toArray(new String[Items.size()]);

        final List selectedItems = new ArrayList();
        int defaultItem = 0;
        selectedItems.add(defaultItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Ucheck 알람 시간 설정");
        builder.setSingleChoiceItems(items, defaultItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItems.clear();
                selectedItems.add(which);
            }
        });
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!selectedItems.isEmpty()){
                    int index = (int) selectedItems.get(0);
                    String msg = items[index];
                    if(msg.equals("15분전")){
                        m_time = 15;
                    }else if(msg.equals("10분전")){
                        m_time = 10;
                    }else if(msg.equals("5분전")){
                        m_time = 5;
                    }else if(msg.equals("3분전")){
                        m_time = 3;
                    }else if(msg.equals("1분전")){
                        m_time = 1;
                    }
                    //설정된 시간 안내 Toast
                    Toast.makeText(getContext(), "alarm set in" + m_time + "seconds", Toast.LENGTH_SHORT).show();
                }else{
                    //항목을 선택하세요 안내
                    Toast.makeText(getContext(), "항목을 선택하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}





