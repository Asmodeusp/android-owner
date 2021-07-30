package com.saimawzc.shipper.ui.my.set;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;


import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.VersonDto;
import com.saimawzc.shipper.ui.consignee.ConsigneeMainActivity;
import com.saimawzc.shipper.ui.login.ForgetPassActivity;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.ui.my.PersonCenterActivity;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.shipper.weight.utils.dialog.UpdateDialog;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import com.werb.permissionschecker.PermissionChecker;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MySetFragment extends BaseFragment {

    @BindView(R.id.toolbar) Toolbar toolbar;
    public  final String[] PERMISSIONSq = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    public int initContentView() {
        return R.layout.fragment_set;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"系统设置");
        initpermissionChecker();

    }

    @OnClick({R.id.rlforgetword,R.id.rlsuggest,R.id.rlupdate})
    public void click(View view){
        Bundle bundle;
        switch (view.getId()){
            case R.id.rlforgetword:
                readyGo(ForgetPassActivity.class);
                break;
            case R.id.rlsuggest:
                bundle=new Bundle();
                bundle.putString("from","addsuggest");
                readyGo(PersonCenterActivity.class,bundle);
                break;
            case R.id.rlupdate:
                if(permissionChecker.isLackPermissions(PERMISSIONSq)){
                    permissionChecker.requestPermissions();
                }else {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getVerson();
                        }
                    });
                }
                break;
        }

    }

    @Override
    public void initData() {

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
        context.authApi.getVerson(body).enqueue(new CallBack<VersonDto>() {
            @Override
            public void success(final VersonDto response) {
                if(response==null|| TextUtils.isEmpty(response.getVersionNum())){
                    return;
                }
                if(!response.getVersionNum().equals(BaseActivity.getVersionName(context))){
                    updateDialog = new UpdateDialog();
                    updateDialog.customVersionDialogTwo(context,response);
                    if(response.getUpdateContent().contains("\\n")){
                        updateDialog.tvMsg.setText(response.getUpdateContent().replace(
                                "\\n"
                                ,
                                "\n"
                        ));
                    }else {
                        updateDialog.tvMsg.setText(response.getUpdateContent());
                    }
                }else {//和服务器版本相同
                    updateDialog = new UpdateDialog();
                    response.setIsSHowNo(1);
                    updateDialog.customVersionDialogTwo(context,response);
                    updateDialog.tvMsg.setText("当前版本和服务器版本一致，是否替换");
                }
            }
            @Override
            public void fail(String code, String message) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
                    // 执行你的相关操作
                    getVerson();
                } else {
                    // 权限拒绝后的提示
                    permissionChecker.showDialog();
                    //getVerson();
                }
                break;
        }
    }
}
