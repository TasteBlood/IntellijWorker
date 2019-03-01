package com.cloudcreativity.intellijworker.main;

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

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseActivity;
import com.cloudcreativity.intellijworker.databinding.ActivityMainBinding;
import com.cloudcreativity.intellijworker.receiver.MyBusinessReceiver;


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
            case R.id.nav_settings:
                startActivity(new Intent().setClass(this,SettingActivity.class));
                return true;
                default:
                    return false;
        }
    }
}
