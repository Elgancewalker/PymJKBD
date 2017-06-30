package com.example.administrator.myjkbd.bean;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamQuestion {
    /**
     * id : 8
     * question : 这个标志是何含义？
     * answer : 2
     * item1 : 车道方向预告
     * item2 : 交叉路口预告
     * item3 : 分道信息预告
     * item4 : 分岔处预告
     * explains : 此为交叉路口预告，记住吧。
     * url : http://images.juheapi.com/jztk/c1c2subject1/8.jpg
     */

    private int id;
    private String question;
    private String answer;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String explains;
    private String url;

    private String userAnswer;

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getItem1() {
        return item1;
    }

    public String getItem2() {
        return item2;
    }

    public String getItem3() {
        return item3;
    }

    public String getItem4() {
        return item4;
    }

    public String getExplains() {
        return explains;
    }

    public String getUrl() {
        return url;
    }
}
