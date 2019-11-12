package com.tpj.teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        Button btn4 = (Button) findViewById(R.id.btn4);
        Button btn5 = (Button) findViewById(R.id.btn5);
    }

    public void onClick1(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onClick2(View view) {
        Intent intent = new Intent(this,SugangActivity.class);
        startActivity(intent);
    }

    public void onClick3(View view) {
        Intent intent = new Intent(this,AlarmActivity.class);
        startActivity(intent);
    }

    public void onClick4(View view) {
        Intent intent = new Intent(this,MypageActivity.class);
        startActivity(intent);
    }

    public void onClick5(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("고객센터 연결").setMessage("전화연결 하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
           public void onClick(DialogInterface dialog, int id) {
               Intent intent = new Intent();
               intent.setAction(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:01063032666"));
               startActivity(intent);
           }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
           public void onClick(DialogInterface dialog, int id)
           {
                dialog.cancel();
           }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
