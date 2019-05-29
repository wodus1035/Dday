package com.example.dday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends Activity {
    TextView ddayText;
    TextView todayText;
    TextView resultText;
    TextView eventContents;
    ImageButton knubtn;
    //현재시각(년,월,일)
    int tYear;
    int tMonth;
    int tDay;
    //D-day 설정(년,월,일)
    int dYear=1;
    int dMonth=1;
    int dDay=1;
    //D-day 값
    long r;

    int resultNumber = 0;
    final int DATE_DIALOG_ID=0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ddayText=(TextView)findViewById(R.id.dday);
        todayText=(TextView)findViewById(R.id.today);
        resultText=(TextView)findViewById(R.id.result);
        knubtn=(ImageButton)findViewById(R.id.knubtn);
        eventContents = (TextView)findViewById(R.id.econtents);

        //이벤트 설정내용 받는 부분
        Intent intent = getIntent();
        String contents = intent.getStringExtra("contents");
        eventContents.setText(contents);

        //환경설정 버튼 이벤트 부분
        knubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,Cgo.class);
                startActivity(intent1);
            }
        });
        //현재시간 불러오는 부분
        Calendar calendar = Calendar.getInstance();
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONDAY);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar dcalendar = Calendar.getInstance();
        dcalendar.set(dYear,dMonth,dDay);

        //cgo에서 dday 설정값 받음.
        Intent intent1 = new Intent(this.getIntent());
        dYear=intent1.getIntExtra("yearvalue",1);
        dMonth=intent1.getIntExtra("monthvalue",1);
        dDay=intent1.getIntExtra("dayvalue",1);
        r=intent1.getIntExtra("ddayvalue",tDay);
        resultNumber=(int)r+1;
        updateDisplay();
    }

    private void updateDisplay() {
        todayText.setText(String.format("%d년 %d월 %d일",tYear,tMonth+1,tDay));
        ddayText.setText(String.format("목표 날짜:%d년 %d월 %d일",dYear,dMonth+1,dDay));
        if(resultNumber>=0){
            resultText.setText(String.format("D-%d",resultNumber));
        }
        else{
            int absR=Math.abs(resultNumber);
            resultText.setText(String.format("D+%d",absR));
        }
    }

}
