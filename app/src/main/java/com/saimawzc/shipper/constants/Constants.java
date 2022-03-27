package com.saimawzc.shipper.constants;

import android.os.Environment;

import com.saimawzc.shipper.base.BaseActivity;

/**
 * 作者：tryedhp on 2017/8/14/0014 18:28
 * 邮箱：try2017yx@163.com
 */
public class Constants {

//    public static final String Baseurl = "http://106.12.165.54:8005/";
//    public static final String Baseurl = "http://180.76.240.138:8005/";
//    public static final String Baseurl = "http://120.48.17.182:8005/";
//    public static final String Baseurl = "http://192.168.102.15:8005/";
//    public static final String Baseurl = "http://192.168.101.30:8005/";
    public static final String Baseurl = "https://app.api.wzcwlw.com/";
    public static int DEVICE_FIRM = -1;
    public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
    public static final String reshAccount_confirm = "reshAccount";//更新结算单
    public static final String reshAccount_unconfirm = "reshAccountun_confirm";//更新结算单
    public static final String APK_SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/nxshiper/"+ BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm")+"huozhu.apk";

    public static final String resSHr = "resSHr";//收货人

    public static final String SHRCHANG_ROLE = "resSHr_ROLE";//收货人
    public static final String reshService = "reshService";//刷新服务方
    public static final String reshCarLeaderList = "reshCarLeaderList";//刷新车队长列表
    public static final String reshTeamDelation = "reshTeamDelation";//刷新车队长详情
}
