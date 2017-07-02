package com.example.administrator.myjkbd.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myjkbd.ExamApplication;
import com.example.administrator.myjkbd.R;
import com.example.administrator.myjkbd.bean.ExamIfor;
import com.example.administrator.myjkbd.bean.ExamQuestion;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4;
    ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initData();
    }

    private void initView() {
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
        tvExamTitle=(TextView)findViewById(R.id.tv_title);
        tvop1=(TextView)findViewById(R.id.tv_op1);
        tvop2=(TextView)findViewById(R.id.tv_op2);
        tvop3=(TextView)findViewById(R.id.tv_op3);
        tvop4=(TextView)findViewById(R.id.tv_op4);

    }

    private void initData() {
       ExamIfor examIfor= ExamApplication.getInstance().getmExamIfor();
        if(examIfor!=null){
            showData(examIfor);
        }
        List<ExamQuestion> examList=ExamApplication.getInstance().getExamList();
        if(examList!=null){
            showExam(examList);
        }
    }
private void showExam(List<ExamQuestion> examList) {
    ExamQuestion exam=examList.get(0);
    if(exam!=null)
    {
        tvExamTitle.setText(exam.getQuestion());
        tvop1.setText(exam.getItem1());
        tvop2.setText(exam.getItem2());
        tvop3.setText(exam.getItem3());
        tvop4.setText(exam.getItem4());
        Picasso.with(ExamActivity.this)
                .load(exam.getUrl())
                .into(mImageView);

    }
}


    private void showData(ExamIfor examIfo) {
        tvExamInfo.setText(examIfo.toString());
    }

}
