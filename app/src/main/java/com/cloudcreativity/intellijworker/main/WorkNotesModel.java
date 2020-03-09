package com.cloudcreativity.intellijworker.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.intellijworker.base.BaseDialogImpl;
import com.cloudcreativity.intellijworker.databinding.ActivityWorkNotesBinding;
import com.cloudcreativity.intellijworker.databinding.LayoutItemWorkRotesBinding;
import com.cloudcreativity.intellijworker.entity.DayWorkEntity;
import com.cloudcreativity.intellijworker.entity.SimpleWorkEntity;
import com.cloudcreativity.intellijworker.entity.UserProjectEntity;
import com.cloudcreativity.intellijworker.utils.DefaultObserver;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.cloudcreativity.intellijworker.utils.SPUtils;
import com.cloudcreativity.intellijworker.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WorkNotesModel {

    private BaseDialogImpl baseDialog;
    private Activity context;
    private UserProjectEntity entity;
    private ActivityWorkNotesBinding binding;

    public ObservableField<String> selectDate = new ObservableField<>();
    public ObservableField<String> dateLimit = new ObservableField<>();
    public BaseBindingRecyclerViewAdapter<SimpleWorkEntity, LayoutItemWorkRotesBinding> adapter;

    private Calendar start;
    private Calendar end;
    private Calendar select;

    WorkNotesModel(final BaseDialogImpl baseDialog, Activity context, final UserProjectEntity entity, ActivityWorkNotesBinding binding) {
        this.baseDialog = baseDialog;
        this.context = context;
        this.entity = entity;
        this.binding = binding;
        this.binding.rcvWorkNotes.setLayoutManager(new GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false));


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
                        if (item.getCardNum() <= 0)
                            return;
                        String[] split = item.getCardTime().split("-");
                        HttpUtils.getInstance()
                                .getCardByDay(entity.getwId(),Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DefaultObserver<String>(baseDialog, true) {
                                    @Override
                                    public void onSuccess(String t) {
                                        Type type = new TypeToken<List<DayWorkEntity>>() {
                                        }.getType();
                                        List<DayWorkEntity> entities = new Gson().fromJson(t, type);
                                        StringBuffer buffer = new StringBuffer();
                                        for (DayWorkEntity entity1 : entities) {
                                            buffer.append(entity1.formatDate()).append("  ").append(entity1.getDirection().equals("01")?"进门":"出门").append("\n");
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
            if(TextUtils.isEmpty(entity.getStartDate())||TextUtils.isEmpty(entity.getCompleteDate())){
                ToastUtils.showShortToast(context,"开工或完工日期不能为空");
                context.finish();
                return;
            }

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            start = Calendar.getInstance();
            start.setTime(format.parse(entity.getStartDate()));
            end = Calendar.getInstance();
            end.setTime(format.parse(entity.getCompleteDate()));
            Calendar today = Calendar.getInstance();
            /*
             * 当前月如果是在开工和开工前的，加载开工所在月的考勤记录
             * 当前月在开工和完工之间的，直接加载
             * 当前月在完工和完工后的，直接加载完工所在月的考勤记录
             * 完工和完工之后加载
             */
            dateLimit.set(entity.getStartDate() + " 至 " + entity.getCompleteDate());
            if (entity.getProStatus().equals("完工")) {
                //加载完工
                loadData(end.get(Calendar.YEAR) + "-" + (formatZero(end.get(Calendar.MONTH) + 1)));
                selectDate.set(end.get(Calendar.YEAR) + "年" + (end.get(Calendar.MONTH) + 1) + "月");
                select = end;
            } else {
                selectDate.set(today.get(Calendar.YEAR) + "年" + (today.get(Calendar.MONTH) + 1) + "月");
                select = today;
                loadData(today.get(Calendar.YEAR) + "-" + (formatZero(today.get(Calendar.MONTH) + 1)));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void onNextClick() {
        select.add(Calendar.MONTH, 1);
        loadData(select.get(Calendar.YEAR) + "-" + formatZero(select.get(Calendar.MONTH) + 1));
        selectDate.set(select.get(Calendar.YEAR) + "年" + formatZero(select.get(Calendar.MONTH) + 1) + "月");
    }

    public void onLastClick() {
        select.add(Calendar.MONTH, -1);
        loadData(select.get(Calendar.YEAR) + "-" + formatZero(select.get(Calendar.MONTH) + 1));
        selectDate.set(select.get(Calendar.YEAR) + "年" + formatZero(select.get(Calendar.MONTH) + 1) + "月");
    }

    //点击打开月份选择器
    public void onMonthClick() {
        //先获取到开工日期到完工日期之间的所有月份
        List<String> months = new ArrayList<>();
        Calendar current = (Calendar) start.clone();
        while (current.before(end)) {
            //满足这个条件就能进行月份统计
            months.add(current.get(Calendar.YEAR) + "年" + formatZero((current.get(Calendar.MONTH) + 1))+"月");
            current.add(Calendar.MONTH, 1);
        }
        //展示对话框
        final CharSequence[] sequences = months.toArray(new CharSequence[0]);
        new AlertDialog.Builder(context, 0)
                .setTitle("月份选择")
                .setItems(sequences, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //设置当前的时间
                        String current = (String) sequences[which];
                        DateFormat format = new SimpleDateFormat("yyyy年MM月",Locale.CHINA);
                        try {
                            Date parse = format.parse(current);
                            Calendar instance = Calendar.getInstance();
                            instance.setTime(parse);
                            select.set(Calendar.YEAR,instance.get(Calendar.YEAR));
                            select.set(Calendar.MONTH,instance.get(Calendar.MONTH));
                            selectDate.set(current);
                            dialog.dismiss();
                            loadData(select.get(Calendar.YEAR) + "-" + formatZero(select.get(Calendar.MONTH) + 1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            ToastUtils.showShortToast(context,"系统异常，请重试");
                        }
                    }
                }).show();
    }

    private void loadData(String date) {
        String[] split = date.split("-");
        HttpUtils.getInstance()
                .getCardByMonth(entity.getProjectCode(), entity.getwId(),Integer.parseInt(split[0]), Integer.parseInt(split[1]))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog, true) {
                    @Override
                    public void onSuccess(String t) {
                        Type type = new TypeToken<List<SimpleWorkEntity>>() {
                        }.getType();
                        List<SimpleWorkEntity> entities = new Gson().fromJson(t, type);
                        if (entities == null || entities.isEmpty())
                            return;
                        Collections.sort(entities, new Comparator<SimpleWorkEntity>() {
                            @Override
                            public int compare(SimpleWorkEntity o1, SimpleWorkEntity o2) {
                                return o1.getDay().compareTo(o2.getDay());
                            }
                        });
                        //判断第一天是这个月的星期几，完了在进行计算
                        try {
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                            Date parse = format.parse(entities.get(0).getCardTime());
                            Calendar instance = Calendar.getInstance();
                            instance.setTime(parse);
                            int index = instance.get(Calendar.DAY_OF_WEEK);
                            for (int i = 0; i < index - 1; i++) {
                                entities.add(0, new SimpleWorkEntity());
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

    private String formatZero(int a) {
        return a < 10 ? "0" + a : a + "";
    }
}
