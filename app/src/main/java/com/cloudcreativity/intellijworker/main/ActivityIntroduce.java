package com.cloudcreativity.intellijworker.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseActivity;
import com.cloudcreativity.intellijworker.loginAndRegister.LoginActivity;
import com.cloudcreativity.intellijworker.utils.SPUtils;

/**
 * 引导页
 */
public class ActivityIntroduce extends BaseActivity {
    //控件定义
    private ViewPager viewPager;

    private View[] views = new View[3];
    private View[] dots = new View[3];
    private int[] resources = {R.mipmap.ic_index1,R.mipmap.ic_index2,R.layout.layout_index3};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        initView();
    }

    private void changeDots(int position){
        viewPager.setCurrentItem(position);
        for(int i=0;i<3;i++){
            dots[i].setSelected(false);
            if(i==position)
                dots[i].setSelected(true);
        }
    }

    /**
     * 初始化view
     */
    private void initView() {

        LinearLayout layout = findViewById(R.id.dotsLayout);
        for(int i=0;i<3;i++){
            //创建View，默认创建ImageView
            View dot = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            if(i>0)
                params.setMarginStart(20);
            dot.setLayoutParams(params);
            dot.setBackground(getResources().getDrawable(R.drawable.selector_dots));
            dots[i] = dot;
            layout.addView(dot);
            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams vpLayoutParams = new ViewPager.LayoutParams();
            vpLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            vpLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(vpLayoutParams);
            imageView.setImageResource(resources[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            views[i] = imageView;
            if(i==2){
                //添加最后一个携带按钮的View
                View v = View.inflate(this,resources[i],null);
                views[i] = v;
                v.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //设置已经读取过
                        SPUtils.get().setIsFirst(false);
                        //启动页之后一般都是直接跳转
                        if(SPUtils.get().isLogin()){
                            //
                            startActivity(new Intent(ActivityIntroduce.this,MainActivity.class));
                            onBackPressed();
                        }else{
                            startActivity(new Intent(ActivityIntroduce.this, LoginActivity.class));
                            onBackPressed();
                        }
                    }
                });
            }
        }

        viewPager = findViewById(R.id.vpIntroduce);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(views[position]);
                return views[position];
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //change dots
                changeDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);
        dots[0].setSelected(true);
    }
}
