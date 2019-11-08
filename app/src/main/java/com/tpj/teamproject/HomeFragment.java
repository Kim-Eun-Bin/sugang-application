package com.tpj.teamproject;

        import android.os.Bundle;

        import androidx.fragment.app.Fragment;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ProgressBar;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;


public class HomeFragment extends Fragment {
    private ProgressBar finalProgressBar;
    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        finalProgressBar = view.findViewById(R.id.main_progress_final);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = "2019-09-02";
        String currentDate = format.format((Calendar.getInstance().getTime()));
        String endDate = "2019-12-20";

        long data1 = calculateDate(format,currentDate,endDate);
        long data2 = calculateDate(format,startDate,endDate);
        long data3 = (data1*100/data2);


        finalProgressBar.setProgress(100-(int)data3);
        return view;
    }



    private long calculateDate(SimpleDateFormat format, String start, String end){
        Date FirstDate = null;
        Date SecondDate = null;
        try {
            FirstDate = format.parse(start);
            SecondDate = format.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long calDate = FirstDate.getTime() - SecondDate.getTime();

        long calDateDays = calDate / ( 24*60*60*1000);

        System.out.println("dasdsa"+calDateDays);
        if(calDateDays < 0){
            return -calDateDays;
        }
        return calDateDays;
    }
}