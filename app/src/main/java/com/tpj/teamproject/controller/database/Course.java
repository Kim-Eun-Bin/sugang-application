package com.tpj.teamproject.controller.database;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    public String title;
    public ArrayList<CourseTime> times;
    public String url;

    public class CourseTime implements Serializable{
        public String day;
        public String time;

        public CourseTime(String day, String time){
            this.day = day;
            this.time = time;
        }

        @Override
        public String toString(){
            return "::"+day+"::"+time+"\n";
        }
    }
    public Course(String title, String time, String url){
        this.title = title;
        this.times = new ArrayList<>();
        this.url = url;
        this.addTime(time);
    }

    public void addTime(String src){
        String day = src.substring(0,1).trim();
        String time = src.substring(2).trim();

        this.times.add(new CourseTime(day,time));
    }
}