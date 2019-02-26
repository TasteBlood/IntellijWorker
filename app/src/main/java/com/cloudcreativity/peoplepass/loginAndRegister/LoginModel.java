package com.cloudcreativity.peoplepass.loginAndRegister;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.cloudcreativity.peoplepass.base.BaseDialogImpl;
import com.cloudcreativity.peoplepass.databinding.ActivityLoginBinding;
import com.cloudcreativity.peoplepass.utils.StrUtils;

public class LoginModel {
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> pwd = new ObservableField<>();
    private ActivityLoginBinding binding;
    private LoginActivity context;
    private BaseDialogImpl baseDialog;

    public LoginModel(ActivityLoginBinding binding, LoginActivity context) {
        this.binding = binding;
        this.context = context;
        this.baseDialog = context;
    }

    /**
     * 登录按钮点击
     */
    public void onLoginClick(){
        if(TextUtils.isEmpty(phone.get())||!StrUtils.isPhone(phone.get())){
            binding.tilPhone.setError("手机号格式不正确");
            return;
        }
        binding.tilPhone.setError(null);
        if(TextUtils.isEmpty(pwd.get())){
            binding.tilPwd.setError("密码不能为空");
            return;
        }
        binding.tilPwd.setError(null);
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
