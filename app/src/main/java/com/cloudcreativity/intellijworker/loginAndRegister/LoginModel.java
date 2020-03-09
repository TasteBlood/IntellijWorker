package com.cloudcreativity.intellijworker.loginAndRegister;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.cloudcreativity.intellijworker.entity.UserEntity;
import com.cloudcreativity.intellijworker.base.BaseDialogImpl;
import com.cloudcreativity.intellijworker.databinding.ActivityLoginBinding;
import com.cloudcreativity.intellijworker.main.MainActivity;
import com.cloudcreativity.intellijworker.utils.DefaultObserver;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.cloudcreativity.intellijworker.utils.SPUtils;
import com.cloudcreativity.intellijworker.utils.StrUtils;
import com.cloudcreativity.intellijworker.utils.ToastUtils;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel {
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> pwd = new ObservableField<>();
    private ActivityLoginBinding binding;
    private LoginActivity context;
    private BaseDialogImpl baseDialog;

    LoginModel(ActivityLoginBinding binding, LoginActivity context) {
        this.binding = binding;
        this.context = context;
        this.baseDialog = context;
    }

    public void onBack(){
        context.finish();
    }

    /**
     * 登录按钮点击
     */
    public void onLoginClick(){

        if(phone==null||TextUtils.isEmpty(phone.get())){
            ToastUtils.showShortToast(context,"身份证号格式有误");
            return;
        }
        if(pwd==null||TextUtils.isEmpty(pwd.get())){
            ToastUtils.showShortToast(context,"密码不能为空");
            return;
        }
        HttpUtils.getInstance().login(phone.get(), StrUtils.md5(pwd.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<String>(baseDialog,true) {
                    @Override
                    public void onSuccess(String t) {
                        UserEntity entity = new Gson().fromJson(t, UserEntity.class);
                        if(entity!=null&&entity.getId()!=0){
                            //保存当前的信息
                            SPUtils.get().putInt(SPUtils.Config.UID,entity.getWorkerDomain().getId());
                            SPUtils.get().putString(SPUtils.Config.TOKEN,entity.getToken());
                            SPUtils.get().putBoolean(SPUtils.Config.IS_LOGIN,true);
                            SPUtils.get().putString(SPUtils.Config.USER,new Gson().toJson(entity));
                            SPUtils.get().putString(SPUtils.Config.IDCARD,entity.getIdCardNumber());
                            context.finish();
                            context.startActivity(new Intent().setClass(context, MainActivity.class));
                        }else{
                            ToastUtils.showShortToast(context,"登录失败");
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        ToastUtils.showShortToast(context,"登录失败");
                    }
                });

    }

    /**
     * 注册按钮点击
     */
    public void onRegisterClick(){
        context.startActivity(new Intent(context,RegisterActivity.class));
    }

    /**
     * 忘记密码点击
     */
    public void onForgetClick(){
        context.startActivity(new Intent(context,ForgetPwdActivity.class));
    }
}
