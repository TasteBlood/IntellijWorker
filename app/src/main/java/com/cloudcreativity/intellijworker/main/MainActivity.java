package com.cloudcreativity.intellijworker.main;

import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseActivity;
import com.cloudcreativity.intellijworker.databinding.ActivityMainBinding;
import com.cloudcreativity.intellijworker.fragments.HomeFragment;
import com.cloudcreativity.intellijworker.fragments.MineFragment;
import com.cloudcreativity.intellijworker.fragments.WorkFragment;
import com.cloudcreativity.intellijworker.receiver.MyBusinessReceiver;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

    private ActivityMainBinding binding;
    private MyBusinessReceiver receiver;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Fragment current;

    private HomeFragment homeFragment;
    private WorkFragment workFragment;
    private MineFragment mineFragment;

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

        binding.navMain.setFirstSelectedPosition(0);
        binding.navMain.setTabSelectedListener(this);


        init();
    }

    //initial display
    private void init(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        homeFragment = new HomeFragment();
        workFragment = new WorkFragment();
        mineFragment = new MineFragment();

        current = homeFragment;
        transaction.add(R.id.frameMain,homeFragment);
        transaction.commit();
    }

    private void switchContent(Fragment to){
        if (current != to) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(current).add(R.id.frameMain, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(current).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            current = to;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁广播
        unregisterReceiver(receiver);
    }

    @Override
    public void onTabSelected(int position) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        switch (position){
            case 0:
                switchContent(homeFragment);
                break;
            case 1:
                switchContent(workFragment);
                break;
            case 2:
                switchContent(mineFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
