package com.cloudcreativity.intellijworker.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

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

        View index = findViewById(R.id.iv_index);
        Animation animation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2500);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (SPUtils.get().isLogin()) {
                    //跳转到主页
                    startActivity(new Intent().setClass(IndexActivity.this, MainActivity.class));
                } else {
                    //跳转到登录
                    startActivity(new Intent().setClass(IndexActivity.this, LoginActivity.class));
                }

                onBackPressed();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        index.startAnimation(animation);
    }
}
