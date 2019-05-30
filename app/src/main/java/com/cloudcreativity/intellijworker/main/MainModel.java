package com.cloudcreativity.intellijworker.main;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijworker.databinding.ActivityMainBinding;
import com.cloudcreativity.intellijworker.databinding.LayoutItemMainProBinding;
import com.cloudcreativity.intellijworker.entity.BaseResult;
import com.cloudcreativity.intellijworker.entity.UserProjectEntity;
import com.cloudcreativity.intellijworker.utils.DefaultObserver;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.cloudcreativity.intellijworker.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainModel {
    private ActivityMainBinding binding;
    private MainActivity context;
    public BaseBindingRecyclerViewAdapter<UserProjectEntity,LayoutItemMainProBinding> adapter;
    private int currentPage = 1;


    MainModel(final ActivityMainBinding binding, MainActivity context) {
        this.binding = binding;
        this.context = context;

        binding.navMain.setMode(BottomNavigationBar.MODE_FIXED);
        binding.navMain.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //binding.navMain.setBarBackgroundColor(R.color.gray_f1f1f1);

        binding.navMain.addItem(new BottomNavigationItem(R.mipmap.home,"首页")).setActiveColor(R.color.colorPrimary).setInActiveColor(R.color.gray_717171);
        binding.navMain.addItem(new BottomNavigationItem(R.mipmap.history,"经历")).setActiveColor(R.color.colorPrimary).setInActiveColor(R.color.gray_717171);
        binding.navMain.addItem(new BottomNavigationItem(R.mipmap.mine,"我的")).setActiveColor(R.color.colorPrimary).setInActiveColor(R.color.gray_717171);

        binding.navMain.initialise();

//        binding.rcvMain.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
//        binding.refreshMain.setOnRefreshListener(new RefreshListenerAdapter() {
//            @Override
//            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                currentPage = 1;
//                loadData(currentPage);
//            }
//
//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                loadData(currentPage);
//            }
//        });
//
//        adapter = new BaseBindingRecyclerViewAdapter<UserProjectEntity, LayoutItemMainProBinding>(context) {
//            @Override
//            protected int getLayoutResId(int viewType) {
//                return R.layout.layout_item_main_pro;
//            }
//
//            @Override
//            protected void onBindItem(LayoutItemMainProBinding binding, final UserProjectEntity item, int position) {
//                binding.setItem(item);
//                binding.tvWork.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(context,WorkNotesActivity.class);
//                        intent.putExtra("proEntity",item.getProjectDomain());
//                        context.startActivity(intent);
//                    }
//                });
//                binding.tvSalary.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(context,SalaryActivity.class);
//                        intent.putExtra("proEntity",item);
//                        context.startActivity(intent);
//                    }
//                });
//            }
//        };
//
//        binding.refreshMain.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                binding.refreshMain.startRefresh();
//            }
//        },200);
//
//        View headerView = binding.nvMain.getHeaderView(0);
//        TextView tvName = headerView.findViewById(R.id.tv_name);
//        String string = SPUtils.get().getString(SPUtils.Config.USER, "{}");
//        UserEntity userEntity = new Gson().fromJson(string, UserEntity.class);
//        tvName.setText(userEntity.getWorkerName());
    }

    private void loadData(final int page){
        HttpUtils.getInstance().getProList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(context,false) {
                    @Override
                    public void onSuccess(String t) {
                        BaseResult result = new Gson().fromJson(t, BaseResult.class);
                        if(result.getTotalData()>0){
                            Type type = new TypeToken<List<UserProjectEntity>>() {
                            }.getType();
                            List<UserProjectEntity> entities = new Gson().fromJson(new Gson().toJson(result.getData()), type);
                            if(page==1){
                                //binding.refreshMain.finishRefreshing();
                                adapter.getItems().clear();
                            }else{
                               // binding.refreshMain.finishLoadmore();
                            }
                            adapter.getItems().addAll(entities);
                            currentPage ++;
                        }else{
                            if(page<=1){
                                ToastUtils.showShortToast(context,"暂无工程数据");
                            }
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        if(page==1){
                            //binding.refreshMain.finishRefreshing();
                        }else{
                            //binding.refreshMain.finishLoadmore();
                        }
                    }
                });
    }
}
