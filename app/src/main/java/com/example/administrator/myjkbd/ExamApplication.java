package com.example.administrator.myjkbd;

import android.app.Application;
import android.util.Log;

import com.example.administrator.myjkbd.bean.ExamIfor;
import com.example.administrator.myjkbd.bean.ExamQuestion;
import com.example.administrator.myjkbd.utils.OkHttpUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamIfor mExamIfor;
    List<ExamQuestion> mmExamIfor;
    ExamApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initData();
    }

    private void initData() {
        OkHttpUtils<ExamIfor> utils=new OkHttpUtils<>(getApplicationContext());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri)
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
    }
}
