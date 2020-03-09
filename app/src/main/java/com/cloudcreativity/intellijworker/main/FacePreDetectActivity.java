package com.cloudcreativity.intellijworker.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.cloudcreativity.intellijworker.R;
import com.cloudcreativity.intellijworker.baiduFace.FaceDetectActivity;
import com.cloudcreativity.intellijworker.base.BaseActivity;
import com.cloudcreativity.intellijworker.utils.APIService;
import com.cloudcreativity.intellijworker.utils.AppConfig;
import com.cloudcreativity.intellijworker.utils.HttpUtils;
import com.cloudcreativity.intellijworker.utils.LogUtils;
import com.cloudcreativity.intellijworker.utils.SPUtils;
import com.cloudcreativity.intellijworker.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FacePreDetectActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView;
    private File result;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查权限
        if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            setContentView(R.layout.activity_face_pre_detect);
            findViewById(R.id.btn_detect).setOnClickListener(this);
            findViewById(R.id.btn_upload).setOnClickListener(this);
            Toolbar bar = findViewById(R.id.tlb_face);
            setSupportActionBar(bar);
            bar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            imageView = findViewById(R.id.iv_face);
        }else{
            //检查权限
            requestPermissions(new String[]{Manifest.permission.CAMERA},100);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED)
            return;
        if (requestCode == 100) {
            String faceData = data.getStringExtra("image");
            LogUtils.e("xuxiwu", faceData);
            byte[] temp = Base64.decode(faceData, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            //将bitmap保存在本地的文件夹中
            result = new File(getApplicationContext().getExternalCacheDir(), String.format(AppConfig.FILE_NAME, System.currentTimeMillis(), "jpeg"));
            try {
                imageView.setImageBitmap(bitmap);
                FileOutputStream outputStream = new FileOutputStream(result);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_detect:
                Intent intent = new Intent(this, FaceDetectActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.btn_upload:
                uploadFace();
                break;
        }
    }

    private void uploadFace() {
        if (result != null) {
            SPUtils spUtils = SPUtils.get();
            FacePreDetectActivity.this.showProgress("上传中...");
            HttpUtils.uploadImage(spUtils.getToken(), SPUtils.get().getUser().getWorkerDomain().getWorkerName(), spUtils.getIdCard(), result, APIService.HOST + "/app/worker/addFace", this,
                    new HttpUtils.OnSuccessListener() {
                        @Override
                        public void onSuccess(String response) {
                            FacePreDetectActivity.this.dismissProgress();
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getInt("status") == 1) {
                                    ToastUtils.showShortToast(FacePreDetectActivity.this, "人脸上传成功");
                                    finish();
                                } else {
                                    ToastUtils.showShortToast(FacePreDetectActivity.this, "人脸上传失败，请重试");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                ToastUtils.showShortToast(FacePreDetectActivity.this, "数据解析失败，请重试");
                            }

                        }

                        @Override
                        public void onError(String error) {
                            LogUtils.e("xuxiwu", error);
                            FacePreDetectActivity.this.dismissProgress();
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(Manifest.permission.CAMERA.equals(permissions[0])&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //开始
                setContentView(R.layout.activity_face_pre_detect);
                findViewById(R.id.btn_detect).setOnClickListener(this);
                findViewById(R.id.btn_upload).setOnClickListener(this);
                Toolbar bar = findViewById(R.id.tlb_face);
                setSupportActionBar(bar);
                bar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                imageView = findViewById(R.id.iv_face);
            }else{
                ToastUtils.showShortToast(this,"请在手机设置中打开拍照权限");
            }
        }
    }
}
