package com.tpj.teamproject.controller;

import java.util.ArrayList;

public class SugangManager {
    public static ArrayList<SuGangDTO> initMajorList(){
        ArrayList<SuGangDTO> list = new ArrayList<>();
        //                 과목명     학점(점수) 학점(시간)
        list.add(new SuGangDTO("C언어프로그래밍",-1,4,11));
        list.add(new SuGangDTO("공학입문설계",-1,3,11));
        list.add(new SuGangDTO("컴퓨터공학세미나1",-1,1,11));
        list.add(new SuGangDTO("객체지향프로그래밍1",-1,4,12));
        list.add(new SuGangDTO("C언어연습",-1,1,12));
        list.add(new SuGangDTO("객체지향프로그래밍2",-1,3,21));
        list.add(new SuGangDTO("자료구조",-1,3,21));
        list.add(new SuGangDTO("컴퓨터하드웨어",-1,3,21));
        list.add(new SuGangDTO("컴퓨터공학세미나2",-1,1,21));
        list.add(new SuGangDTO("웹프로그래밍",-1,3,22));
        list.add(new SuGangDTO("고급객체지향프로그래밍",-1,3,22));
        list.add(new SuGangDTO("팀프로젝트1",-1,3,22));
        list.add(new SuGangDTO("컴퓨터공학콜로키움",-1,1,22));
        list.add(new SuGangDTO("공개SW실무",-1,3,22));
        list.add(new SuGangDTO("컴퓨터네트워크",-1,3,31));
        list.add(new SuGangDTO("데이터베이스",-1,3,31));
        list.add(new SuGangDTO("운영체제",-1,3,31));
        list.add(new SuGangDTO("소프트웨어공학",-1,3,31));
        list.add(new SuGangDTO("시스템분석및설계",-1,3,31));
        list.add(new SuGangDTO("컴퓨터아키텍쳐",-1,3,31));
        list.add(new SuGangDTO("팀프로젝트2",-1,3,31));
        list.add(new SuGangDTO("IT커뮤니케이션",-1,2,51));
        list.add(new SuGangDTO("알고리즘",-1,3,32));
        list.add(new SuGangDTO("시스템프로그래밍",-1,3,32));
        list.add(new SuGangDTO("임베디드시스템",-1,3,32));
        list.add(new SuGangDTO("데이터베이스설계",-1,3,32));
        list.add(new SuGangDTO("프로그래밍언어",-1,3,32));
        list.add(new SuGangDTO("자기주도학습1",-1,2,32));
        list.add(new SuGangDTO("컴퓨터보안",-1,3,32));
        list.add(new SuGangDTO("캡스톤디자인1",-1,3,41));
        list.add(new SuGangDTO("컴퓨터그래픽스",-1,3,41));
        list.add(new SuGangDTO("컴퓨터공학특론1",-1,3,41));
        list.add(new SuGangDTO("블록체인",-1,3,41));
        list.add(new SuGangDTO("시스템보안",-1,3,41));
        list.add(new SuGangDTO("SW아키텍쳐",-1,3,41));
        list.add(new SuGangDTO("자기주도학습2",-1,2,41));
        list.add(new SuGangDTO("인턴쉽1",-1,3,41));
        list.add(new SuGangDTO("캡스톤디자인2",-1,3,42));
        list.add(new SuGangDTO("영상컴퓨팅",-1,3,42));
        list.add(new SuGangDTO("컴퓨터공학특론2",-1,3,42));
        list.add(new SuGangDTO("네트워크컴퓨팅",-1,3,42));
        list.add(new SuGangDTO("인공지능",-1,3,42));
        list.add(new SuGangDTO("모바일프로그래밍",-1,3,42));
        list.add(new SuGangDTO("클라우드컴퓨팅",-1,3,42));
        list.add(new SuGangDTO("인턴쉽2",-1,3,42));
        list.add(new SuGangDTO("IBM현장실무교육",-1,12,42));
        list.add(new SuGangDTO("소프트웨어와창업",-1,3,51));
        list.add(new SuGangDTO("ICT학점이수인턴제1",-1,12,51));
        list.add(new SuGangDTO("ICT학점이수인턴제1",-1,4,51));
        list.add(new SuGangDTO("ICT학점이수인턴제2",-1,3,51));

        return list;
    }

    public static ArrayList<SuGangDTO> initMSCList(){
        ArrayList<SuGangDTO> list = new ArrayList<>();
        //                 과목명     학점(점수) 학점(시간)
        list.add(new SuGangDTO("미적분학1",-1,3,11));
        list.add(new SuGangDTO("공학수학1",-1,3,21));
        list.add(new SuGangDTO("통계학개론",-1,3,12));
        list.add(new SuGangDTO("선형대수학개론",-1,3,22));
        list.add(new SuGangDTO("이산수학개론",-1,3,12));
        list.add(new SuGangDTO("물리학1",-1,3,11));
        list.add(new SuGangDTO("물리학실험1",-1,1,11));

        return list;
    }

    public static ArrayList<SuGangDTO> initSuper_RefinementList(){
        ArrayList<SuGangDTO> list = new ArrayList<>();
        //                 과목명     학점(점수) 학점(시간)
        list.add(new SuGangDTO("글쓰기",-1,3,51));
        list.add(new SuGangDTO("발표와토의",-1,3,51));
        list.add(new SuGangDTO("영어1",-1,2,11));
        list.add(new SuGangDTO("영어2",-1,2,12));
        list.add(new SuGangDTO("영어3",-1,2,11));
        list.add(new SuGangDTO("영어4",-1,2,12));
        list.add(new SuGangDTO("영어회화1",-1,1,11));
        list.add(new SuGangDTO("영어회화2",-1,1,12));
        list.add(new SuGangDTO("영어회화3",-1,1,11));
        list.add(new SuGangDTO("영어회화4",-1,1,12));
        list.add(new SuGangDTO("철학과인간",-1,3,51));
        list.add(new SuGangDTO("한국근현대사의이해",-1,3,51));
        list.add(new SuGangDTO("역사와문명",-1,3,51));
        list.add(new SuGangDTO("세계화와사회변화",-1,3,51));
        list.add(new SuGangDTO("민주주의와현대사회",-1,3,51));
        list.add(new SuGangDTO("첨단과학의이해",-1,3,51));
        list.add(new SuGangDTO("환경과인간",-1,3,51));
        list.add(new SuGangDTO("창업인문",-1,3,51));
        list.add(new SuGangDTO("글로벌문화",-1,3,51));
        list.add(new SuGangDTO("고전으로읽는인문학",-1,3,51));
        list.add(new SuGangDTO("여성소수자공동체",-1,3,51));
        list.add(new SuGangDTO("예술과창조성",-1,3,51));
        list.add(new SuGangDTO("인공지능의세계",-1,3,51));
        list.add(new SuGangDTO("4차산업혁명의이해",-1,3,51));
        list.add(new SuGangDTO("4차산업혁명을위한비판적사고와비판",-1,3,51));


        return list;
    }
}
