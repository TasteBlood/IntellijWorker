package com.cloudcreativity.intellijworker.loginAndRegister;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.cloudcreativity.intellijworker.base.BaseDialogImpl;
import com.cloudcreativity.intellijworker.databinding.ActivityForgetPwdBinding;
import com.cloudcreativity.intellijworker.receiver.MyBusinessReceiver;
import com.cloudcreativity.intellijworker.utils.DefaultObserver;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.cloudcreativity.intellijworker.utils.StrUtils;
import com.cloudcreativity.intellijworker.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForgetPwdModel {
    public ObservableField<String> pwd1 = new ObservableField<>();
    public ObservableField<String> pwd2 = new ObservableField<>();

    private BaseDialogImpl baseDialog;
    private ForgetPwdActivity context;
    private ActivityForgetPwdBinding binding;

    ForgetPwdModel(BaseDialogImpl baseDialog, ForgetPwdActivity context, ActivityForgetPwdBinding binding) {
        this.baseDialog = baseDialog;
        this.context = context;
        this.binding = binding;
    }

    /**
     * 注册点击
     */
    public void onSubmitClick(){
        if(TextUtils.isEmpty(pwd1.get())){
            binding.tilPwd1.setError("旧密码不能为空");
            return;
        }
        binding.tilPwd1.setError(null);
        if(TextUtils.isEmpty(pwd2.get())){
            binding.tilPwd2.setError("新密码不一致");
            return;
        }
        binding.tilPwd2.setError(null);

        HttpUtils.getInstance()
                .editPwd(pwd1.get(),pwd2.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        ToastUtils.showShortToast(context,"密码修改成功，需重新登录");
                        Intent intent = new Intent();
                        intent.setAction(MyBusinessReceiver.ACTION_RE_LOGIN);
                        context.sendBroadcast(intent);
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        ToastUtils.showShortToast(context,"密码修改失败");
                    }
                });
    }
}
