package com.cloudcreativity.peoplepass.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.cloudcreativity.peoplepass.R;
import com.cloudcreativity.peoplepass.base.BaseActivity;
import com.cloudcreativity.peoplepass.databinding.ActivityMainBinding;
import com.cloudcreativity.peoplepass.receiver.MyBusinessReceiver;
import com.cloudcreativity.peoplepass.utils.ToastUtils;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private MyBusinessReceiver receiver;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册广播
        receiver = new MyBusinessReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyBusinessReceiver.ACTION_LOGOUT);
        filter.addAction(MyBusinessReceiver.ACTION_EXIT_APP);
        filter.addAction(MyBusinessReceiver.ACTION_RE_LOGIN);
        registerReceiver(receiver,filter);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setMainModal(new MainModel(binding,this));
        setSupportActionBar(binding.tlbMain);
        binding.dwlMain.setStatusBarBackgroundColor(Color.argb(255,0,0,0));
        binding.tlbMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.dwlMain.openDrawer(Gravity.START,true);
            }
        });

        binding.nvMain.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁广播
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        binding.dwlMain.closeDrawer(Gravity.START,true);
        switch (item.getItemId()){
            case R.id.nav_collect:
                ToastUtils.showShortToast(this,item.getTitle());
                return true;
            case R.id.nav_settings:
                startActivity(new Intent().setClass(this,SettingActivity.class));
                return true;
            case R.id.nav_report:
                ToastUtils.showShortToast(this,item.getTitle());
                return true;
            case R.id.nav_prize:
                ToastUtils.showShortToast(this,item.getTitle());
                return true;
                default:
                    return false;
        }
    }
}
