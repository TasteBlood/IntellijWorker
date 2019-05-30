package com.cloudcreativity.intellijworker.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseActivity;
import com.cloudcreativity.intellijworker.databinding.ActivityWorkNotesBinding;
import com.cloudcreativity.intellijworker.entity.ProjectEntity;
import com.cloudcreativity.intellijworker.entity.UserProjectEntity;

/**
 * 出勤记录
 */
public class WorkNotesActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserProjectEntity entity = (UserProjectEntity) getIntent().getSerializableExtra("proEntity");
        ActivityWorkNotesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_work_notes);
        binding.setWorkModel(new WorkNotesModel(this,this,entity,binding));
        binding.tlbWorkNotes.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
