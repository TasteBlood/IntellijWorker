package com.cloudcreativity.intellijworker.fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijworker.base.BaseDialogImpl;
import com.cloudcreativity.intellijworker.databinding.FragmentWorkBinding;
import com.cloudcreativity.intellijworker.databinding.LayoutItemMainProBinding;
import com.cloudcreativity.intellijworker.entity.BaseResult;
import com.cloudcreativity.intellijworker.entity.UserProjectEntity;
import com.cloudcreativity.intellijworker.main.SalaryActivity;
import com.cloudcreativity.intellijworker.main.WorkNotesActivity;
import com.cloudcreativity.intellijworker.utils.DefaultObserver;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.cloudcreativity.intellijworker.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FgWorkModel {
    private BaseDialogImpl baseDialog;
    private Activity context;
    private FragmentWorkBinding binding;

    public BaseBindingRecyclerViewAdapter<UserProjectEntity,LayoutItemMainProBinding> adapter;

    private int currentPage = 1;

    FgWorkModel(BaseDialogImpl baseDialog, Activity context, final FragmentWorkBinding binding) {
        this.baseDialog = baseDialog;
        this.context = context;
        this.binding = binding;


        binding.refreshWork.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                currentPage = 1;
                loadData(currentPage);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                loadData(currentPage);
            }
        });

        binding.rcvWork.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.refreshWork.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                currentPage = 1;
                loadData(currentPage);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                loadData(currentPage);
            }
        });

        adapter = new BaseBindingRecyclerViewAdapter<UserProjectEntity, LayoutItemMainProBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.layout_item_main_pro;
            }

            @Override
            protected void onBindItem(LayoutItemMainProBinding binding, final UserProjectEntity item, int position) {
                binding.setItem(item);
                binding.tvWork.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,WorkNotesActivity.class);
                        intent.putExtra("proEntity",item);
                        context.startActivity(intent);
                    }
                });
                binding.tvSalary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,SalaryActivity.class);
                        intent.putExtra("proEntity",item);
                        context.startActivity(intent);
                    }
                });
            }
        };

        binding.refreshWork.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.refreshWork.startRefresh();
            }
        },200);
    }

    private void loadData(final int page) {
        HttpUtils.getInstance().getProList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                            Type type = new TypeToken<List<UserProjectEntity>>() {
                            }.getType();
                            List<UserProjectEntity> entities = new Gson().fromJson(t, type);
                            if(page==1){
                                binding.refreshWork.finishRefreshing();
                                adapter.getItems().clear();
                            }else{
                                 binding.refreshWork.finishLoadmore();
                            }
                            adapter.getItems().addAll(entities);
                            currentPage ++;
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        if(page==1){
                            binding.refreshWork.finishRefreshing();
                        }else{
                            binding.refreshWork.finishLoadmore();
                        }
                    }
                });
    }
}
