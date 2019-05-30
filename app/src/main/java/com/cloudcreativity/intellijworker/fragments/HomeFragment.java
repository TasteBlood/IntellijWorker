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
import com.cloudcreativity.intellijworker.databinding.FragmentHomeBinding;

public class HomeFragment extends LazyFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        binding.setModel(new FgHomeModel(this,context,binding));
        return binding.getRoot();
    }

    @Override
    public void initialLoadData() {

    }
}
