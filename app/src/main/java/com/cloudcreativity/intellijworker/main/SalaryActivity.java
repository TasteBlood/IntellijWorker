package com.cloudcreativity.intellijworker.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseActivity;
import com.cloudcreativity.intellijworker.databinding.ActivitySalaryBinding;
import com.cloudcreativity.intellijworker.entity.ProjectEntity;
import com.cloudcreativity.intellijworker.entity.UserProjectEntity;

/**
 * 工资记录
 */
public class SalaryActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySalaryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_salary);
        binding.tlbSalary.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        UserProjectEntity entity  = (UserProjectEntity) getIntent().getSerializableExtra("proEntity");
        binding.setSalaryModel(new SalaryModel(this,this,entity,binding));
    }

}
