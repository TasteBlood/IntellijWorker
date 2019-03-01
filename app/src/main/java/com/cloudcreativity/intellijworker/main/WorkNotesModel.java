package com.cloudcreativity.intellijworker.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijworker.base.BaseDialogImpl;
import com.cloudcreativity.intellijworker.databinding.ActivityWorkNotesBinding;
import com.cloudcreativity.intellijworker.databinding.LayoutItemWorkRotesBinding;
import com.cloudcreativity.intellijworker.entity.DayWorkEntity;
import com.cloudcreativity.intellijworker.entity.ProjectEntity;
import com.cloudcreativity.intellijworker.entity.SimpleWorkEntity;
import com.cloudcreativity.intellijworker.utils.DefaultObserver;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.cloudcreativity.intellijworker.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WorkNotesModel {

    private BaseDialogImpl baseDialog;
    private Activity context;
    private ProjectEntity entity;
    private ActivityWorkNotesBinding binding;

    public ObservableField<String> selectDate = new ObservableField<>();
    public ObservableField<String> dateLimit = new ObservableField<>();
    public BaseBindingRecyclerViewAdapter<SimpleWorkEntity, LayoutItemWorkRotesBinding> adapter;

    private Calendar start;
    private Calendar end;
    private Calendar select;

    WorkNotesModel(final BaseDialogImpl baseDialog, Activity context, final ProjectEntity entity, ActivityWorkNotesBinding binding) {
        this.baseDialog = baseDialog;
        this.context = context;
        this.entity = entity;
        this.binding = binding;
        this.binding.rcvWorkNotes.setLayoutManager(new GridLayoutManager(context, 7,LinearLayoutManager.VERTICAL,false));


        adapter = new BaseBindingRecyclerViewAdapter<SimpleWorkEntity, LayoutItemWorkRotesBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.layout_item_work_rotes;
            }

            @Override
            protected void onBindItem(LayoutItemWorkRotesBinding binding, final SimpleWorkEntity item, int position) {
                binding.setItem(item);
                binding.layoutItemWork.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //显示打卡详情
                        if(item.getCardNum()<=0)
                            return;
                        HttpUtils.getInstance()
                                .getCardByDay(entity.getPid(),SPUtils.get().getUid(),item.getCardTime())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                                    @Override
                                    public void onSuccess(String t) {
                                        Type type = new TypeToken<List<DayWorkEntity>>() {
                                        }.getType();
                                        List<DayWorkEntity> entities = new Gson().fromJson(t,type);
                                        StringBuffer buffer = new StringBuffer();
                                        for(DayWorkEntity entity1:entities){
                                            buffer.append(entity1.getCardTime()).append("  ").append(entity1.getDoorNum()).append("\n");
                                        }
                                        new AlertDialog.Builder(context).setTitle("出勤详情")
                                                .setMessage(buffer.toString())
                                                .show();
                                    }

                                    @Override
                                    public void onFail(ExceptionReason msg) {

                                    }
                                });
                    }
                });
            }
        };

        try {
            start = Calendar.getInstance();
            start.setTime(SimpleDateFormat.getDateInstance().parse(entity.getStartTime()));
            end = Calendar.getInstance();
            end.setTime(SimpleDateFormat.getDateInstance().parse(entity.getEndTime()));
            Calendar today = Calendar.getInstance();
            /*
             * 当前月如果是在开工和开工前的，加载开工所在月的考勤记录
             * 当前月在开工和完工之间的，直接加载
             * 当前月在完工和完工后的，直接加载完工所在月的考勤记录
             * 完工和完工之后加载
             */
            dateLimit.set(entity.getStartTime()+" 至 "+entity.getEndTime());
            if(entity.getPstate()==-1){
                //加载完工
                loadData(entity.getPid(),end.get(Calendar.YEAR)+"-"+(fomartZero(end.get(Calendar.MONTH)+1)));
                selectDate.set(end.get(Calendar.YEAR)+"年"+(end.get(Calendar.MONTH)+1)+"月");
                select = end;
            }else{
                if(today.get(Calendar.YEAR)==start.get(Calendar.YEAR)){
                    if(today.get(Calendar.MONTH)<=start.get(Calendar.MONTH)){
                        loadData(entity.getPid(),end.get(Calendar.YEAR)+"-"+fomartZero((end.get(Calendar.MONTH)+1)));
                        selectDate.set(start.get(Calendar.YEAR)+"年"+(start.get(Calendar.MONTH)+1)+"月");
                        select = start;
                    }else{
                        loadData(entity.getPid(),end.get(Calendar.YEAR)+"-"+fomartZero(end.get(Calendar.MONTH)+1));
                        selectDate.set(today.get(Calendar.YEAR)+"年"+(today.get(Calendar.MONTH)+1)+"月");
                        select = today;
                    }
                }else if(today.get(Calendar.YEAR)<start.get(Calendar.YEAR)){
                    loadData(entity.getPid(),end.get(Calendar.YEAR)+"-"+fomartZero(end.get(Calendar.MONTH)+1));
                    selectDate.set(start.get(Calendar.YEAR)+"年"+(start.get(Calendar.MONTH)+1)+"月");
                    select = start;
                }else{
                    loadData(entity.getPid(),end.get(Calendar.YEAR)+"-"+fomartZero(end.get(Calendar.MONTH)+1));
                    selectDate.set(today.get(Calendar.YEAR)+"年"+(today.get(Calendar.MONTH)+1)+"月");
                    select = today;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void onNextClick(){
        select.add(Calendar.MONTH,1);
        loadData(entity.getPid(),select.get(Calendar.YEAR)+"-"+fomartZero(select.get(Calendar.MONTH)+1));
        selectDate.set(select.get(Calendar.YEAR)+"年"+(select.get(Calendar.MONTH)+1)+"月");
    }

    public void onLastClick(){
        select.add(Calendar.MONTH,-1);
        loadData(entity.getPid(),select.get(Calendar.YEAR)+"-"+fomartZero(select.get(Calendar.MONTH)+1));
        selectDate.set(select.get(Calendar.YEAR)+"年"+(select.get(Calendar.MONTH)+1)+"月");
    }

    private void loadData(int pid, String date){
        HttpUtils.getInstance()
                .getCardByMonth(pid, SPUtils.get().getUid(),date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        Type type = new TypeToken<List<SimpleWorkEntity>>() {
                        }.getType();
                        List<SimpleWorkEntity> entities = new Gson().fromJson(t, type);
                        if(entities==null||entities.isEmpty())
                            return;
                        Collections.sort(entities, new Comparator<SimpleWorkEntity>() {
                            @Override
                            public int compare(SimpleWorkEntity o1, SimpleWorkEntity o2) {
                                return o1.getDay().compareTo(o2.getDay());
                            }
                        });
                        //判断第一天是这个月的星期几，完了在进行计算
                        try {
                            Date parse = SimpleDateFormat.getDateInstance().parse(entities.get(0).getCardTime());
                            Calendar instance = Calendar.getInstance();
                            instance.setTime(parse);
                            int index = instance.get(Calendar.DAY_OF_WEEK);
                            for(int i=0;i<index-1;i++){
                                entities.add(0,new SimpleWorkEntity());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        adapter.getItems().clear();
                        adapter.getItems().addAll(entities);
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    private String fomartZero(int a){
        return a<10?"0"+a:a+"";
    }
}
