package com.example.administrator.myjkbd;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/6/30.
 */

public class SplashActivity extends AppCompatActivity
    {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splah);
            mCountDownTimer.start();
        }
        CountDownTimer mCountDownTimer=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
}
