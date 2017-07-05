package com.example.administrator.myjkbd.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myjkbd.ExamApplication;
import com.example.administrator.myjkbd.R;
import com.example.administrator.myjkbd.bean.ExamQuestion;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class QuestionAdapter  extends BaseAdapter{
    Context mContext;
    List<ExamQuestion> examQuestionList;

    public QuestionAdapter(Context context){
        mContext=context;
        examQuestionList= ExamApplication.getInstance().getExamList();
    }
    @Override
    public int getCount() {
        return examQuestionList==null?0:examQuestionList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View  view=View.inflate(mContext, R.layout.item_question,null);
        TextView tvNum= (TextView) view.findViewById(R.id.tv_num);
        ImageView ivQuestion= (ImageView) view.findViewById(R.id.iv_question);
        String ua=examQuestionList.get(position).getUserAnswer();
        if(ua!=null && !ua.equals("")) {
            ivQuestion.setImageResource(R.mipmap.answer24x24);
        }else {
            ivQuestion.setImageResource(R.mipmap.ques24x24);
        }
        tvNum.setText("第"+(position+1)+"题");
        return view;
    }
}
