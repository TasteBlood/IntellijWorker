package com.cloudcreativity.intellijworker.fragments;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.cloudcreativity.intellijworker.baiduFace.FaceDetectActivity;
import com.cloudcreativity.intellijworker.base.BaseDialogImpl;
import com.cloudcreativity.intellijworker.databinding.FragmentMineBinding;
import com.cloudcreativity.intellijworker.entity.UserEntity;
import com.cloudcreativity.intellijworker.main.FacePreDetectActivity;
import com.cloudcreativity.intellijworker.main.LawActivity;
import com.cloudcreativity.intellijworker.main.PassRecordActivity;
import com.cloudcreativity.intellijworker.main.PassRecordModel;
import com.cloudcreativity.intellijworker.main.SettingActivity;
import com.cloudcreativity.intellijworker.utils.GlideUtils;
import com.cloudcreativity.intellijworker.utils.SPUtils;
import com.cloudcreativity.intellijworker.utils.ToastUtils;

public class FgMineModel {
    private BaseDialogImpl baseDialog;
    private Activity context;
    private FragmentMineBinding binding;

    public ObservableField<UserEntity> user = new ObservableField<>();
    FgMineModel(BaseDialogImpl baseDialog, Activity context, FragmentMineBinding binding) {
        this.baseDialog = baseDialog;
        this.context = context;
        this.binding = binding;
        user.set(SPUtils.get().getUser());
    }

    public void onLawClick(){
        context.startActivity(new Intent(context, LawActivity.class));
    }

    public void onReportClick(){
        context.startActivity(new Intent(context, PassRecordActivity.class));
    }

    public void onSetClick(){
        context.startActivity(new Intent(context,SettingActivity.class));
    }

    public void onFaceClick(){context.startActivity(new Intent(context,FacePreDetectActivity.class));}

    @BindingAdapter("mineAvatar")
    public static void loadAvatar(ImageView imageView, String url){
        GlideUtils.load(imageView.getContext(),url,imageView);
    }
}
