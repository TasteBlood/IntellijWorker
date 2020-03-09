package com.cloudcreativity.intellijworker.main;

import android.app.Activity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijworker.base.BaseDialogImpl;
import com.cloudcreativity.intellijworker.databinding.ActivitySalaryBinding;
import com.cloudcreativity.intellijworker.databinding.LayoutItemSalaryBinding;
import com.cloudcreativity.intellijworker.entity.SalaryEntity;
import com.cloudcreativity.intellijworker.entity.UserProjectEntity;
import com.cloudcreativity.intellijworker.utils.DefaultObserver;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SalaryModel {

    private BaseDialogImpl baseDialog;
    private Activity context;
    private int pageNum = 1;
    private UserProjectEntity projectEntity;
    private ActivitySalaryBinding binding;
    public BaseBindingRecyclerViewAdapter<SalaryEntity, LayoutItemSalaryBinding> adapter;


    SalaryModel(BaseDialogImpl baseDialog, Activity context, UserProjectEntity projectEntity, final ActivitySalaryBinding binding) {
        this.baseDialog = baseDialog;
        this.context = context;
        this.projectEntity = projectEntity;
        this.binding = binding;

        this.binding.rcvSalary.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.divider_line_5dp_transparent));
        this.binding.rcvSalary.addItemDecoration(itemDecoration);

        binding.refreshSalary.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                loadData(pageNum);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                loadData(pageNum);
            }
        });

        adapter = new BaseBindingRecyclerViewAdapter<SalaryEntity, LayoutItemSalaryBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.layout_item_salary;
            }

            @Override
            protected void onBindItem(LayoutItemSalaryBinding binding, SalaryEntity item, int position) {
                binding.setItem(item);
            }
        };

        binding.refreshSalary.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.refreshSalary.startRefresh();
            }
        }, 200);

    }

    private void loadData(final int page) {
        HttpUtils.getInstance()
                .getSalary(projectEntity.getwId(), page, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog, false) {
                    @Override
                    public void onSuccess(String t) {
//                        if("[]".equals(t)){
//                            baseDialog.showRequestErrorMessage("暂无数据");
//                            if(page==1){
//                                binding.refreshSalary.finishRefreshing();
//                            }else{
//                                binding.refreshSalary.finishLoadmore();
//                            }
//                            return;
//                        }
                        Type type = new TypeToken<List<SalaryEntity>>() {
                        }.getType();
                        List<SalaryEntity> entities = new Gson().fromJson(t, type);
                        if (entities != null && !entities.isEmpty()) {
                            if (page == 1) {
                                binding.refreshSalary.finishRefreshing();
                                adapter.getItems().clear();
                            } else {
                                binding.refreshSalary.finishLoadmore();
                            }
                            adapter.getItems().addAll(entities);
                            pageNum++;
                        } else {
                            if (page == 1) {
                                binding.refreshSalary.finishRefreshing();
                                adapter.getItems().clear();
                            } else {
                                binding.refreshSalary.finishLoadmore();
                            }
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        if (page == 1) {
                            binding.refreshSalary.finishRefreshing();
                        } else {
                            binding.refreshSalary.finishLoadmore();
                        }
                    }
                });
    }
}
