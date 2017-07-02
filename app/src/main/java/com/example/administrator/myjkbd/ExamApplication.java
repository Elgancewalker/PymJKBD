package com.example.administrator.myjkbd;

import android.app.Application;
import android.util.Log;

import com.example.administrator.myjkbd.bean.ExamIfor;
import com.example.administrator.myjkbd.bean.ExamQuestion;
import com.example.administrator.myjkbd.bean.Result;
import com.example.administrator.myjkbd.biz.ExamBiz;
import com.example.administrator.myjkbd.biz.IExamBiz;
import com.example.administrator.myjkbd.utils.OkHttpUtils;
import com.example.administrator.myjkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamIfor mExamIfor;
    List<ExamQuestion> mExamList;
    IExamBiz biz;
    private static ExamApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        biz=new ExamBiz();
        initData();
    }
    public static ExamApplication getInstance(){
        return instance;
    }
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }
    public ExamIfor getmExamIfor(){
        return mExamIfor;
    }
    public void setmExamIfor(ExamIfor examInfo){
        mExamIfor=examInfo;
    }

    public List<ExamQuestion> getExamList() {
        return mExamList;
    }
    public void setExamList(List<ExamQuestion> examList){
        mExamList=examList;
    }
}
