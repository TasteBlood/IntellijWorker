package com.cloudcreativity.intellijworker.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseActivity;
import com.cloudcreativity.intellijworker.databinding.ActivityPassBinding;
import com.donkingliang.imageselector.utils.ImageSelector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 举报页面
 */
public class PassActivity extends BaseActivity {

    private PassModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
            ActivityPassBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_pass);
            setSupportActionBar(binding.tlbPass);
            binding.tlbPass.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            binding.setPassModel(model = new PassModel(this,binding));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK)
            return;
        if(requestCode==100&&data!=null){
            ArrayList<String> listExtra = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            model.adapter.update(listExtra);

            //地址选择
        }else if(requestCode==PassModel.REQUEST_CODE&&data!=null){
            String address = data.getStringExtra("address");
            String province = data.getStringExtra("province");
            String city = data.getStringExtra("city");
            String area = data.getStringExtra("area");
            String street = data.getStringExtra("street");
            String name = data.getStringExtra("name");
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);

            HashMap<String,Object> result = new HashMap<>();
            result.put("address",address);
            result.put("province",province);
            result.put("city",city);
            result.put("area",area);
            result.put("street",street);
            result.put("latitude",latitude);
            result.put("longitude",longitude);
            result.put("name",name);

            model.onLocationResult(result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Map<String,Object> data){
        if(data!=null){
            model.uploadSuccess(data);
        }
    }
}
