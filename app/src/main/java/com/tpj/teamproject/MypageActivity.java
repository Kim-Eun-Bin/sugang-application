package com.tpj.teamproject;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MypageActivity extends AppCompatActivity {
    private int m_time = 0;
    private TextView ddayText;
    private TextView todayText;
    private TextView resultText;
    private Button dateButton;
    private Switch switch1;
    private boolean switchOnOff;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static  final String SWITCH1 = "switch1";
    public static final String ALARM_TIME = "alarmtime";

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

        switch1 = (Switch) findViewById(R.id.switch1);
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
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmWork(m_time);
                } else {

                }
                saveData();
            }
        });
        loadDate();
        updateViews();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences .Editor editor = sharedPreferences.edit();

        editor.putInt(ALARM_TIME, m_time);
        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();
    }

    public void loadDate(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
        m_time = sharedPreferences.getInt(ALARM_TIME, 0);
    }

    public void updateViews(){
        switch1.setChecked(switchOnOff);
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
        Intent intent = new Intent(this, CalendarActivity.class);
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

    public void onClick6(View view) {
        final List<String> Items = new ArrayList<>();
        Items.add("15분전");
        Items.add("10분전");
        Items.add("5분전");
        Items.add("3분전");
        Items.add("1분전");
        final String[] items = Items.toArray(new String[Items.size()]);

        final List selectedItems = new ArrayList();
        int defaultItem = 0;
        selectedItems.add(defaultItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ucheck 알람 시간 설정");
        builder.setSingleChoiceItems(items, defaultItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItems.clear();
                selectedItems.add(which);
            }
        });
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!selectedItems.isEmpty()){
                    int index = (int) selectedItems.get(0);
                    String msg = items[index];
                    if(msg.equals("15분전")){
                        m_time = 15;
                    }else if(msg.equals("10분전")){
                        m_time = 10;
                    }else if(msg.equals("5분전")){
                        m_time = 5;
                    }else if(msg.equals("3분전")){
                        m_time = 3;
                    }else if(msg.equals("1분전")){
                        m_time = 1;
                    }
                    //설정된 시간 안내 Toast
                    Toast.makeText(MypageActivity.this, "alarm set in" + m_time + "seconds", Toast.LENGTH_SHORT).show();
                }else{
                    //항목을 선택하세요 안내
                    Toast.makeText(MypageActivity.this, "항목을 선택하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void alarmWork(int time){
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 1000, pendingIntent);
        Toast.makeText(MypageActivity.this, "Alarm set in" + time + "seconds", Toast.LENGTH_SHORT).show();
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


