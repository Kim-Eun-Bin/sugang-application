package com.tpj.teamproject;

import com.tpj.teamproject.controller.ParseLogin;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.tpj.teamproject.common.Url.ECLASS;
import static com.tpj.teamproject.common.Url.ECLASS_COURSE;

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

    public HashMap<String,Course> getCourse(){
        return getCourseHashMap(course.select("#FrameRight > table > tbody > tr"));
    }


    private ArrayList<Eclass> getHomeworkArrayList(Elements elements) {
        ArrayList<Eclass> list = new ArrayList<>();
        if(elements.text().contains("없")){
            return list;
        }
        for(Element e : elements){
            String title =  e.select("li > dl > dt > a > strong").text();
            String date = e.select("li > dl > dd.date > p > span:nth-child(2)").text();
            String info = e.select("li > dl > dd.information").text();
            String comment = e.select("li > dl > dd.comment").text();
            String href = e.select("li > dl > dt > a").attr("href");
            list.add(new Eclass(title, date, info, comment, href));
        }
        return list;
    }

    private HashMap<String,Course> getCourseHashMap(Elements elements){
        HashMap<String, Course> courses = new HashMap<>();
        if(elements.text().contains("없")){
            return courses;
        }
        for(Element e : elements){
            //int number = Integer.parseInt(e.select("td:nth-child(1)").text());
            String title = e.select("td:nth-child(2)").text();
            String time = e.select("td:nth-child(3)").text();
            String url = e.select("td:nth-child(4) > span > a").attr("href");
            Course c = new Course(0,title,time,url);

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