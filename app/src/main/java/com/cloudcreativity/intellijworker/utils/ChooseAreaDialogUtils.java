package com.cloudcreativity.intellijworker.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijworker.base.BaseDialogImpl;
import com.cloudcreativity.intellijworker.databinding.LayoutDialogChooseAreaBinding;
import com.cloudcreativity.intellijworker.databinding.LayoutItemProvinceBinding;
import com.cloudcreativity.intellijworker.entity.address.ProvinceEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChooseAreaDialogUtils {
    private Dialog dialog;
    private int currentProId;
    private int currentCityId;
    private int currentAreaId;
    private ProvinceEntity lastPro;
    private ProvinceEntity lastCity;
    private ProvinceEntity lastArea;
    public ObservableField<Boolean> areaMode = new ObservableField<>();
    public ObservableField<Boolean> loading = new ObservableField<>();

    private BaseDialogImpl baseDialog;

//    private List<ProvinceEntity> provinceEntities = new ArrayList<>();
//    private List<ProvinceEntity> cityEntities = new ArrayList<>();
//    private List<ProvinceEntity> areaEntities = new ArrayList<>();

    public BaseBindingRecyclerViewAdapter<ProvinceEntity,LayoutItemProvinceBinding> provinceAdapter;
    public BaseBindingRecyclerViewAdapter<ProvinceEntity,LayoutItemProvinceBinding> cityAdapter;
    public BaseBindingRecyclerViewAdapter<ProvinceEntity,LayoutItemProvinceBinding> areaAdapter;

    private OnOkListener okListener;
    public void show(Context context, final boolean isChooseArea, BaseDialogImpl baseDialog,OnOkListener onOkListener){
        this.baseDialog = baseDialog;
        this.okListener = onOkListener;
        areaMode.set(isChooseArea);
        dialog = new Dialog(context, R.style.myProgressDialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        LayoutDialogChooseAreaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.layout_dialog_choose_area,null,false);
        binding.setUtils(this);
        binding.rcvProvince.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.rcvCity.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.rcvArea.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
//        itemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.shape_1dip_list_divider));
//        binding.rcvProvince.addItemDecoration(itemDecoration);
//        binding.rcvCity.addItemDecoration(itemDecoration);
//        binding.rcvArea.addItemDecoration(itemDecoration);
        dialog.setContentView(binding.getRoot());
        Window window = dialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        window.getAttributes().width = metrics.widthPixels;
        window.getAttributes().height = metrics.heightPixels/2;
        dialog.show();
        this.loadProvince();
        provinceAdapter = new BaseBindingRecyclerViewAdapter<ProvinceEntity, LayoutItemProvinceBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.layout_item_province;
            }

            @Override
            protected void onBindItem(LayoutItemProvinceBinding binding, final ProvinceEntity item, final int position) {
                binding.setItem(item);
                binding.layoutItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(lastPro==item)
                            return;
                        if(lastPro!=null){
                            lastPro.setCheck(false);
                        }
                        item.setCheck(true);
                        provinceAdapter.notifyItemChanged(position);
                        provinceAdapter.notifyItemChanged(provinceAdapter.getItems().indexOf(lastPro));
                        lastPro = item;
                        lastCity = null;
                        lastArea = null;
                        loadCity(item.getId());
                        areaAdapter.getItems().clear();
                        cityAdapter.getItems().clear();
                    }
                });
            }
        };

        cityAdapter = new BaseBindingRecyclerViewAdapter<ProvinceEntity, LayoutItemProvinceBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.layout_item_province;
            }

            @Override
            protected void onBindItem(LayoutItemProvinceBinding binding, final ProvinceEntity item, final int position) {
                binding.setItem(item);
                binding.layoutItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(lastCity==item)
                            return;
                        if(lastCity!=null){
                            lastCity.setCheck(false);
                        }
                        item.setCheck(true);
                        cityAdapter.notifyItemChanged(position);
                        cityAdapter.notifyItemChanged(cityAdapter.getItems().indexOf(lastCity));
                        lastCity = item;
                        lastArea = null;
                        if(isChooseArea){
                            loadArea(item.getId());
                            areaAdapter.getItems().clear();
                        }
                    }
                });
            }
        };

        areaAdapter = new BaseBindingRecyclerViewAdapter<ProvinceEntity, LayoutItemProvinceBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.layout_item_province;
            }

            @Override
            protected void onBindItem(LayoutItemProvinceBinding binding, final ProvinceEntity item, final int position) {
                binding.setItem(item);
                binding.layoutItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(lastArea==item)
                            return;
                        if(lastArea!=null){
                            lastArea.setCheck(false);
                        }
                        item.setCheck(true);
                        areaAdapter.notifyItemChanged(position);
                        areaAdapter.notifyItemChanged(areaAdapter.getItems().indexOf(lastArea));
                        lastArea = item;
                    }
                });
            }
        };
    }

    private void loadProvince(){
        this.loading.set(true);
        HttpUtils.getInstance().getProvince("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                        loading.set(false);
                        Type type = new TypeToken<List<ProvinceEntity>>(){}.getType();
                        List<ProvinceEntity> entities = new Gson().fromJson(t,type);
                        provinceAdapter.getItems().addAll(entities);
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        loading.set(false);
                    }
                });
    }

    private void loadCity(int provinceId){
        this.loading.set(true);
        HttpUtils.getInstance().getCity(provinceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                        loading.set(false);
                        cityAdapter.getItems().clear();
                        Type type = new TypeToken<List<ProvinceEntity>>(){}.getType();
                        List<ProvinceEntity> entities = new Gson().fromJson(t,type);
                        cityAdapter.getItems().addAll(entities);
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        loading.set(false);
                    }
                });
    }

    private void loadArea(int cityId){
        this.loading.set(true);
        HttpUtils.getInstance().getArea(cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                        loading.set(false);
                        areaAdapter.getItems().clear();
                        Type type = new TypeToken<List<ProvinceEntity>>(){}.getType();
                        List<ProvinceEntity> entities = new Gson().fromJson(t,type);
                        areaAdapter.getItems().addAll(entities);
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        loading.set(false);
                    }
                });
    }

    public void onCancel(){
        if(this.okListener!=null){
            this.okListener.onCancel();
        }
        dialog.dismiss();
    }

    public void onOk(){
        dialog.dismiss();
        if(this.okListener!=null){
            this.okListener.onOkClick(lastPro,lastCity,lastArea);
        }
    }

    public interface OnOkListener{
        void onCancel();
        void onOkClick(ProvinceEntity province,ProvinceEntity city,ProvinceEntity area);
    }
}
