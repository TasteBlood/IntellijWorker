package com.cloudcreativity.intellijworker.fragments;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijworker.base.BaseDialogImpl;
import com.cloudcreativity.intellijworker.base.CommonWebActivity;
import com.cloudcreativity.intellijworker.databinding.FragmentHomeBinding;
import com.cloudcreativity.intellijworker.databinding.LayoutItemPositionBinding;
import com.cloudcreativity.intellijworker.entity.PositionEntity;
import com.cloudcreativity.intellijworker.entity.PositionWrapper;
import com.cloudcreativity.intellijworker.entity.address.ProvinceEntity;
import com.cloudcreativity.intellijworker.utils.BannerImageLoader;
import com.cloudcreativity.intellijworker.utils.CallDialogUtils;
import com.cloudcreativity.intellijworker.utils.ChooseAreaDialogUtils;
import com.cloudcreativity.intellijworker.utils.DefaultObserver;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.cloudcreativity.intellijworker.utils.ToastUtils;
import com.google.gson.Gson;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FgHomeModel {
    private BaseDialogImpl baseDialog;
    private Activity context;
    private FragmentHomeBinding binding;
    private ListPopupWindow popupWindow;
    private int width;
    public BaseBindingRecyclerViewAdapter<PositionEntity,LayoutItemPositionBinding> adapter;

    private int page=1;
    private int size=20;

    private int salaryId = 0;
    private int experienceId = 0;
    private int cityId=0;


    private List<Map<String,Object>> salarys = new ArrayList<>();
    private List<Map<String,Object>> expriences = new ArrayList<>();

    private String[] salaryTitle;
    private String[] experienceTitle;
    private int lastScrollItem = 0;
    private LinearLayoutManager layoutManager;
    private boolean load=false;
    FgHomeModel(BaseDialogImpl baseDialog, Activity context, FragmentHomeBinding binding) {
        this.baseDialog = baseDialog;
        this.context = context;
        this.binding = binding;
        width = context.getResources().getDisplayMetrics().widthPixels/3;
        initBanner();
        initData();
    }

    private void initBanner(){
        final List<String> bannerImages = new ArrayList<>();
        final List<String> links = new ArrayList<>();
//        bannerImages.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554187974&di=6e3c9a082cf2e6d4ccd34268fe84f790&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.dzwww.com%2F2014%2F2014qglh%2Fzljzh%2Ftp%2F201403%2FW020140313406344710259.jpg");
//        bannerImages.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1761735802,3117408946&fm=26&gp=0.jpg");
//        bannerImages.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553593404752&di=5b8775c3b5c9faf4be95561b3df642e1&imgtype=0&src=http%3A%2F%2Fwww.yxthly.com%2Fpic%2F2017043008350580954.jpg");
        final List<String> bannerTitles = new ArrayList<>();
//        bannerTitles.add("李克强总理就保障农民工权益发表意见");
//        bannerTitles.add("甘肃就农民工工资问题开展工作会议");
//        bannerTitles.add("甘肃省出台农民工快捷举报通道");
        binding.bannerHome.setDelayTime(5000);
        binding.bannerHome.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setBannerAnimation(Transformer.FlipHorizontal)
                .setIndicatorGravity(BannerConfig.RIGHT);
        HttpUtils.getInstance().getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                        try {
                            JSONObject object = new JSONObject(t);
                            JSONArray records = object.getJSONArray("records");
                            if(records!=null&&records.length()>0){
                                for (int i=0;i<records.length();i++) {
                                    bannerImages.add(records.getJSONObject(i).getString("rotationAddress"));
                                    bannerTitles.add(records.getJSONObject(i).getString("rotationTitle"));
                                    links.add(records.getJSONObject(i).getString("rotationUrl"));

                                    binding.bannerHome.setImages(bannerImages).setBannerTitles(bannerTitles)
                                            .setOnBannerListener(new OnBannerListener() {
                                                @Override
                                                public void OnBannerClick(int position) {
                                                    if(!TextUtils.isEmpty(links.get(position))){
                                                        CommonWebActivity.startActivity(context,bannerTitles.get(position),links.get(position));
                                                    }
                                                }
                                            }).start();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    private void initData(){
        binding.rcvHome.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        adapter = new BaseBindingRecyclerViewAdapter<PositionEntity, LayoutItemPositionBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.layout_item_position;
            }

            @Override
            protected void onBindItem(LayoutItemPositionBinding binding, final PositionEntity item, int position) {
                binding.setItem(item);
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new CallDialogUtils().show(FgHomeModel.this.context,item);
                    }
                });
            }
        };
        layoutManager = (LinearLayoutManager) binding.rcvHome.getLayoutManager();
        binding.rcvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastScrollItem + 1 == adapter.getItems().size()){
                    //加载数据
                    loadPosition();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastScrollItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        this.loadPosition();
        this.loadExperience();
        this.loadSalary();

    }

    private void loadSalary(){
        HttpUtils.getInstance().getSalaryList("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                        salarys.clear();
                        try {
                            JSONArray array = new JSONArray(t);
                            for(int i=0;i<array.length();i++){
                                Map<String,Object> data = new HashMap<>();
                                data.put("id",array.getJSONObject(i).getInt("sid"));
                                data.put("content",array.getJSONObject(i).getString("name"));
                                salarys.add(data);
                            }
                            Map<String,Object> data = new HashMap<>();
                            data.put("id",0);
                            data.put("content","不限");
                            salarys.add(0,data);
                            salaryTitle = new String[salarys.size()];
                            for(int i=0;i<salarys.size();i++){
                                salaryTitle[i] = (String) salarys.get(i).get("content");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    private void loadExperience(){
        HttpUtils.getInstance().getExperiences("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                        expriences.clear();
                        try {
                            JSONArray array = new JSONArray(t);
                            for(int i=0;i<array.length();i++){
                                Map<String,Object> data = new HashMap<>();
                                data.put("id",array.getJSONObject(i).getInt("eid"));
                                data.put("content",array.getJSONObject(i).getString("name"));
                                expriences.add(data);
                            }
                            Map<String,Object> data = new HashMap<>();
                            data.put("id",0);

                            data.put("content","不限");
                            expriences.add(0,data);
                            experienceTitle = new String[expriences.size()];
                            for(int i=0;i<expriences.size();i++){
                                experienceTitle[i] = (String) expriences.get(i).get("content");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }
    private void loadPosition(){
        if(load)
            return;
        load = true;
        HttpUtils.getInstance().getPositionList(experienceId,salaryId,cityId,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,false) {
                    @Override
                    public void onSuccess(String t) {
                        load = false;
                        PositionWrapper positionWrapper = new Gson().fromJson(t,PositionWrapper.class);
                        if(positionWrapper.getRecords()==null||positionWrapper.getRecords().size()<=0){
                            //无数据
                            if(page==1){
                                adapter.getItems().clear();
                                ToastUtils.showShortToast(context,"暂无职位数据");
                            }else{
                                ToastUtils.showShortToast(context,"无更多职位数据");
                            }
                        }else{
                            if(page==1){
                                adapter.getItems().clear();
                                adapter.getItems().addAll(positionWrapper.getRecords());
                            }else{
                                adapter.getItems().addAll(positionWrapper.getRecords());
                            }
                            page ++;
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        load = false;
                    }
                });
    }

    public void onExClick(){
        if(experienceTitle==null)
            return;
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
            return;
        }
        popupWindow = new ListPopupWindow(context);
        popupWindow.setAdapter(new ArrayAdapter<>(context,R.layout.item_drop_down_text,R.id.text1,experienceTitle));
        popupWindow.setWidth(width);
        popupWindow.setModal(true);
        popupWindow.setDropDownGravity(ListPopupWindow.MATCH_PARENT);
        popupWindow.setAnchorView(binding.tvWork);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.tvWork.setText(experienceTitle[position]);
                experienceId = Integer.parseInt(String.valueOf(expriences.get(position).get("id")));
                page = 1;
                loadPosition();
                popupWindow.dismiss();
            }
        });
        popupWindow.setListSelector(context.getResources().getDrawable(R.drawable.selector_n_white_press_gray));
        popupWindow.show();
    }

    public void onSalaryClick(){
        if(salaryTitle==null)
            return;
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
            return;
        }
        popupWindow = new ListPopupWindow(context);
        popupWindow.setAdapter(new ArrayAdapter<>(context,R.layout.item_drop_down_text,R.id.text1,salaryTitle));
        popupWindow.setWidth(width);
        popupWindow.setModal(true);
        popupWindow.setDropDownGravity(ListPopupWindow.MATCH_PARENT);
        popupWindow.setAnchorView(binding.tvSalary);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.tvSalary.setText(salaryTitle[position]);
                salaryId = Integer.parseInt(String.valueOf(salarys.get(position).get("id")));
                page = 1;
                loadPosition();
                popupWindow.dismiss();
            }
        });
        popupWindow.setListSelector(context.getResources().getDrawable(R.drawable.selector_n_white_press_gray));
        popupWindow.show();
    }

    public void onAreaClick(){
        new ChooseAreaDialogUtils().show(context, false, baseDialog, new ChooseAreaDialogUtils.OnOkListener() {
            @Override
            public void onCancel() {
                binding.tvArea.setText("所在地区");
                cityId = 0;
                page = 1;
                loadPosition();
            }

            @Override
            public void onOkClick(ProvinceEntity province, ProvinceEntity city, ProvinceEntity area) {
                if(province!=null){
                    binding.tvArea.setText(province.getName());
                }
                if(city!=null){
                    binding.tvArea.setText(binding.tvArea.getText().toString().concat(city.getName()));
                    cityId = city.getId();
                    page = 1;
                    loadPosition();
                }
            }
        });
    }
}
