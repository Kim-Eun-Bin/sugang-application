package com.tpj.teamproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class HomeFragment extends Fragment {
    public View mView;

    public TextView name;

    public RecyclerView cRecyclerView;
    public RecyclerView.LayoutManager cLayoutManager;
    public EclassCourseAdapter  cAdapter;
    public TextView noCourse;



    private ProgressBar finalProgressBar;
    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        finalProgressBar = mView.findViewById(R.id.main_progress_final);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = "2019-09-02";
        String currentDate = format.format((Calendar.getInstance().getTime()));
        String endDate = "2019-12-20";

        long data1 = calculateDate(format,currentDate,endDate);
        long data2 = calculateDate(format,startDate,endDate);
        long data3 = (data1*100/data2);

        final String id = getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE).getString("inputID",null);
        final String pw = getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE).getString("inputPW",null);

        new EclassManager(this,id,pw);

        noCourse = mView.findViewById(R.id.no_course);


        finalProgressBar.setProgress(100-(int)data3);
        return mView;
    }

    public void setCourse(HashMap<String, Course> courses){
        cAdapter = new EclassCourseAdapter(this,courses);

        cRecyclerView = mView.findViewById(R.id.course_recycler);
        cRecyclerView.setHasFixedSize(true);

        cLayoutManager = new LinearLayoutManager(getContext());

        cRecyclerView.setLayoutManager(cLayoutManager);
        cRecyclerView.setAdapter(cAdapter);

        if(courses.isEmpty()){
            cRecyclerView.setVisibility(View.GONE);
            noCourse.setVisibility(View.VISIBLE);
        }
    }



    private long calculateDate(SimpleDateFormat format, String start, String end){
        Date FirstDate = null;
        Date SecondDate = null;
        try {
            FirstDate = format.parse(start);
            SecondDate = format.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long calDate = FirstDate.getTime() - SecondDate.getTime();

        long calDateDays = calDate / ( 24*60*60*1000);

        if(calDateDays < 0){
            return -calDateDays;
        }
        return calDateDays;
    }
}
