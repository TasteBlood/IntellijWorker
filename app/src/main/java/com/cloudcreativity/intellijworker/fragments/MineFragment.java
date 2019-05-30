package com.cloudcreativity.intellijworker.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.LazyFragment;
import com.cloudcreativity.intellijworker.databinding.FragmentMineBinding;

public class MineFragment extends LazyFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMineBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mine,container,false);
        binding.setModel(new FgMineModel(this,context,binding));
        return binding.getRoot();
    }

    @Override
    public void initialLoadData() {

    }
}
