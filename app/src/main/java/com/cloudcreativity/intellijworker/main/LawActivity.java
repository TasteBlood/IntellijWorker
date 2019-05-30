package com.cloudcreativity.intellijworker.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseActivity;
import com.cloudcreativity.intellijworker.databinding.ActivityLawBinding;

public class LawActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLawBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_law);
        setSupportActionBar(binding.tlbLaw);
        binding.tlbLaw.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.setModel(new LawModel(this,binding));
    }
}
