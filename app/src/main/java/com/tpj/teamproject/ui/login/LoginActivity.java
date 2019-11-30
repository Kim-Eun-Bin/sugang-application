package com.tpj.teamproject.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tpj.teamproject.R;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        SharedPreferences pref = getSharedPreferences("auth",Context.MODE_PRIVATE);
        usernameEditText.setText(pref.getString("inputID",""));
        passwordEditText.setText(pref.getString("inputPW",""));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameEditText.getText().length()==0){
                    Toast.makeText(LoginActivity.this, "학번을 입력 해 주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwordEditText.getText().length()==0){
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력 해 주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String id = usernameEditText.getText().toString();
                String pw = passwordEditText.getText().toString();
                new LoginManager(LoginActivity.this,id,pw);


            }
        });
    }
}
