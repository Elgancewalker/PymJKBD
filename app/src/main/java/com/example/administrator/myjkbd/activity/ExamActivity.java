package com.example.administrator.myjkbd.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.myjkbd.ExamApplication;
import com.example.administrator.myjkbd.R;
import com.example.administrator.myjkbd.bean.ExamIfor;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initData();
    }

    private void initView() {
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
    }

    private void initData() {
        ExamIfor examIfor= ExamApplication.getInstance().getmExamIfor();
        if(examIfor!=null){
            showData(examIfor);
        }
    }
    private void showData(ExamIfor examIfo) {
        tvExamInfo.setText(examIfo.toString());
    }

}
