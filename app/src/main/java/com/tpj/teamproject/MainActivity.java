package com.tpj.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonHome;
    private Button buttonAlarm;
    private Button buttonMyPage;
    private Button buttonSuGang;

    private Fragment homeFragment, alarmFragment, sugangFragment, mypageFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        buttonHome = findViewById(R.id.main_button_home);
        buttonAlarm = findViewById(R.id.main_button_alarm);
        buttonMyPage = findViewById(R.id.main_button_mypage);
        buttonSuGang = findViewById(R.id.main_button_sugang);

        buttonHome.setOnClickListener(this);
        buttonAlarm.setOnClickListener(this);
        buttonMyPage.setOnClickListener(this);
        buttonSuGang.setOnClickListener(this);

        homeFragment = new HomeFragment();
        alarmFragment = new AlarmFragment();
        sugangFragment = new SuGangFragment();
        mypageFragment = new MyPageFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,homeFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_button_home:
                //TODO
                changeFragment(homeFragment);
                break;
            case R.id.main_button_alarm:
                //TODO
                changeFragment(alarmFragment);
                break;
            case R.id.main_button_mypage:
                //TODO
                changeFragment(mypageFragment);
                break;
            case R.id.main_button_sugang:
                //TODO
                changeFragment(sugangFragment);
                break;
        }
    }

    void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,fragment).commit();
    }
}
