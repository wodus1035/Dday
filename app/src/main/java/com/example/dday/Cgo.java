package com.example.dday;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;

public class Cgo extends Activity {
    Button etoday; //D-day 시간 설정
    EditText ename;//이벤트 내용적는 부분
    Button check;//설정완료(확인버튼)

    //현재시간변수
    int etYear;
    int etMonth;
    int etDay;
    //D-day 설정할 시간 변수
    int edYear=1;
    int edMonth=1;
    int edDay=1;

    long ed;
    long et;
    long er;

    int eresultNumber = 0;
    final int eDATE_DIALOG_ID=0;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgo);

        etoday= (Button)findViewById(R.id.etoday);
        ename = (EditText)findViewById(R.id.ename);
        check = (Button)findViewById(R.id.check);
        etoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        Calendar calendar = Calendar.getInstance();//현재날짜 불러옴
        etYear = calendar.get(Calendar.YEAR);
        etMonth = calendar.get(Calendar.MONDAY);
        etDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar dcalendar = Calendar.getInstance();
        dcalendar.set(edYear,edMonth,edDay);

        et=calendar.getTimeInMillis();//오늘 날짜를 밀리타임으로 변환
        ed=dcalendar.getTimeInMillis();//D-day설정 날짜를 밀리타임으로 변환
        er=(ed-et)/(24*60*60*1000);//디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈
        eresultNumber=(int)er+1;

        updateDisplay();

        //check버튼 눌렀을시 MainActivity로 이벤트내용, D-day시간(년,월,일),값을 Intent로 넘김
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cgo.this, MainActivity.class);
                intent.putExtra("contents",ename.getText().toString());
                intent.putExtra("ddayvalue",eresultNumber);
                intent.putExtra("yearvalue",edYear);
                intent.putExtra("monthvalue",edMonth);
                intent.putExtra("dayvalue",edDay);
                startActivity(intent);
            }
        });

    }
    //현재시간과 D-day시간 보여주는 함수
    private void updateDisplay() {
        etoday.setText(String.format("%d년 %d월 %d일",etYear,etMonth+1,etDay));
        etoday.setText(String.format("%d년 %d월 %d일",edYear,edMonth+1,edDay));
        }

    //D-day시간 설정하는 Listener와 함수
    private DatePickerDialog.OnDateSetListener dDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            edYear=year;
            edMonth=month;
            edDay=dayOfMonth;
            final  Calendar dCalendar = Calendar.getInstance();
            dCalendar.set(edYear,edMonth,edDay);

            ed=dCalendar.getTimeInMillis();
            er=(ed-et)/(24*60*60*1000);

            eresultNumber=(int)er;
            updateDisplay();
        }
    };
    @Override
    protected Dialog onCreateDialog(int id){
        if(id==eDATE_DIALOG_ID){
            return  new DatePickerDialog(this,dDateSetListener,etYear,etMonth,etDay);
        }
        return  null;

    }
}
