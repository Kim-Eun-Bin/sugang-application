package com.tpj.teamproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class MypageActivity extends AppCompatActivity {
    private TextView ddayText;
    private TextView todayText;
    private TextView resultText;
    private Button dateButton;
    private static final String TAG = "MypageActivity";
    private int tYear;           // 현재 연월일 변수
    private int tMonth;
    private int tDay;

    private int dYear=1;        //디데이 연월일 변수
    private int dMonth=1;
    private int dDay=1;


    private long d;
    private long t;
    private long r;

    private int resultNumber=0;
    private int result_second = 0;
    static final int DATE_DIALOG_ID=0;
    private  int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        Button btn4 = (Button) findViewById(R.id.btn4);
        Button btn5 = (Button) findViewById(R.id.btn5);
        //ProgressBar pgBar = (ProgressBar) findViewById(R.id.progressBar);


        ddayText=(TextView)findViewById(R.id.dday);
        todayText=(TextView)findViewById(R.id.today);
        resultText=(TextView)findViewById(R.id.result);
        dateButton=(Button)findViewById(R.id.dateButton);

        dateButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(0);//----------------
            }
        });

        Calendar calendar =Calendar.getInstance();              // 현재 날짜 설정
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar dCalendar =Calendar.getInstance();
        dCalendar.set(dYear,dMonth, dDay);

        t = calendar.getTimeInMillis();                 // 현재 날짜를 밀리타임으로 바꿈
        d = dCalendar.getTimeInMillis();              // 디데이날짜를 밀리타임으로 바꿈
        r = (d-t)/(24*60*60*1000);                 // 디데이 날짜에서 개강일 날짜를 뺀 값을 '일'단위로 바꿈

        resultNumber=(int)r+1;


        updateDisplay();
      //  Toast.makeText(getApplicationContext(),resultNumber,Toast.LENGTH_SHORT).show();

    }

    public void onClick1(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClick2(View view) {
        Intent intent = new Intent(this, SugangActivity.class);
        startActivity(intent);
    }

    public void onClick3(View view) {
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }

    public void onClick4(View view) {
        Intent intent = new Intent(this, MypageActivity.class);
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

    private void updateDisplay(){

        todayText.setText(String.format("%d년 %d월 %d일",tYear, tMonth+1,tDay));
        ddayText.setText(String.format("%d년 %d월 %d일",dYear, dMonth+1,dDay));

        if(resultNumber>=0){
            resultText.setText(String.format("D-%d", resultNumber));
            count =1;
        }
        else{
            int absR=Math.abs(resultNumber);
            resultText.setText(String.format("D+%d", absR));
            count = 0;
        }

    }//디데이 날짜가 오늘날짜보다 뒤에오면 '-', 앞에오면 '+'를 붙인다

    private DatePickerDialog.OnDateSetListener dDateSetListener=new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            dYear=year;
            dMonth=monthOfYear;
            dDay=dayOfMonth;
            final Calendar dCalendar =Calendar.getInstance();
            dCalendar.set(dYear,dMonth, dDay);

            d=dCalendar.getTimeInMillis();
            r=(d-t)/(24*60*60*1000);

            resultNumber=(int)r;
            updateDisplay();
        }
    };


    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DATE_DIALOG_ID){
            return new DatePickerDialog(this,dDateSetListener,tYear,tMonth,tDay);
        }
        return null;
    }
}


