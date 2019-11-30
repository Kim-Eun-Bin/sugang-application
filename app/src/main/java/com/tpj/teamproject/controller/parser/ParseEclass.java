package com.tpj.teamproject.controller.parser;

import com.tpj.teamproject.controller.database.Course;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

import static com.tpj.teamproject.controller.common.Url.ECLASS_COURSE;

public class ParseEclass extends ParseLogin {

    Document course;

    public ParseEclass(String id, String pw) {
        super(id,pw);
    }

    public void init(){
        if(loginCheck()){
            course = getEclassCourse();
        }
    }

    public HashMap<String, Course> getCourse(){
        return getCourseHashMap(course.select("#FrameRight > table > tbody > tr"));
    }


    private HashMap<String,Course> getCourseHashMap(Elements elements){
        HashMap<String, Course> courses = new HashMap<>();
        if(elements.text().contains("없")){
            return courses;
        }
        for(Element e : elements){
            String title = e.select("td:nth-child(2)").text();
            String time = e.select("td:nth-child(3)").text();
            String url = e.select("td:nth-child(4) > span > a").attr("href");
            Course c = new Course(title,time,url);

            if(courses.containsKey(title)){
                courses.get(title).addTime(time);
            }else{
                courses.put(title,c);
            }
        }
        return courses;
    }

    public Document getEclassCourse(){
        try{
            return super.getInstance(userID,userPW)
                    .getResponse(loginCookie,ECLASS_COURSE)
                    .parse();
        } catch (IOException e){
            return null;
        }
    }

}