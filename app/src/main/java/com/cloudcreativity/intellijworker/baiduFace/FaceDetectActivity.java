package com.cloudcreativity.intellijworker.baiduFace;

import android.content.Intent;
import android.os.Bundle;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.cloudcreativity.intellijworker.base.BaseApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaceDetectActivity extends FaceLivenessActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        BaseApp.app.addActivity(this);
        initLib();
        setFaceConfig();
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化SDK
     */
    private void initLib() {
        //// 为了android和ios 区分授权，appId=appname_face_android ,其中appname为申请sdk时的应用名
        // 应用上下文
        // 申请License取得的APPID
        // assets目录下License文件名
        FaceSDKManager.getInstance().initialize(this, Config.licenseID, Config.licenseFileName);
        // setFaceConfig();
    }

    private void setFaceConfig() {
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
        List<LivenessTypeEnum> livenessList = new ArrayList<>();
        livenessList.add(LivenessTypeEnum.Eye);
//        livenessList.add(LivenessTypeEnum.Mouth);
//        livenessList.add(LivenessTypeEnum.HeadDown);
//        livenessList.add(LivenessTypeEnum.HeadUp);
//        livenessList.add(LivenessTypeEnum.HeadLeft);
//        livenessList.add(LivenessTypeEnum.HeadRight);
        livenessList.add(LivenessTypeEnum.HeadLeftOrRight);
        config.setLivenessTypeList(livenessList);
        config.setLivenessRandom(false);
        config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
        config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
        config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
        config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
        config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
        config.setCheckFaceQuality(true);
        config.setFaceDecodeNumberOfThreads(2);

        FaceSDKManager.getInstance().setFaceConfig(config);
    }

    @Override
    public void onLivenessCompletion(com.baidu.idl.face.platform.FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        Intent intent = new Intent();
        if (status == FaceStatusEnum.OK && mIsCompletion) {
            //获取到bestImage0 这是最佳的图片，只需要把这个图片上传到服务器上面就可以进行使用
            String base64data = base64ImageMap.get("bestImage0");
            intent.putExtra("image",base64data);
            setResult(RESULT_OK,intent);
            finish();
        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            intent.putExtra("image","");
            setResult(RESULT_OK,intent);
            finish();
        }

    }

    @Override
    public void finish() {
        BaseApp.app.removeActivity(this);
        super.finish();
    }
}
