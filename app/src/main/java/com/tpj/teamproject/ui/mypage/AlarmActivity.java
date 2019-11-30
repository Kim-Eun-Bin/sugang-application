package com.tpj.teamproject.ui.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tpj.teamproject.R;
import com.tpj.teamproject.ui.MainActivity;
import com.tpj.teamproject.ui.home.HomeFragment;

public class AlarmActivity extends AppCompatActivity {
    Button main_btn, exit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        main_btn = (Button) findViewById(R.id.main_btn);
        exit_btn = (Button) findViewById(R.id.exit_btn);
    }

    public void Main_Active(View view) {
        Intent intent = new Intent(this, HomeFragment.class);
        startActivity(intent);
    }

    public void Exit_Active(View view) {
        finish();
    }
}
