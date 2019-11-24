package com.tpj.teamproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    int number;
    String title;
    ArrayList<CourseTime> times;
    String url;

    private class CourseTime implements Serializable{
        String day;
        String time;

        public CourseTime(String day, String time){
            this.day = day;
            this.time = time;
        }

        @Override
        public String toString(){
            return "::"+day+"::"+time+"\n";
        }
    }

    public Course(int number, String title, String time, String url){
        this.title = title;
        this.number = number;
        this.times = new ArrayList<>();
        this.url = url;
        this.addTime(time);
    }

    public void addTime(String src){
        String day = src.substring(0,1).trim();
        String time = src.substring(2).trim();

        this.times.add(new CourseTime(day,time));
    }

    @Override
    public String toString(){
        String str = "["+title+"]";
        for(CourseTime time : times){
            str += (times.toString() + "\n");
        }
        return str;
    }
}