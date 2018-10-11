package com.phong.baitap6;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtDate;
    private TextView txtTime;
    private EditText editCv;
    private EditText editNd;
    private Button btnDate;
    private Button btnTime;
    private Button btnAdd;
    private ListView lvCv;
    private ArrayList<JobWek> arrJob = new ArrayList<JobWek>();
    private ArrayAdapter<JobWek> adapter = null;
    private Calendar cal;
    private Date dateFinish;
    private Date hourFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getFormWidgets();
        getDefaultInfor();
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }
    private void initView() {
        txtDate = findViewById(R.id.txtdate);
        txtTime = findViewById(R.id.txttime);
        editCv = findViewById(R.id.editcongviec);
        editNd = findViewById(R.id.editnoidung);
        btnDate = findViewById(R.id.btndate);
        btnTime = findViewById(R.id.btntime);
        btnAdd = findViewById(R.id.btncongviec);
        lvCv = findViewById(R.id.lvcongviec);
    }

    private void getDefaultInfor() {
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        txtDate.setText(strDate);
        dft=new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String strTime=dft.format(cal.getTime());
        txtTime.setText(strTime);
        dft=new SimpleDateFormat("HH:mm",Locale.getDefault());
        txtTime.setTag(dft.format(cal.getTime()));
        editCv.requestFocus();
        dateFinish=cal.getTime();
        hourFinish=cal.getTime();

    }

    private void getFormWidgets() {
        adapter = new ArrayAdapter<JobWek>(this, android.R.layout.simple_list_item_1, arrJob);
        lvCv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btndate:
                DatePickerDialog.OnDateSetListener call = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtDate.setText((dayOfMonth) +"/" + (month+1) + "/"+ year);
                        cal.set(year, month, dayOfMonth);
                        dateFinish=cal.getTime();
                    }
                };
                String s=txtDate.getText()+"";
                String strArrtmp[]=s.split("/");
                int ngay=Integer.parseInt(strArrtmp[0]);
                int thang=Integer.parseInt(strArrtmp[1])-1;
                int nam=Integer.parseInt(strArrtmp[2]);
                DatePickerDialog pic=new DatePickerDialog(
                        MainActivity.this,
                        call, nam, thang, ngay);
                pic.setTitle("Chọn ngày hoàn thành");
                pic.show();
                break;
            case R.id.btntime:
                TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        String s=hourOfDay +":"+minute;
                        int hourTam=hourOfDay;
                        if(hourTam>12)
                            hourTam=hourTam-12;
                        txtTime.setText
                                (hourTam +":"+minute +(hourOfDay>12?" PM":" AM"));
                        txtTime.setTag(s);
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        hourFinish=cal.getTime();
                    }
                };
                String s1=txtTime.getTag()+"";
                String strArr[]=s1.split(":");
                int gio=Integer.parseInt(strArr[0]);
                int phut=Integer.parseInt(strArr[1]);
                TimePickerDialog time=new TimePickerDialog(
                        MainActivity.this,
                        callback, gio, phut, true);
                time.setTitle("Chọn giờ hoàn thành");
                time.show();
                break;
            case R.id.btncongviec:
                String title=editCv.getText()+"";
                String description=editNd.getText()+"";
                JobWek job=new JobWek(title, description, dateFinish, hourFinish);
                arrJob.add(job);
                adapter.notifyDataSetChanged();
                editCv.setText("");
                editNd.setText("");
                editCv.requestFocus();
                break;
        }

    }
}
