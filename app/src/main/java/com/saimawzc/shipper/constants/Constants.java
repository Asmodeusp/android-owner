package com.saimawzc.shipper.constants;

import android.os.Environment;

import java.nio.charset.Charset;

/**
 * 作者：tryedhp on 2017/8/14/0014 18:28
 * 邮箱：try2017yx@163.com
 */
public class Constants {

    //public static final String Baseurl = "http://106.12.165.54:8005/";
    public static final String Baseurl = "https://app.api.wzcwlw.com/";
    public static int DEVICE_FIRM = -1;
    public static final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
    public static final String reshAccount_confirm = "reshAccount";//更新结算单
    public static final String reshAccount_unconfirm = "reshAccountun_confirm";//更新结算单
    public static final String APK_SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/nxdriver/"+"huozhu.apk";
    public static final String resSHr = "resSHr";//收货人
    public static final String SHRCHANG_ROLE = "resSHr_ROLE";//收货人
}
