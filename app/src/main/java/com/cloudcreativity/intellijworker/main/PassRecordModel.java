package com.cloudcreativity.intellijworker.main;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijworker.databinding.ActivityPassRecordBinding;
import com.cloudcreativity.intellijworker.databinding.ItemLayoutPassItemBinding;
import com.cloudcreativity.intellijworker.entity.PassEntity;
import com.cloudcreativity.intellijworker.entity.PassWrapper;
import com.cloudcreativity.intellijworker.utils.DefaultObserver;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PassRecordModel {
    private PassRecordActivity context;
    private ActivityPassRecordBinding binding;

    private int page = 1;
    private static final int PAGE_SIZE = 10;
    public BaseBindingRecyclerViewAdapter<PassEntity, ItemLayoutPassItemBinding> adapter;
    PassRecordModel(PassRecordActivity context,ActivityPassRecordBinding binding) {
        this.context = context;
        this.binding = binding;
        binding.refreshPassRecord.setOnRefreshListener(new RefreshListenerAdapter() {
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

        adapter = new BaseBindingRecyclerViewAdapter<PassEntity, ItemLayoutPassItemBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_pass_item;
            }

            @Override
            protected void onBindItem(ItemLayoutPassItemBinding binding, PassEntity item, int position) {
                binding.setItem(item);
            }
        };

        binding.rcvPassRecord.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        binding.refreshPassRecord.startRefresh();

    }
    private void loadData(){
        HttpUtils.getInstance().passList(page,PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(context,false) {
                    @Override
                    public void onSuccess(String t) {
                        PassWrapper passWrapper = new Gson().fromJson(t, PassWrapper.class);
                        if(passWrapper.getRecords()!=null&&passWrapper.getRecords().size()>0){
                            if(page==1){
                                adapter.getItems().clear();
                                binding.refreshPassRecord.finishRefreshing();
                            }else{
                                binding.refreshPassRecord.finishLoadmore();
                            }
                            page ++;
                            adapter.getItems().addAll(passWrapper.getRecords());
                        }else{
                            if(page==1){
                                binding.refreshPassRecord.finishRefreshing();

                            }else{
                                binding.refreshPassRecord.finishLoadmore();
                            }
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        if(page==1){
                            binding.refreshPassRecord.finishRefreshing();
                        }else{
                            binding.refreshPassRecord.finishLoadmore();
                        }
                    }
                });
    }
    public void onAddClick(){
        context.startActivity(new Intent(context,PassActivity.class));
    }
}
