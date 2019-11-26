package com.tpj.teamproject.controller;

import java.util.HashMap;
import java.util.Map;

public class SuGangDTO implements Comparable<SuGangDTO>{

    public String name;
    public double grade;
    public int time;
    public boolean isComplete;
    public int semester;

    SuGangDTO(){}

    /**
     * @param semester 1학년 1학기 -> 11 1학년 2학기 -> 12 2학년 1학기 -> 21 //해당안되는 것 -> 51 ...
     *
     * */
    SuGangDTO(String name, double grade, int time, int semester){
        this.name = name;
        this.grade = grade;
        this.time = time;
        this.isComplete = false;
        this.semester = semester;
    }

    void setGrade(){
        this.grade = grade;
    }

    public Map<String,  Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("grade",this.grade);
        map.put("time",this.time);
        map.put("isComplete",this.isComplete);
        map.put("semester",this.semester);
        return map;
    }

    @Override
    public int compareTo(SuGangDTO o) {
        if(this.semester < o.semester){
            return -1;
        }else if(this.semester > o.semester){
            return 1;
        }else{
            return 0;
        }
    }
}
