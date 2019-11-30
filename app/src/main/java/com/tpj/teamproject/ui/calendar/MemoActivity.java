package com.tpj.teamproject.ui.calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tpj.teamproject.R;
import com.tpj.teamproject.ui.mypage.AlarmActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MemoActivity extends AppCompatActivity {

    private static final String TAG = "MemoActivity";
    private TextView theData;
    private Button btnGoCalendar;
    Button load, save, delete;
    EditText inputText;
    private int find_state;
    private String filename;
    private String data_filename="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        theData = (TextView) findViewById(R.id.data);
        btnGoCalendar = (Button) findViewById(R.id.btnGoCalendar);

        Intent incomingIntent = getIntent();
        String data = incomingIntent.getStringExtra("data");
        theData.setText(data);
        data_filename = data;
        load = (Button) findViewById(R.id.load);
        save = (Button) findViewById(R.id.save);
        delete = (Button) findViewById(R.id.delete);
        inputText = (EditText) findViewById(R.id.inputText);

        load.setOnClickListener(listener);
        save.setOnClickListener(listener);
        delete.setOnClickListener(listener);

        filename = data_filename;
        Log.i("TAG", filename);
        load(filename);


        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoActivity.this, CalendarFragment.class);
                startActivity(intent);
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.load:
                    Log.i("TAG", "load 진행");
                    //LinearLayout alert_layout1 = (LinearLayout) View.inflate(MemoActivity.this, R.layout.alert_layout, null);
                    //final EditText alert_edit = (EditText) alert_layout1.findViewById(R.id.search_memo);
                    new AlertDialog.Builder(MemoActivity.this)
                            .setTitle("메모 불러오기")
                            .setMessage("불러올 메모의 날짜 입력")
                            .setMessage(data_filename)

                            //.setView(alert_layout1)
                            .setPositiveButton("Load", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //filename = alert_edit.getText().toString();
                                    filename = data_filename;
                                    Log.i("TAG", filename);
                                    load(filename);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MemoActivity.this, "Load 취소", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                    break;
                case R.id.save:
                    Log.i("TAG", "save 진행");
                    //LinearLayout alert_layout2 = (LinearLayout) View.inflate(MemoActivity.this, R.layout.alert_layout, null);
                    //final EditText alert_edit2 = (EditText) alert_layout2.findViewById(R.id.search_memo);
                    if (filename == null) {
                        new AlertDialog.Builder(MemoActivity.this)
                                .setTitle("메모 저장하기")
                                .setMessage("저장할 이름")
                                .setMessage(data_filename)
                                //.setView(alert_layout2)
                                .setPositiveButton("LOAD", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // filename = alert_edit2.getText().toString();
                                        filename = data_filename;
                                        save(filename);
                                        find_state = 1;
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(MemoActivity.this, "save 취소", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
                    } else {
                        save(filename);
                    }
                    break;
                case R.id.delete:
                    Log.i("TAG", "delete 진행");
                    if (filename == null) {
                        Toast.makeText(MemoActivity.this, "삭제파일이 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        delete(filename);
                        filename = null;
                    }
                    break;
            }
        }
    };

    public void load(String filename){
        FileInputStream fis = null;
        try{
            fis = openFileInput(filename);
            byte[] data = new byte[fis.available()];
            while( fis.read(data) != -1){
            }
            inputText.setText(new String(data));
            Toast.makeText(MemoActivity.this, "load 완료", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{ if(fis != null) fis.close(); }catch(Exception e){e.printStackTrace();}
        }
    }

    public void save(String filename){
        FileOutputStream fos = null;
        try{
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
            String out = inputText.getText().toString();
            fos.write(out.getBytes());
            Toast.makeText(MemoActivity.this, "save 완료", Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            Toast.makeText(MemoActivity.this, "저장할 파일명을 입력하세요", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }finally{
            try{ if(fos != null) fos.close(); }catch(Exception e){e.printStackTrace();}
        }
    }

    public void delete(String filename){
        boolean b = deleteFile(filename);
        if(b){
            Toast.makeText(MemoActivity.this, "delete 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MemoActivity.this, AlarmActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(MemoActivity.this, "delete 실패", Toast.LENGTH_SHORT).show();
        }
    }
}
