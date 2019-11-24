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

    /*
    * Course 객체 안에 Course Time 이라고 과목 시간표도 함께 담겨 있습니다. run에서 로그 확인해 보시면 바로 알 수 있으니 해당 객체 응용해서
    * 시간표 완성하시면 됩니다.
    * */
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
