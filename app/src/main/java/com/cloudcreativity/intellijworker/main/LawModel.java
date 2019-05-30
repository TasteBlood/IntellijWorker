package com.cloudcreativity.intellijworker.main;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijworker.base.CommonWebActivity;
import com.cloudcreativity.intellijworker.databinding.ActivityLawBinding;
import com.cloudcreativity.intellijworker.databinding.ItemLayoutLawBinding;
import com.cloudcreativity.intellijworker.entity.LawEntity;
import com.cloudcreativity.intellijworker.entity.LawWrapper;
import com.cloudcreativity.intellijworker.utils.DefaultObserver;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.cloudcreativity.intellijworker.utils.ToastUtils;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LawModel {
    private LawActivity context;
    private ActivityLawBinding binding;

    private int page = 1;
    private int size = 10;

    public BaseBindingRecyclerViewAdapter<LawEntity, ItemLayoutLawBinding> adapter;

    LawModel(LawActivity context, ActivityLawBinding binding) {
        this.context = context;
        this.binding = binding;

        binding.refreshLaw.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                page = 1;
                loadData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                loadData();
            }
        });

        adapter = new BaseBindingRecyclerViewAdapter<LawEntity, ItemLayoutLawBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_law;
            }

            @Override
            protected void onBindItem(ItemLayoutLawBinding binding, final LawEntity item, int position) {
                binding.setItem(item);
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到详情
                        CommonWebActivity.startActivityByData(context,item.getLawTitle(),item.getLawContent());
                    }
                });
            }
        };
        binding.rcvLaw.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.refreshLaw.startRefresh();
    }

    private void loadData(){
        HttpUtils.getInstance().getLawByPage(page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(context,false) {
                    @Override
                    public void onSuccess(String t) {
                        LawWrapper lawWrapper = new Gson().fromJson(t, LawWrapper.class);
                        if(lawWrapper.getRecords()!=null&&lawWrapper.getRecords().size()>0){
                            if(page==1){
                                binding.refreshLaw.finishRefreshing();
                                adapter.getItems().clear();
                            }else{
                                binding.refreshLaw.finishLoadmore();
                            }
                            adapter.getItems().addAll(lawWrapper.getRecords());
                            page++;
                        }else{
                            if(page==1){
                                binding.refreshLaw.finishRefreshing();
                                adapter.getItems().clear();
                            }else{
                                binding.refreshLaw.finishLoadmore();
                                ToastUtils.showShortToast(context,"暂无更多数据");
                            }
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        if(page==1){
                            binding.refreshLaw.finishRefreshing();
                        }else{
                            binding.refreshLaw.finishLoadmore();
                        }
                    }
                });
    }
}
