package com.tpj.teamproject.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.tpj.teamproject.R;

public class CalendarFragment extends Fragment {
    public View mView;
    private  static final String TAG = "AlarmActivity";
    private CalendarView mCalenderView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                final Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_calendar, container, false);

        mCalenderView = (CalendarView)mView.findViewById(R.id.calendarView);
        mCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String data = year + "." + month + "." + dayOfMonth;
                Log.d(TAG, "onSelectedDayChange: yyyy.mm.dd: "+data);

                Intent intent = new Intent(getContext(),MemoActivity.class);
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });

        return mView;
    }
}
