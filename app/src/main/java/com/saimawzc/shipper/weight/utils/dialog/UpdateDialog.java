package com.saimawzc.shipper.weight.utils.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.constants.Constants;
import com.saimawzc.shipper.dto.VersonDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.consignee.ConsigneeMainActivity;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import com.saimawzc.shipper.weight.utils.update.InstallUtils;


/**
 * Created by yyfy_yf on 2017/9/26.
 * 版本更新Dialog
 */

public class UpdateDialog {

    public Dialog versionDialog;
    public TextView btnUpdate;
    public TextView tvMsg;
    public TextView tvNoUpdate;
    UserInfoDto userInfoDto;
    /**
     * 自定义dialog two
     */
    public void customVersionDialogTwo(final BaseActivity mContext, final VersonDto dto) {
         versionDialog = new UpdateBaseDialog(mContext, R.style.BaseDialog, R.layout.dialog_update);
         tvMsg = (TextView) versionDialog.findViewById(R.id.tvContect);
         tvNoUpdate = (TextView) versionDialog.findViewById(R.id.tvNoUpdate);
         btnUpdate=(TextView) versionDialog.findViewById(R.id.btnUpdate);
         if(dto.getMandatoryUpdate()==1){
             tvNoUpdate.setVisibility(View.GONE);
         }
         if(dto.getIsSHowNo()==1){
             tvNoUpdate.setVisibility(View.VISIBLE);
         }

        if(mContext.downloadCallBack!=null){
            if (InstallUtils.isDownloading()) {
                InstallUtils.setDownloadCallBack(mContext.downloadCallBack);
            }
        }
        tvNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.ID,""))){//已经登录
                    userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
                    Log.e("msg","111");
                    if(userInfoDto!=null){
                        if(userInfoDto.getRole()==1){
                            readyGo(MainActivity.class,mContext);
                        } else if(userInfoDto.getRole()==4){
                            readyGo(ConsigneeMainActivity.class,mContext);
                        }
                    }else {
                        readyGo(LoginActivity.class,mContext);
                    }
                }else {
                    readyGo(LoginActivity.class,mContext);
                }
                dissmiss();

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dissmiss();
                            mContext.initCallBack();
                            InstallUtils.with(mContext)
                                    //必须-下载地址
                                    .setApkUrl(dto.getDownloadLink())
                                    //非必须-下载保存的文件的完整路径+name.apk
                                    .setApkPath(Constants.APK_SAVE_PATH)
                                    //非必须-下载回调
                                    .setCallBack(mContext.downloadCallBack)
                                    //开始下载
                                    .startDownload();
                        }
                    });
         versionDialog.setCanceledOnTouchOutside(false);
         versionDialog.setCancelable(false);
         versionDialog.show();

    }


    public void  dissmiss(){
        if(versionDialog!=null){
            versionDialog.dismiss();
        }
    }

    public void readyGo(Class<?> clazz,BaseActivity activity) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }
}
