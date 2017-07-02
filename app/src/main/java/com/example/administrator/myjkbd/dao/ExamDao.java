package com.example.administrator.myjkbd.dao;

import android.util.Log;

import com.example.administrator.myjkbd.ExamApplication;
import com.example.administrator.myjkbd.bean.ExamIfor;
import com.example.administrator.myjkbd.bean.ExamQuestion;
import com.example.administrator.myjkbd.bean.Result;
import com.example.administrator.myjkbd.utils.OkHttpUtils;
import com.example.administrator.myjkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ExamDao implements IExamDao{
    @Override
    public void loadExamInfo() {
        OkHttpUtils<ExamIfor> utils=new OkHttpUtils<>(ExamApplication.getInstance());
        String url ="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(url)
                .targetClass(ExamIfor.class)
                .execute(new OkHttpUtils.OnCompleteListener<ExamIfor>(){
                    @Override
                    public void onSuccess(ExamIfor result) {
                        Log.e("main","result="+result);
                        ExamApplication.getInstance().setmExamIfor(result);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });
        OkHttpUtils<String>utils1=new OkHttpUtils<String>(ExamApplication.getInstance());
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
                                ExamApplication.getInstance().setExamList(list);
                            }
                        }
                    }
                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });

    }

    @Override
    public void loadQuestionLists() {

    }
}
