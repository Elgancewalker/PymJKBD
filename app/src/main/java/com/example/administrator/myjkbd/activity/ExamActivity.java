package com.example.administrator.myjkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.myjkbd.ExamApplication;
import com.example.administrator.myjkbd.R;
import com.example.administrator.myjkbd.bean.ExamIfor;
import com.example.administrator.myjkbd.bean.ExamQuestion;
import com.example.administrator.myjkbd.biz.ExamBiz;
import com.example.administrator.myjkbd.biz.IExamBiz;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4,tvLoad,tvNum,tvC,tvD;
    CheckBox cb01,cb02,cb03,cb04;
    CheckBox[]cbs=new CheckBox[4];
    ImageView mImageView;
    IExamBiz biz;
    LinearLayout layoutLoading;
    ProgressBar dialog;
    boolean isLoadExamInfo=false;
    boolean isLoadQuestions=false;

    boolean isLoadExamInfoRecevier=false;
    boolean isLoadQuestionsRecevier=false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast=new LoadExamBroadcast();
        mLoadQuestionBroadcast=new LoadQuestionBroadcast();
        setListener();
        initView();
        biz=new ExamBiz();
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tvLoad.setText("下载数据...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        layoutLoading=(LinearLayout) findViewById(R.id.layout_loading);
        tvLoad= (TextView) findViewById(R.id.tv_load);
        dialog= (ProgressBar) findViewById(R.id.pr_load_dialog);
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
        tvExamTitle=(TextView)findViewById(R.id.tv_title);
        tvNum= (TextView) findViewById(R.id.tv_exam_num);
        tvop1=(TextView)findViewById(R.id.tv_op1);
        tvop2=(TextView)findViewById(R.id.tv_op2);
        tvop3=(TextView)findViewById(R.id.tv_op3);
        tvop4=(TextView)findViewById(R.id.tv_op4);
        tvC= (TextView) findViewById(R.id.tv_c);
        tvD= (TextView) findViewById(R.id.tv_d);
        cb01= (CheckBox) findViewById(R.id.cb_01);
        cb02= (CheckBox) findViewById(R.id.cb_02);
        cb03= (CheckBox) findViewById(R.id.cb_03);
        cb04= (CheckBox) findViewById(R.id.cb_04);
        cbs[0]=cb01;
        cbs[1]=cb02;
        cbs[2]=cb03;
        cbs[3]=cb04;
        mImageView=(ImageView)findViewById(R.id.im_exam_image);
        layoutLoading.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        cb01.setOnCheckedChangeListener(listener);
        cb02.setOnCheckedChangeListener(listener);
        cb03.setOnCheckedChangeListener(listener);
        cb04.setOnCheckedChangeListener(listener);

    }
    CompoundButton.OnCheckedChangeListener listener=new CompoundButton.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                int userAnswer = 0;
                switch (buttonView.getId()) {
                    case R.id.cb_01:
                        userAnswer = 1;
                        break;
                    case R.id.cb_02:
                        userAnswer = 2;
                        break;
                    case R.id.cb_03:
                        userAnswer = 3;
                        break;
                    case R.id.cb_04:
                        userAnswer = 4;
                        break;
                }
                if (userAnswer > 0) {
                    for (CheckBox cb : cbs) {
                        cb.setChecked(false);
                    }
                    cbs[userAnswer - 1].setChecked(true);
                }
            }

        }
    };

    private void initData() {
        if(isLoadExamInfoRecevier&&isLoadQuestionsRecevier) {
            if (isLoadExamInfo && isLoadQuestions) {
                layoutLoading.setVisibility(View.GONE);
                ExamIfor examIfor = ExamApplication.getInstance().getmExamIfor();
                if (examIfor != null) {
                    showData(examIfor);
                }

                showExam(biz.getExam());
            }else {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tvLoad.setText("下载失败，点击重试");
            }
        }
    }

    private void showExam(ExamQuestion exam) {
        if(exam!=null)
        {
            tvExamTitle.setText(exam.getQuestion());
            tvNum.setText(biz.getExamIndex());
            tvop1.setText(exam.getItem1());
            tvop2.setText(exam.getItem2());
            tvop3.setText(exam.getItem3());
            tvop4.setText(exam.getItem4());
            if(exam.getUrl()!=null&&!exam.getUrl().equals("")) {
                mImageView.setVisibility(View.VISIBLE);
                Picasso.with(ExamActivity.this)
                        .load(exam.getUrl())
                        .into(mImageView);
            }else {
                mImageView.setVisibility(View.GONE);
            }

            if(exam.getItem3().equals("")){
                tvC.setVisibility(View.GONE);
                cb03.setVisibility(View.GONE);
            }else {
                tvC.setVisibility(View.VISIBLE);
                cb03.setVisibility(View.VISIBLE);
            }
            if(exam.getItem4().equals("")){
                tvD.setVisibility(View.GONE);
                cb04.setVisibility(View.GONE);
            }else {
                tvD.setVisibility(View.VISIBLE);
                cb04.setVisibility(View.VISIBLE);
            }

        }
    }

    private void showData(ExamIfor examIfo) {
        tvExamInfo.setText(examIfo.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoadExamBroadcast!=null){
            unregisterReceiver(mLoadExamBroadcast);
        }
        if(mLoadQuestionBroadcast!=null){
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }

    public void preExam(View view) {
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        showExam(biz.nextQuestion());
    }

    class LoadExamBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isSuccess);
            if(isSuccess){
                isLoadExamInfo=true;
            }
            isLoadExamInfoRecevier=true;
            initData();
        }
    }
    class LoadQuestionBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess"+isSuccess);
            if(isSuccess){
                isLoadQuestions=true;
            }
            isLoadQuestionsRecevier=true;
            initData();
        }
    }

}
