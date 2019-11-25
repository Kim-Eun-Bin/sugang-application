package com.tpj.teamproject;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tpj.teamproject.controller.SuGangDTO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class MyPageFragment extends Fragment {
    View mView;
/*
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

*/



    // Inflate the layout for this fragment

    //ProgressBar pgBar = (ProgressBar) findViewById(R.id.progressBar);

       /* switch1 = mView.findViewById(R.id.switch1);
        ddayText = mView.findViewById(R.id.dday);
        todayText = mView.findViewById(R.id.today);
        resultText = mView.findViewById(R.id.result);
        dateButton = mView.findViewById(R.id.dateButton);

        dateButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //showDialog(0);//----------------
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
                }
                saveData();
            }
        });
        loadDate();
        updateViews();*/



    private static String DB_MSC = "msc";
    private static String DB_MAJOR = "major";
    private static String DB_SUPER = "super";

    boolean finishFlag = false;

    ArrayList<SuGangDTO> listMajor = new ArrayList<>();
    ArrayList<SuGangDTO> listMsc= new ArrayList<>();
    ArrayList<SuGangDTO> listSuper = new ArrayList<>();

    //1.안들은거 2.학년학기 3.전공,MSC,교양

    EditText editScore;
    TextView textSave, textRecomend;
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        uid = getActivity().getSharedPreferences("uid", Context.MODE_PRIVATE).getString("uid",null);

        mView =  inflater.inflate(R.layout.fragment_my_page, container, false);
        editScore = mView.findViewById(R.id.edit_my_page_score);
        textSave = mView.findViewById(R.id.text_my_page_score_save);
        textRecomend = mView.findViewById(R.id.text_my_page_recomend);

        textSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(uid).child("config").child("goal_score");
                ref.setValue(Double.valueOf(editScore.getText().toString()));
                Toast.makeText(getContext(), "목표 학점이 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase.getInstance().getReference("user").child(uid).child("config").child("goal_score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(editScore != null)
                    editScore.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        textRecomend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finishFlag){
                    String str = "전공 " ;
                    for(SuGangDTO s : recomendList()){
                        if(recomendList().get(0).semester == s.semester)
                            str += s.name + ",";
                    }
                    textRecomend.setText(str);
                }
            }
        });

        setToTalScore();
        getNoComplete();

        return mView;
    }


    public void getNoComplete(){
        FirebaseDatabase.getInstance().getReference()
                .child("user").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MAJOR).getChildren()){
                    SuGangDTO suGangDTO = postSnapshot.getValue(SuGangDTO.class);
                        listMajor.add(suGangDTO);
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MSC).getChildren()){
                    SuGangDTO suGangDTO = postSnapshot.getValue(SuGangDTO.class);
                        listMsc.add(suGangDTO);
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_SUPER).getChildren()){
                    SuGangDTO suGangDTO = postSnapshot.getValue(SuGangDTO.class);
                        listSuper.add(suGangDTO);
                }
                finishFlag = true;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public ArrayList<SuGangDTO> recomendList(){
        ArrayList<SuGangDTO> result = new ArrayList<>();

        for(SuGangDTO suGangDTO : listMajor){
            if(!suGangDTO.isComplete){
                result.add(suGangDTO);
            }
        }
        Collections.sort(result);
        return result;

    }


    public void setToTalScore(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(uid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double totalScore = 0;
                double totalTime = 0;

                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MAJOR).getChildren()){
                    SuGangDTO sugang = postSnapshot.getValue(SuGangDTO.class);
                    if(sugang.isComplete){
                        totalScore += (sugang.grade*sugang.time);
                        totalTime += (sugang.time);
                    }
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_MSC).getChildren()){
                    SuGangDTO sugang = postSnapshot.getValue(SuGangDTO.class);
                    if(sugang.isComplete){
                        totalScore += sugang.grade*sugang.time;
                        totalTime += sugang.time;
                    }
                }
                for(DataSnapshot postSnapshot : dataSnapshot.child(DB_SUPER).getChildren()){
                    SuGangDTO sugang = postSnapshot.getValue(SuGangDTO.class);
                    if(sugang.isComplete){
                        totalScore += sugang.grade*sugang.time;
                        totalTime += sugang.time;
                    }
                }
                updateTotalScore(totalScore,totalTime);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void updateTotalScore(double totalScore, double totalTime){
        double total = totalScore / totalTime;

    }
}

/*
    public void saveData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences .Editor editor = sharedPreferences.edit();

        editor.putInt(ALARM_TIME, m_time);
        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();
    }

    public void loadDate(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
        m_time = sharedPreferences.getInt(ALARM_TIME, 0);
    }

    public void updateViews(){
        switch1.setChecked(switchOnOff);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                    Toast.makeText(getContext(), "alarm set in" + m_time + "seconds", Toast.LENGTH_SHORT).show();
                }else{
                    //항목을 선택하세요 안내
                    Toast.makeText(getContext(), "항목을 선택하세요", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        AlarmManager am = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 1000, pendingIntent);
        Toast.makeText(getContext(), "Alarm set in" + time + "seconds", Toast.LENGTH_SHORT).show();
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
*/
/*
    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DATE_DIALOG_ID){
            return new DatePickerDialog(this,dDateSetListener,tYear,tMonth,tDay);
        }
        return null;
    }*/

