package com.tpj.teamproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tpj.teamproject.ui.calendar.CalendarFragment;
import com.tpj.teamproject.ui.home.HomeFragment;
import com.tpj.teamproject.ui.mypage.MyPageFragment;
import com.tpj.teamproject.R;
import com.tpj.teamproject.ui.sugang.SuGangFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView buttonHome;
    private ImageView buttonMyPage;
    private ImageView buttonSuGang;
    private ImageView buttonCalendar;

    private Fragment homeFragment, sugangFragment, mypageFragment, CalendarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonHome = findViewById(R.id.main_button_home);
        buttonMyPage = findViewById(R.id.main_button_mypage);
        buttonSuGang = findViewById(R.id.main_button_sugang);
        buttonCalendar = findViewById(R.id.main_calendar_button);

        buttonHome.setOnClickListener(this);
        buttonMyPage.setOnClickListener(this);
        buttonSuGang.setOnClickListener(this);
        buttonCalendar.setOnClickListener(this);

        homeFragment = new HomeFragment();
        sugangFragment = new SuGangFragment();
        mypageFragment = new MyPageFragment();
        CalendarFragment = new CalendarFragment();


        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment,homeFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment,mypageFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment,sugangFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment,CalendarFragment).commit();

        getSupportFragmentManager().beginTransaction().show(homeFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(sugangFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(CalendarFragment).commit();

        //getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,homeFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_button_home:
                getSupportFragmentManager().beginTransaction().show(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(sugangFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(CalendarFragment).commit();
                break;
            case R.id.main_button_mypage:
                mypageFragment = new MyPageFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.main_fragment,mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().show(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(sugangFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(CalendarFragment).commit();
                break;
            case R.id.main_button_sugang:

                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().show(sugangFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(CalendarFragment).commit();
                break;
            case R.id.main_calendar_button:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(sugangFragment).commit();
                getSupportFragmentManager().beginTransaction().show(CalendarFragment).commit();
                break;
        }
    }
}
