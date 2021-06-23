package com.saimawzc.shipper.ui.order.creatorder.richtext;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import java.io.File;
import butterknife.BindView;

public class JcTakePhoneActivity extends BaseActivity {

    @BindView(R.id.jcameraview)
    JCameraView jCameraView;
    private  String[] PERMISSION_AUDIO = {
            Manifest.permission.RECORD_AUDIO
    };
    @Override
    protected int getViewId() {
        return R.layout.activity_jc_takephone;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    @Override
    protected void init() {
          //设置视频保存路径
        initpermissionChecker();
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "nxshiper");
        //设置只能录像或只能拍照或两种都可以（默认两种都可以）
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_RECORDER);
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        jCameraView.setTip("长按拍摄视频");

        if(permissionChecker.isLackPermissions(PERMISSION_AUDIO)){
            permissionChecker.requestPermissions();
        }

    }

    @Override
    protected void initListener() {
       //JCameraView监听
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //打开Camera失败回调
                Log.i("CJT", "open camera error");
            }
            @Override
            public void AudioPermissionError() {
                //没有录取权限回调
                Log.i("CJT", "AudioPermissionError");
            }
        });
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                Log.i("JCameraView", "bitmap = " + bitmap.getWidth());
            }
            @Override
            public void recordSuccess(String url,Bitmap firstFrame) {
                Log.e("msg","点击返回"+url);
                //获取视频路径
                Intent intent = new Intent();
                intent.putExtra("path", url);
                setResult(RESULT_OK, intent);
                finish();
            }

        });
         //左边按钮点击事件
        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        jCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }
}
