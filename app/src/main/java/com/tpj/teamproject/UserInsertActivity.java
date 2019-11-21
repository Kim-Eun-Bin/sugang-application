package com.tpj.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class UserInsertActivity extends AppCompatActivity {
    String[] courses = {"C언어프로그래밍","공학입문설계","컴퓨터공학세미나1","객체지향프로그래밍1","C언어연습",
            "객체지향프로그래밍2","자료구조","컴퓨터하드웨어","컴퓨터공학세미나2","웹프로그래밍","고급객체지향프로그래밍",
            "팀프로젝트1","컴퓨터공학콜로키움","공개SW실무","컴퓨터네트워크","데이터베이스","운영체제","소프트웨어공학",
            "시스템분석및설계","컴퓨터아키텍쳐","팀프로젝트2","IT커뮤니케이션","알고리즘","시스템프로그래밍","임베디드시스템",
            "데이터베이스설계","프로그래밍언어","자기주도학습1","컴퓨터보안","캡스톤디자인1","컴퓨터그래픽스","컴퓨터공학특론1",
            "블록체인","시스템보안","SW아키텍쳐","자기주도학습2","인턴쉽1","캡스톤디자인2","영상컴퓨팅","컴퓨터공학특론2",
            "네트워크컴퓨팅","인공지능","모바일프로그래밍","클라우드컴퓨팅","인턴쉽2","IBM현장실무교육","소프트웨어와창업",
            "ICT학점이수인턴제1","ICT학점이수인턴제2","ICT학점이수인턴제3"};



    RecyclerView completeRecyclerView, noCompleteRecyclerView;
    LinearLayoutManager completeLayoutManager, noCompleteLayoutManager;
    CompleteListAdapter completeAdapter;
    NoCompleteListAdapter noCompleteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_insert);
        setComplete();
        setNoComplete();
        completeAdapter.noAdapter = noCompleteAdapter;
        noCompleteAdapter.compAdapter = completeAdapter;

    }

    private ArrayList<String> getStringArrayPref(){
        SharedPreferences prefs = getSharedPreferences("sugang",Context.MODE_PRIVATE);
        String json = prefs.getString("sugang",null);
        ArrayList<String> source = new ArrayList<>();
        if(json != null){
            try{
                JSONArray a = new JSONArray(json);
                 for(int i = 0; i< a.length(); i++){
                     String url = a.optString(i);
                     source.add(url);
                 }
            }catch (JSONException e){
            }
        }
        return source;
    }

    private void setComplete(){
        completeRecyclerView = findViewById(R.id.recycler_user_complete);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        completeRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        completeLayoutManager = new LinearLayoutManager(this);
        completeRecyclerView.setLayoutManager(completeLayoutManager);

        // specify an adapter (see also next example)

        ArrayList<String> list =  getStringArrayPref();
        completeAdapter = new CompleteListAdapter(list,this);

        completeRecyclerView.setAdapter(completeAdapter);
    }

    private void setNoComplete(){
        noCompleteRecyclerView = findViewById(R.id.recycler_user_no_complete);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        noCompleteRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        noCompleteLayoutManager = new LinearLayoutManager(this);
        noCompleteRecyclerView.setLayoutManager(noCompleteLayoutManager);

        // specify an adapter (see also next example)
        noCompleteAdapter = new NoCompleteListAdapter(createComputerEngList());
        noCompleteRecyclerView.setAdapter(noCompleteAdapter);
    }

    ArrayList<String> createComputerEngList(){
        ArrayList<String> arrayList = new ArrayList<>();
        for(String course : courses ){
            arrayList.add(course);
        }
        return arrayList;
    }

    public void saveData(ArrayList<String> source){
        SharedPreferences prefs = getSharedPreferences("sugang", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArray = new JSONArray();
        for(String s : source){
            jsonArray.put(s);
        }
        if(!source.isEmpty()){
            editor.putString("sugang", jsonArray.toString());
        }else{
            editor.putString("sugang",null);
        }
        editor.apply();
    }
}
