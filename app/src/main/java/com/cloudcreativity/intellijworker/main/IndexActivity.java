package com.cloudcreativity.intellijworker.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.loginAndRegister.LoginActivity;
import com.cloudcreativity.intellijworker.utils.SPUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_index);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SPUtils.get().isFirst()){
                    startActivity(new Intent().setClass(IndexActivity.this, ActivityIntroduce.class));
                }else{
                    if (SPUtils.get().isLogin()) {
                        //跳转到主页
                        startActivity(new Intent().setClass(IndexActivity.this, MainActivity.class));
                    } else {
                        //跳转到登录
                        startActivity(new Intent().setClass(IndexActivity.this, LoginActivity.class));
                    }
                }
                onBackPressed();
            }
        },3000);
    }
}
