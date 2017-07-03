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
    public static String LOAD_EXAM_INFO="load_exam_info";
    public static String LOAD_EXAM_QUESTION="load_exam_question";
    public static String LOAD_DATA_SUCCESS="load_data_success";
    ExamIfor mExamIfor;
    List<ExamQuestion> mExamList;
    private static ExamApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
    public static ExamApplication getInstance(){
        return instance;
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
