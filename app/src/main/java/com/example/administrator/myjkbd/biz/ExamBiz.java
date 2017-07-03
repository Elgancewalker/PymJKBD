package com.example.administrator.myjkbd.biz;

import com.example.administrator.myjkbd.ExamApplication;
import com.example.administrator.myjkbd.bean.ExamQuestion;
import com.example.administrator.myjkbd.dao.ExamDao;
import com.example.administrator.myjkbd.dao.IExamDao;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;
    int examIndex=0;
    List<ExamQuestion> examQuestionList=null;

    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
        examIndex=0;
        dao.loadExamInfo();
        dao.loadQuestionLists();

    }

    @Override
    public ExamQuestion getExam() {
        examQuestionList=ExamApplication.getInstance().getExamList();
        if(examQuestionList!=null){
            return examQuestionList.get(examIndex);
        }else {
            return null;
        }
    }

    @Override
    public ExamQuestion nextQuestion() {
        if(examQuestionList!=null&&examIndex<examQuestionList.size()-1){
            examIndex++;
            return examQuestionList.get(examIndex);
        }else {
            return null;
        }
    }

    @Override
    public ExamQuestion preQuestion() {
        if(examQuestionList!=null&&examIndex>0){
            examIndex--;
            return examQuestionList.get(examIndex);
        }else {
            return null;
        }
    }

    @Override
    public void commitExam() {

    }

    @Override
    public String getExamIndex() {
        return (examIndex+1)+".";
    }
}
