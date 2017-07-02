package com.example.administrator.myjkbd;

import android.app.Application;
import android.util.Log;

import com.example.administrator.myjkbd.bean.ExamIfor;
import com.example.administrator.myjkbd.bean.ExamQuestion;
import com.example.administrator.myjkbd.bean.Result;
import com.example.administrator.myjkbd.utils.OkHttpUtils;
import com.example.administrator.myjkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamIfor mExamIfor;
    List<ExamQuestion> mExamList;
    private static ExamApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initData();
    }
    public static ExamApplication getInstance(){
        return instance;
    }
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils<ExamIfor> utils=new OkHttpUtils<>(getApplicationContext());
                String url ="http://101.251.196.90:8080/JztkServer/examInfo";
                utils.url(url)
                        .targetClass(ExamIfor.class)
                        .execute(new OkHttpUtils.OnCompleteListener<ExamIfor>(){
                            @Override
                            public void onSuccess(ExamIfor result) {
                                Log.e("main","result="+result);
                                mExamIfor=result;
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });

                OkHttpUtils<String>utils1=new OkHttpUtils<String>(instance);
                String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url2)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>(){

                            @Override
                            public void onSuccess(String jsonStr) {
                                Result result= ResultUtils.getListResultFromJson(jsonStr);
                                if(result!=null&&result.getError_code()==0){
                                    List<ExamQuestion> list=result.getResult();
                                    if(list!=null&&list.size()>0){
                                        mExamList=list;
                                    }
                                }
                            }
                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });

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
