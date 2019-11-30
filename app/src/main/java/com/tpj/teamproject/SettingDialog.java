package com.tpj.teamproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class SettingDialog extends DialogFragment {
    private int m_time = 0;

    private int tYear;           // 현재 연월일 변수
    private int tMonth;
    private int tDay;

    private int dYear=1;        //디데이 연월일 변수
    private int dMonth=1;
    private int dDay=1;

    private long d,t,r;

    String result = "";

    private int resultNumber=0;

    OnMyDialogResult mDialogResult;

    private  int count = 1;

    private HomeFragment context;
    public SettingDialog(HomeFragment context){
        this.context = context;
        Calendar calendar =Calendar.getInstance();              // 현재 날짜 설정
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar dCalendar =Calendar.getInstance();
        dCalendar.set(dYear,dMonth, dDay);

        t = calendar.getTimeInMillis();                 // 현재 날짜를 밀리타임으로 바꿈
        d = dCalendar.getTimeInMillis();              // 디데이날짜를 밀리타임으로 바꿈
        r = (d-t)/(24*60*60*1000);                 // 디데이 날짜에서 개강일 날짜를 뺀 값을 '일'단위로 바꿈


    }

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
    public Dialog onCreateDialog(Bundle bundle){

        resultNumber=(int)r+1;

        return new DatePickerDialog(context.getContext(),dDateSetListener,tYear,tMonth,tDay);
    }

    private void updateDisplay(){
        if(resultNumber>=0){
            result = String.format("D-%d", resultNumber);
            count =1;
        }
        else{
            int absR=Math.abs(resultNumber);
            result = String.format("D+%d", absR);
            count = 0;
        }

    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;

    }

    public interface OnMyDialogResult{
        void finish(String result);
    }

}
