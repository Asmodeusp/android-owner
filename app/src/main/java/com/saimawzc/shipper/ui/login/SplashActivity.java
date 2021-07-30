package com.saimawzc.shipper.ui.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.VersonDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.consignee.ConsigneeMainActivity;
import com.saimawzc.shipper.weight.utils.dialog.UpdateDialog;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import com.gyf.immersionbar.ImmersionBar;
import com.werb.permissionschecker.PermissionChecker;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
   private UserInfoDto userInfoDto;
    @Override
    protected int getViewId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉
        return R.layout.activity_splash;
    }
    String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @Override
    protected void init() {
        initpermissionChecker();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        if(permissionChecker.isLackPermissions(PERMISSIONS)){
            turnToMain();
        }else {
            getVerson();
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    private void turnToMain() {
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.ID,""))){//已经登录
            if(userInfoDto.getRole()==1){//货主
                Log.e("msg","跳转货主");
                readyGo(MainActivity.class);
            }else if(userInfoDto.getRole()==4){//收货人
                readyGo(ConsigneeMainActivity.class);
            }
        }else {
            Log.e("msg","跳转登陆");
            readyGo(LoginActivity.class);
        }
    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
    private UpdateDialog updateDialog;//版本更新Dialog
    /***
     * 获取版本更新接口
     * **/
    private void getVerson(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appSource","1");//安卓
            jsonObject.put("appType",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        authApi.getVerson(body).enqueue(new CallBack<VersonDto>() {
            @Override
            public void success(final VersonDto response) {
                if(response==null){
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            } finally {
                                turnToMain();
                            }
                        }
                    }.start();
                }
                if(!response.getVersionNum().equals(BaseActivity.getVersionName(SplashActivity.this))){
                    updateDialog = new UpdateDialog();
                    updateDialog.customVersionDialogTwo(SplashActivity.this,response);
                    if(response.getUpdateContent().contains("\\n")){
                        updateDialog.tvMsg.setText(response.getUpdateContent().replace(
                                "\\n"
                                ,
                                "\n"
                        ));
                    }else {
                        updateDialog.tvMsg.setText(response.getUpdateContent());
                    }
                }else {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            } finally {
                                turnToMain();
                            }
                        }
                    }.start();
                }
            }
            @Override
            public void fail(String code, String message) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        } finally {
                            turnToMain();
                        }
                    }
                }.start();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
                    // 执行你的相关操作
                    getVerson();
                } else {
                    // 权限拒绝后的提示
                    permissionChecker.showDialog();
                }
                break;
        }
    }
}
