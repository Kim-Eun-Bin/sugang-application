package com.tpj.teamproject.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tpj.teamproject.R;
import com.tpj.teamproject.controller.database.Course;
import com.tpj.teamproject.ui.timetable.EclassManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class HomeFragment extends Fragment {
    public View mView;

    private TextView[] monday, tuesday, wednesday, thursday, friday;



    private ImageView imageSetting;

    private ProgressBar finalProgressBar;


    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
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

        imageSetting = mView.findViewById(R.id.image_home_setting);

        initTimeTable();

        imageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SettingDialog dialog =  new SettingDialog(HomeFragment.this);
                dialog.show(getFragmentManager(),"setting");
                dialog.setDialogResult(new SettingDialog.OnMyDialogResult() {
                    @Override
                    public void finish(String result) {
                        System.out.println(result+"DDDDDD");
                        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                    }
                }) ;
            }});

        finalProgressBar.setProgress(100-(int)data3);
        return mView;
    }


    /*
    * Course 객체 안에 Course Time 이라고 과목 시간표도 함께 담겨 있습니다. run에서 로그 확인해 보시면 바로 알 수 있으니 해당 객체 응용해서
    * 시간표 완성하시면 됩니다.
    * */
    public void setCourse(HashMap<String, Course> courses){
        ArrayList<Course> courseArray = new ArrayList<Course>(courses.values());

        for(Course course : courseArray){
            System.out.println(course.title);
            for(Course.CourseTime time : course.times){
                //public void ChangeColor(String m_day, int m_start_hour, int m_end_hour, String m_name, String m_lecture, String m_place)
                int start = Integer.parseInt(time.time.substring(0,2));
                int end = Integer.parseInt(time.time.substring(8,10));
                ChangeColor(time.day,start,end+1,course.title,"","");
                System.out.println(time.day + time.time);
            }
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

    public void ChangeColor(String m_day, int m_start_hour, int m_end_hour, String m_name, String m_lecture, String m_place) {

        System.out.println("ddd" + m_start_hour +"d" + m_end_hour);
        if (m_day.contains("월")) {
            for (int i = m_start_hour - 8; i < m_end_hour - 8; i++) {

                monday[i].setText(m_lecture + "\n" + m_name + "\n" + m_place);
                monday[i].setBackgroundColor(Color.parseColor("#23779b"));
            }
        } else if (m_day.contains("화")) {
            for (int i = m_start_hour - 8; i < m_end_hour - 8; i++) {
                tuesday[i].setText(m_lecture + "\n" + m_name + "\n" + m_place);
                tuesday[i].setBackgroundColor(Color.parseColor("#23779b"));
            }
        } else if (m_day.contains("수")) {
            for (int i = m_start_hour - 8; i < m_end_hour - 8; i++) {
                wednesday[i].setText(m_lecture + "\n" + m_name + "\n" + m_place);
                wednesday[i].setBackgroundColor(Color.parseColor("#23779b"));
            }
        } else if (m_day.contains("목")) {
            for (int i = m_start_hour - 8; i < m_end_hour - 8; i++) {
                thursday[i].setText(m_lecture + "\n" + m_name + "\n" + m_place);
                thursday[i].setBackgroundColor(Color.parseColor("#23779b"));
            }
        } else if (m_day.contains("금")) {
            for (int i = m_start_hour - 8; i < m_end_hour - 8; i++) {
                friday[i].setText(m_lecture + "\n" + m_name + "\n" + m_place);
                friday[i].setBackgroundColor(Color.parseColor("#23779b"));
            }
        }
    }

    private void initTimeTable() {
        monday = new TextView[13];
        tuesday = new TextView[13];
        wednesday = new TextView[13];
        thursday = new TextView[13];
        friday = new TextView[13];

        monday[0] = mView.findViewById(R.id.monday0);
        monday[1] = mView.findViewById(R.id.monday1);
        monday[2] = mView.findViewById(R.id.monday2);
        monday[3] = mView.findViewById(R.id.monday3);
        monday[4] = mView.findViewById(R.id.monday4);
        monday[5] = mView.findViewById(R.id.monday5);
        monday[6] = mView.findViewById(R.id.monday6);
        monday[7] = mView.findViewById(R.id.monday7);
        monday[8] = mView.findViewById(R.id.monday8);
        monday[9] = mView.findViewById(R.id.monday9);
        monday[10] = mView.findViewById(R.id.monday10);
        monday[11] = mView.findViewById(R.id.monday11);
        monday[12] = mView.findViewById(R.id.monday12);

        tuesday[0] = mView.findViewById(R.id.tuesday0);
        tuesday[1] = mView.findViewById(R.id.tuesday1);
        tuesday[2] = mView.findViewById(R.id.tuesday2);
        tuesday[3] = mView.findViewById(R.id.tuesday3);
        tuesday[4] = mView.findViewById(R.id.tuesday4);
        tuesday[5] = mView.findViewById(R.id.tuesday5);
        tuesday[6] = mView.findViewById(R.id.tuesday6);
        tuesday[7] = mView.findViewById(R.id.tuesday7);
        tuesday[8] = mView.findViewById(R.id.tuesday8);
        tuesday[9] = mView.findViewById(R.id.tuesday9);
        tuesday[10] = mView.findViewById(R.id.tuesday10);
        tuesday[11] = mView.findViewById(R.id.tuesday11);
        tuesday[12] = mView.findViewById(R.id.tuesday12);

        wednesday[0] = mView.findViewById(R.id.wednesday0);
        wednesday[1] = mView.findViewById(R.id.wednesday1);
        wednesday[2] = mView.findViewById(R.id.wednesday2);
        wednesday[3] = mView.findViewById(R.id.wednesday3);
        wednesday[4] = mView.findViewById(R.id.wednesday4);
        wednesday[5] = mView.findViewById(R.id.wednesday5);
        wednesday[6] = mView.findViewById(R.id.wednesday6);
        wednesday[7] = mView.findViewById(R.id.wednesday7);
        wednesday[8] = mView.findViewById(R.id.wednesday8);
        wednesday[9] = mView.findViewById(R.id.wednesday9);
        wednesday[10] = mView.findViewById(R.id.wednesday10);
        wednesday[11] = mView.findViewById(R.id.wednesday11);
        wednesday[12] = mView.findViewById(R.id.wednesday12);

        thursday[0] = mView.findViewById(R.id.thursday0);
        thursday[1] = mView.findViewById(R.id.thursday1);
        thursday[2] = mView.findViewById(R.id.thursday2);
        thursday[3] = mView.findViewById(R.id.thursday3);
        thursday[4] = mView.findViewById(R.id.thursday4);
        thursday[5] = mView.findViewById(R.id.thursday5);
        thursday[6] = mView.findViewById(R.id.thursday6);
        thursday[7] = mView.findViewById(R.id.thursday7);
        thursday[8] = mView.findViewById(R.id.thursday8);
        thursday[9] = mView.findViewById(R.id.thursday9);
        thursday[10] = mView.findViewById(R.id.thursday10);
        thursday[11] = mView.findViewById(R.id.thursday11);
        thursday[12] = mView.findViewById(R.id.thursday12);

        friday[0] = mView.findViewById(R.id.friday0);
        friday[1] = mView.findViewById(R.id.friday1);
        friday[2] = mView.findViewById(R.id.friday2);
        friday[3] = mView.findViewById(R.id.friday3);
        friday[4] = mView.findViewById(R.id.friday4);
        friday[5] = mView.findViewById(R.id.friday5);
        friday[6] = mView.findViewById(R.id.friday6);
        friday[7] = mView.findViewById(R.id.friday7);
        friday[8] = mView.findViewById(R.id.friday8);
        friday[9] = mView.findViewById(R.id.friday9);
        friday[10] = mView.findViewById(R.id.friday10);
        friday[11] = mView.findViewById(R.id.friday11);
        friday[12] = mView.findViewById(R.id.friday12);
    }

}
