package com.cloudcreativity.intellijworker.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.databinding.DialogLayoutCallBinding;
import com.cloudcreativity.intellijworker.entity.PositionEntity;

public class CallDialogUtils {
    public PositionEntity entity;
    private Dialog dialog;
    private Activity context;

    public void show(Activity context,PositionEntity entity) {
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
            return;
        }
        this.context = context;
        this.entity = entity;
        dialog = new Dialog(context, R.style.myProgressDialogStyle);
        DialogLayoutCallBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_layout_call,null,false);
        binding.setItem(this);
        dialog.setContentView(binding.getRoot());
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        int width = context.getResources().getDisplayMetrics().widthPixels;
        params.width = width-40;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onCallClick(){
        onDismiss();
        if(context.checkSelfPermission(Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel://"+entity.getContactsMobile()));
            context.startActivity(intent);
        }else{
            ToastUtils.showShortToast(context,"请在设置中打开拨打电话功能");
        }
    }

    private void onDismiss(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    public void onClose(){
        onDismiss();
    }
}
