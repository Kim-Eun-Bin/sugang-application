package com.tpj.teamproject.controller;

import java.util.HashMap;
import java.util.Map;

public class SuGangDTO {
    public String name;
    public double grade;
    public int time;
    public boolean isComplete;

    SuGangDTO(){}

    SuGangDTO(String name, double grade, int time){
        this.name = name;
        this.grade = grade;
        this.time = time;
        this.isComplete = false;
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
        return map;
    }
}
