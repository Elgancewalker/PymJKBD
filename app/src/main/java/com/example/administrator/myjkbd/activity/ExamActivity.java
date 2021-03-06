package com.example.administrator.myjkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Gallery;
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
import com.example.administrator.myjkbd.view.QuestionAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.fillBefore;
import static android.R.attr.onClick;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4,tvLoad,tvNum,tvC,tvD,tvTime;
    CheckBox cb01,cb02,cb03,cb04;
    CheckBox[]cbs=new CheckBox[4];
    TextView[] tvOps=new TextView[4];
    ImageView mImageView;
    IExamBiz biz;
    LinearLayout layoutLoading;
    ProgressBar dialog;
    QuestionAdapter mAdapter;
    boolean isLoadExamInfo=false;
    boolean isLoadQuestions=false;

    boolean isLoadExamInfoRecevier=false;
    boolean isLoadQuestionsRecevier=false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;
    Gallery mGallery;

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
        tvOps[0]=tvop1;
        tvOps[1]=tvop2;
        tvOps[2]=tvop3;
        tvOps[3]=tvop4;
        tvTime= (TextView) findViewById(R.id.tv_time);
        mImageView=(ImageView)findViewById(R.id.im_exam_image);
        mGallery= (Gallery) findViewById(R.id.gallery);
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
                initGallery();
                showExam(biz.getExam());
                initTimer(examIfor);
            }else {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tvLoad.setText("下载失败，点击重试");
            }
        }
    }

    private void initGallery() {
        mAdapter=new QuestionAdapter(this);
        mGallery.setAdapter(mAdapter);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //            Log.e("gall","position="+position);
                saveUserAnswer();
                showExam(biz.getExam(position));
            }
        });
    }

    private void initTimer(ExamIfor examIfor) {
        int sumTimer=examIfor.getLimitTime()*60*1000;
        final long overTime= sumTimer+System.currentTimeMillis();
        final Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long l=overTime-System.currentTimeMillis();
                final int min= (int) (l/1000/60);
                final int sec= (int) (l/1000%60);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("剩余时间："+min+"分"+sec+"秒");
                    }
                });

            }
        },0,1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commit(null);
                    }
                });
            }
        },sumTimer);
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
            resetOptions();
            String userAnswer=exam.getUserAnswer();
            if(userAnswer!=null&&!userAnswer.equals("")){
                int userCB=Integer.parseInt(userAnswer)-1;
                cbs[userCB].setChecked(true);
                setOptions(false);
                setAnswerTextColor(userAnswer,exam.getAnswer());
            }else {
                setOptions(true);
                setOptionsColor();
            }
        }
    }

    private void setOptionsColor() {
        for (TextView tvOp : tvOps) {
            tvOp.setTextColor(getResources()
                    .getColor(R.color.black));
        }
    }

    private void setAnswerTextColor(String userAnswer, String answer) {
        int ra=Integer.parseInt(answer)-1;
        for (int i = 0; i < tvOps.length; i++) {
            if(i==ra){
                tvOps[i].setTextColor(getResources()
                        .getColor(R.color.green));
            }else {
                if(!userAnswer.equals(answer)){
                    int ua=Integer.parseInt(userAnswer)-1;
                    if(i==ua) {
                        tvOps[i].setTextColor(getResources()
                                .getColor(R.color.red));
                    }else {
                        tvOps[i].setTextColor(getResources()
                                .getColor(R.color.black));
                    }
                }
            }
        }
    }


    private void setOptions(boolean hasAnswer){
        for (CheckBox cb : cbs) {
            cb.setEnabled(hasAnswer);
        }
    }
    private void resetOptions() {
        for(CheckBox cb:cbs){
            cb.setChecked(false);
        }
    }

    private void saveUserAnswer(){
        for (int i = 0; i < cbs.length; i++) {
            if(cbs[i].isChecked()){
                biz.getExam().setUserAnswer(String.valueOf(i+1));
                setOptions(true);
                mAdapter.notifyDataSetChanged();
                return;
           }
        }
        biz.getExam().setUserAnswer("");
        mAdapter.notifyDataSetChanged();
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
        saveUserAnswer();
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        saveUserAnswer();
        showExam(biz.nextQuestion());
    }

    public void commit(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("交卷")
                .setMessage("你还有剩余时间，确认胶卷么？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        commit();
                    }
                })
                .setNegativeButton("取消", null);
        builder.create().show();
    }

    public void commit() {
        saveUserAnswer();
        int s=biz.commitExam();
        View inflate=View.inflate(this,R.layout.layout_result,null);
        TextView tvResult= (TextView) inflate.findViewById(R.id.tv_result);
        tvResult.setText("你的分数为\n"+s+"分！");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("交卷")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setCancelable(false);
        builder.create().show();
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
