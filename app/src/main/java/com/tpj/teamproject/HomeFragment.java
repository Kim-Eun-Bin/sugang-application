package com.tpj.teamproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * HomeFragment : 시간표, 종강일 , 학점 이 보이게
 * SuGangFragment : 전체 과목 정보 관리, 전체 학점
 * MyPageFragment : 유저의 졸업 목표 학점, (가능하다면)UCheck 알람 기능, 다음 학기 추천 수강 과목
 *
 * MyPage는 구현 안했습니다. 유저의 졸업 목표 학점 저장 방법은 두 가지가 있는데
 * 1. SharedPreference를 이용한 로컬에 저장하거나
 * 2. Firebase에 유저의 uid 하단에 새로운 폴더 ( 이름 config 추천 ) 로 따로 저장 시킵니다.
 *
 * 다음 학기 수강 추천 과목은 순서를 정해 두고,
 * 1. 전공, 교양, MSC 과목 중 학점이 부족한 것이 아닌지 확인 시키고
 * 2. 졸업 목표 학점에 못 미쳤을 때 전공 또는 교양 중 학점이 낮은 과목을 우선으로 추천 시킵니다.
 * 한 학기에 21학점이 최대인 것도 고려하면 좋을 것 같습니다.
 *
 * UCheck 알림 기능은 거의 다 구현 된 것 처럼 보이는데 테스트 때문에 일단 해당 기능은 주석 처리 해 놨습니다.
 * 시간표 기능 구현에 사용된 Course객체 안에 있는 CourseTime 객체의 정보를 활용하면 금방 만들 수 있을 것 같습니다.
 * 알람 기능은 안드로이드의 Service라는 시스템을 이용해야 합니다.
 *
 * Fragment가 계속 새로 생성되게 호출 되어 있는데 FragmentManager에 미리 하나의 인스턴스만 추가 시킨 뒤에 show 함수를 이용하면
 * HomeFragment로 갈 때마다 로그인이 계속 되는 것을 방지할 수 있습니다.
 *
 * xml 파일에서 onClick을 통해 java파일에 있는 함수를 호출하는 방식으로 버튼 리스너를 사용하셨는데 해당 방법은 추후에 어플 사용 또는 유지관리에서
 * 메모리 문제나 null 문제를 일으킬 수 있어서, findViewById로 xml 내의 객체를 먼저 찾고 후에 onclickListener를 java에서 동적으로 설정하는 방법을 추천드립니다.
 *
 * xml 파일 또는 xml 안에 있는 id의 이름 규칙은 요소종류_있는곳_기능 입니다.
 * 예를 들어 sugang Fragment 안에 있는 저장 버튼을 명명한다고 하면 button_sugang_save 처럼 되는 것이 맞습니다.
 *
 * java에서 클래스의 첫 글자는 대문자 이어야 합니다.
 *
 * 원래 폴더별로 정리가 되었어야 하는데 프로젝트가 뒤죽박죽이라 발표 전 주말 쯤에 한번 리팩토링 작업 한번 해드릴게요.
 * */


public class HomeFragment extends Fragment {
    public View mView;

    public TextView[] monday, tuesday, wednesday, thursday, friday;

    public TextView name;

    public RecyclerView cRecyclerView;
    public RecyclerView.LayoutManager cLayoutManager;
    public EclassCourseAdapter  cAdapter;
    public TextView noCourse;
    public ImageView imageSetting;

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

        noCourse = mView.findViewById(R.id.no_course);
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

    View findViewById(int resID){
       return mView.findViewById(resID);
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
