package com.example.administrator.myjkbd.biz;

import com.example.administrator.myjkbd.bean.ExamQuestion;

/**
 * Created by Administrator on 2017/7/2.
 */

public interface IExamBiz {
    void beginExam();
    ExamQuestion getExam();
    ExamQuestion nextQuestion();
    ExamQuestion preQuestion();
    void commitExam();
    String getExamIndex();
}
