package com.cloudcreativity.intellijworker.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseActivity;
import com.cloudcreativity.intellijworker.databinding.ActivityPassRecordBinding;

public class PassRecordActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPassRecordBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_pass_record);
        setSupportActionBar(binding.tlbPassRecord);
        binding.tlbPassRecord.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.setModel(new PassRecordModel(this,binding));
    }
}
