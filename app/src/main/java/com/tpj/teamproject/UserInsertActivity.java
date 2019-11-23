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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_insert);


    }

}
