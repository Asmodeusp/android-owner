package com.saimawzc.shipper.base;

import static com.saimawzc.shipper.ui.my.identification.CargoOwnerFragment.REQUEST_CODE_PIC;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.gyf.immersionbar.ImmersionBar;
import com.nanchen.compresshelper.CompressHelper;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.dto.login.AreaDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.consignee.ConsigneeMainActivity;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.weight.BottomDialogUtil;
import com.saimawzc.shipper.weight.utils.FileUtil;
import com.saimawzc.shipper.weight.utils.Md5Utils;
import com.saimawzc.shipper.weight.utils.MyComparator;
import com.saimawzc.shipper.weight.utils.SdCardUtil;
import com.saimawzc.shipper.weight.utils.api.OrderApi;
import com.saimawzc.shipper.weight.utils.api.auto.AuthApi;
import com.saimawzc.shipper.weight.utils.api.mine.MineApi;
import com.saimawzc.shipper.weight.utils.app.AppManager;
import com.saimawzc.shipper.weight.utils.dialog.DialogLoading;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import com.saimawzc.shipper.weight.utils.statusbar.StatusBarUtil;
import com.saimawzc.shipper.weight.utils.update.InstallUtils;
import com.werb.permissionschecker.PermissionChecker;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-03-21.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivity context;
    public Context mContext;
    private DialogLoading loading;
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,

    };
    public static String apkURLQ;
    public static final String[] PERMISSIONS_CAMERA = new String[]{
            Manifest.permission.CAMERA,
    };
    public static final String[] PERMISSIONSS_LOCATION = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    public PermissionChecker permissionChecker;
    public AuthApi authApi = Http.http.createApi(AuthApi.class);
    public MineApi mineApi = Http.http.createApi(MineApi.class);
    public OrderApi orderApi = Http.http.createApi(OrderApi.class);
    public LinearLayoutManager layoutManager;
    public UserInfoDto userInfoDto;
    public InstallUtils.DownloadCallBack downloadCallBack;
    private boolean mNeedOnBus;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // ????????????
        setContentView(getViewId());
        ButterKnife.bind(this);
        context = this;
        mContext = this;
        loading = new DialogLoading(this);
        AppManager.get().addActivity(this);
//        notificationUtil=new NotificationUtil();
//        notificationUtil.setNotification(this);
        //??????????????????
        initImmersionBar();
        onGetBundle(getIntent().getExtras());
        init();
        initListener();
        if (mNeedOnBus) {
            EventBus.getDefault().register(this);
        }
    }

    public void setURL(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            apkURLQ=getExternalFilesDir("Caches").getAbsolutePath()+ "/nxshiper/"+ BaseActivity.getCurrentTime("yyyy-MM-dd HH:mm")+"huozhu.apk";
        }
    }

    public void setStatusBar(boolean isUseBlackFontWithStatusBar, boolean isUseFullScreenMode, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isUseFullScreenMode) {//??????????????????????????????????????????????????????
                StatusBarUtil.transparencyBar(this);
            } else {
                StatusBarUtil.setStatusBarColor(this, color);
            }

            if (isUseBlackFontWithStatusBar) {//???????????????????????????????????????
                StatusBarUtil.setLightStatusBar(this, true, isUseFullScreenMode);
            }
        }
    }
    protected void setNeedOnBus(boolean needOnBus) {
        mNeedOnBus = needOnBus;
    }
    /**
     * ??????????????????
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.bg));
        }
        //???????????????????????????
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).
                statusBarDarkFont(true).
                statusBarColor(R.color.bg).
                navigationBarColor(R.color.bg).
                init();
    }

    public BaseActivity() {
    }

    protected abstract int getViewId();

    protected abstract void init();

    protected abstract void initListener();

    protected abstract void onGetBundle(Bundle bundle);

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    /**
     * ?????????????????????
     * **/
    public boolean isEmptyStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ?????????????????????
     * **/
    public boolean isEmptyStr(TextView view) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
    protected void initCamera(String type){
        if(mContext==null){
            return;
        }
        tempImage=System.currentTimeMillis()+"";
        Intent intent = new Intent(mContext, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(mContext,tempImage).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                type);
        startActivityForResult(intent, REQUEST_CODE_PIC);
    }
    /**
     * ?????????????????????
     * **/
    public boolean isEmptyStr(EditText view) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ?????????
     * **/
    public void showMessage(Object message) {
        if ("null".equals(message) || message == null) {
            message = "????????????";
        }
        showToast(message);
    }

    private void showToast(final Object message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (context != null && !isFinishing()) {
                    if (isNotificationEnabled(context)) {//???????????????
                        Toast toast = Toast.makeText(getApplicationContext(), message + "", Toast.LENGTH_LONG);
                        // ?????????????????????????????????????????????????????????
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        myToast(message + "");
                    }

                }
            }
        });
    }

    /**
     * ??????????????????
     * **/
    public String tempImage;
    public final int REQUEST_CODE_CAMERA = 1000;

    public void showCameraAction() {
        tempImage = SdCardUtil.getCacheTempImage(context);
        openCamera(tempImage, REQUEST_CODE_CAMERA);
    }

    public void openCamera(String path, int code) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //???????????????????????????????????????????????????Uri??????????????????
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse("file:///" + path));
        startActivityForResult(intent, code);
    }

    public void setToolbar(Toolbar toolbar, String title) {
        toolbar.setNavigationIcon(R.drawable.ico_menu_return);
        TextView titleTv = (TextView) toolbar.findViewById(R.id.title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        if (titleTv != null) {
            titleTv.setText(title);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public String getTime(Date date) {//???????????????????????????????????????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivityForResult
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /***
     * ????????????
     * */
    public static String getMd5Sign(String accessKey, Map<String, Object> params) {
        Set<String> set = params.keySet();
        String[] keys = new String[set.size()];
        set.toArray(keys);
        Arrays.sort(keys, new MyComparator());

        StringBuffer signVal = new StringBuffer();
        for (String key : keys) {
            String value = params.get(key) + "";
            if (Md5Utils.isEmpty(value))
                Log.i("TAG", key + "++" + value + "..");
            signVal.append(key).append(value);
        }
        Log.i("msg", signVal.toString() + accessKey);
        String sign = Md5Utils.getMD5Str(signVal + accessKey, "utf-8");
        Log.i("msg", sign);
        return sign;
    }


    /***
     * ????????????
     * ***/
    public boolean isLogin() {
        if (TextUtils.isEmpty(Hawk.get(PreferenceKey.ID) + "")) {
            return false;
        } else if ((Hawk.get(PreferenceKey.ID) + "").equalsIgnoreCase("null")) {
            return false;
        } else {
            return true;
        }
    }

    /****
     * ???????????????????????????String
     * **/
    public boolean changeOk(String key) {
        if (Hawk.get(key) instanceof String) {
            return false;
        } else {
            return true;
        }
    }

    /****
     * ???????????????????????????
     * */
    public static String getLastTime() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        return sf.format(c.getTime());

    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * ??????????????????
     */
    public void showLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && !loading.isShowing()) {
                    loading.show();
                }
            }
        });
    }

    public void showLoadingDialog(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && !loading.isShowing()) {
                    loading.setDialogLabel(txt);
                    loading.setCancelable(false);
                    loading.show();
                }
            }
        });
    }

    /**
     * ??????????????????
     */
    public void dismissLoadingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loading != null) {
                    loading.dismiss();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (loading != null) {
            loading.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissLoadingDialog();
        AppManager.get().removeActivity(this);
        if (processhandler != null) {
            processhandler.removeCallbacksAndMessages(null);
        }
        if (bottomDialogUtil != null) {
            bottomDialogUtil.dismiss();
        }
        super.onDestroy();
    }


    public static void hideKeyBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    /****
     * ??????????????????
     * ***/
    public static String getCurrentTime(String timeformat) {//"yyyy-MM-dd HH:mm"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeformat);// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date) + "";
    }


    protected  int getGapMinutes(String startDate, String endDate) {
        long start = 0;
        long end = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            start = df.parse(startDate).getTime();

            end = df.parse(endDate).getTime();

        } catch (Exception e) {
        }
        int minutes = (int) ((end - start) / (1000 * 60));
        return minutes;
    }
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {

        }
        return versionCode;

    }

    public static String getVersionName(Context context) {
        String versionCode = "";
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {

        }
        return versionCode;

    }

    /**
     * ?????????????????????
     * ***/
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * ?????????????????????????????????????????????
     * @param decryptString ??????
     * @param decryptKey ??????
     * @return ????????????
     */
    public static String decryptDES(String decryptString, String decryptKey) {

        try {
            //?????????Base64??????
            byte[] byteMi = Base64.decode(decryptString, 0);
            //?????????IvParameterSpec????????????????????????????????????
            IvParameterSpec zeroIv = new IvParameterSpec("wujiangk".getBytes());
            //?????????SecretKeySpec???????????????????????????????????????????????????SecretKeySpec,
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
            //???????????????
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            //??????????????????Cipher??????,????????????????????????????????????
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            //????????????????????????
            byte[] decryptedData = cipher.doFinal(byteMi);
            return new String(decryptedData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final int MIN_DELAY_TIME = 100;  // ??????????????????????????????1000ms
    private long lastClickTime;

    /****
     * ??????????????????
     * */
    public boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * ????????????Activity?????????
     * @param context
     * @return
     */
    public String getTopActivity(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(1);
        if (list != null) {
            ComponentName componentName = list.get(0).topActivity;
            //??????
            String packName = componentName.getPackageName();//packName=com.dr.dr_testappmanager
            //??????+?????????????????????????????????
            String className = componentName.getClassName();//className=com.dr.dr_testappmanager.MainActivity
            String nameStr = componentName.toString();//ComponentInfo{com.dr.dr_testappmanager/com.dr.dr_testappmanager.MainActivity}
            return className;
        }
        return null;

    }

    /**
     * ??????Activity??????Destroy
     * @param mActivity
     * @return true:?????????
     */
    public static boolean activityisDestroy(Activity mActivity) {
        if (mActivity == null ||
                mActivity.isFinishing() ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }


    /***
     * ??????Log
     * ****/
    public void showLog(String tag, String contect) {
        Log.e(tag, contect);
    }


    public void initPush() {
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                "kcKPgo9GQKZo7PvKOGjurEOTGPoPBtyr");
    }

    /**
     * ???license?????????????????????
     */
    public void initAccessToken() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                //hasGotToken = true;
                Log.e("msg", "??????????????????" + token);
                //  ?????????????????????????????????,???????????????onDestory???
                //  ????????????????????????????????? intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); ??????????????????????????????????????????
                CameraNativeHelper.init(BaseActivity.this, OCR.getInstance(BaseActivity.this).getLicense(),
                        new CameraNativeHelper.CameraNativeInitCallback() {
                            @Override
                            public void onError(int errorCode, Throwable e) {
                                String msg;
                                switch (errorCode) {
                                    case CameraView.NATIVE_SOLOAD_FAIL:
                                        msg = "??????so??????????????????apk?????????ui?????????so";
                                        break;
                                    case CameraView.NATIVE_AUTH_FAIL:
                                        msg = "????????????????????????token????????????";
                                        break;
                                    case CameraView.NATIVE_INIT_FAIL:
                                        msg = "??????????????????";
                                        break;
                                    default:
                                        msg = String.valueOf(errorCode);
                                }
                                Log.e("msg", "??????????????????????????????????????????????????? " + msg);
                            }
                        });

            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.e("msg", "licence????????????token??????" + error.getMessage());
                initAccessTokenWithAkSk();
            }
        }, getApplicationContext());
    }

    /**
     * ?????????ak???sk?????????
     */
    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                Log.e("msg", "??????????????????" + token);
                //  ?????????????????????????????????,???????????????onDestory???
                //  ????????????????????????????????? intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); ??????????????????????????????????????????
                CameraNativeHelper.init(BaseActivity.this, OCR.getInstance(BaseActivity.this).getLicense(),
                        new CameraNativeHelper.CameraNativeInitCallback() {
                            @Override
                            public void onError(int errorCode, Throwable e) {
                                String msg;
                                switch (errorCode) {
                                    case CameraView.NATIVE_SOLOAD_FAIL:
                                        msg = "??????so??????????????????apk?????????ui?????????so";
                                        break;
                                    case CameraView.NATIVE_AUTH_FAIL:
                                        msg = "????????????????????????token????????????";
                                        break;
                                    case CameraView.NATIVE_INIT_FAIL:
                                        msg = "??????????????????";
                                        break;
                                    default:
                                        msg = String.valueOf(errorCode);
                                }
                                Log.e("msg", "??????????????????????????????????????????????????? " + msg);
                            }
                        });

            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.e("msg", "AK???SK????????????token??????" + error.getMessage());
            }
        }, getApplicationContext(), "Om52FH0oEFxhASWTEaQSOUGf", "6RkLWRmCVOeaf5kxYn26oRLsDvd9Ry5R");
    }

    /**
     * ?????????????????????????????????
     *
     * @param fileLen ????????????
     * @param fileSize ????????????
     * @param fileUnit ??????????????????B,K,M,G???
     * @return
     */
    public static boolean checkFileSizeIsLimit(Long fileLen, int fileSize, String fileUnit) {
//        long len = file.length();
        double fileSizeCom = 0;
        if ("B".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen;
        } else if ("K".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / 1024;
        } else if ("M".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024 * 1024);
        } else if ("G".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024 * 1024 * 1024);
        }
        if (fileSizeCom > fileSize) {
            return false;
        }
        return true;

    }


    /****
     * ??????
     * **/
    public static File compress(final Context context, final File file) {

        File tempFile = null;
        if (file == null) {
            return null;
        }
        if (!checkFileSizeIsLimit(file.length(), 600, "K")) {//????????????1m
            tempFile = new CompressHelper.Builder(context)
                    .setMaxWidth(720)   //?????????????????????720
                    .setMaxHeight(960)  //?????????????????????960
                    .setQuality(80)     //?????????????????????80
                    .setCompressFormat(Bitmap.CompressFormat.PNG) //?????????????????????jpg??????
                    .setFileName(System.currentTimeMillis() + "") //?????????????????????
                    .build()
                    .compressToFile(file);
            return tempFile;
        } else {
            return file;
        }
    }

    /****
     * ????????????
     * **/
    public void cacheArae() {
        mineApi.getArea().enqueue(new CallBack<List<AreaDto>>() {
            @Override
            public void success(List<AreaDto> response) {
                Hawk.put(PreferenceKey.CITY_INFO, response);
            }

            @Override
            public void fail(String code, String message) {
            }
        });
    }

    /***
     * ????????????????????????String
     * **/
    public boolean ChangisString(String key) {
        if (Hawk.get(key) instanceof String) {
            return true;
        } else {
            return false;
        }
    }

    public void initpermissionChecker() {
        permissionChecker = new PermissionChecker(context);
        permissionChecker.setTitle(getString(R.string.check_info_title));
        permissionChecker.setMessage(getString(R.string.check_info_message));
    }

    public void stopSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * ?????????????????????
     *
     * @param packname
     * @return
     */
    public boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    // api_key ??????
    public void initWithApiKey() {

        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                "n4ldOiKYmVXf6403OAcSentL");


    }

    // ??????
    public void unBindForApp() {
        // Push?????????
        PushManager.stopWork(getApplicationContext());
    }


    public void callPhone(String phoneNum) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            context.showMessage("????????????????????????");

            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);

        startActivity(intent);
    }

    private String apkDownloadPath;
    /***
     * ????????????
     * **/

    private ProgressDialog progressDialog;
    private Handler processhandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what<=100){
                if(progressDialog!=null){
                    progressDialog.setProgress(msg.what);
                }
            }else {
                if(progressDialog!=null){
                    progressDialog.setMessage("?????????????????????????????????????????????????????????");
                    progressDialog.dismiss();
                    turnToMain();
                }
            }
        }
    };
    public void initCallBack() {
        downloadCallBack = new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
               // notificationInit();
                if(progressDialog==null){
                    progressDialog= new ProgressDialog(context);
                }
                progressDialog.setTitle("??????");
                progressDialog.setMessage("??????????????????????????????");
                // ??????ProgressDialog ????????????
                progressDialog.setIcon(R.drawable.ico_app);

                // ??????ProgressDialog ???????????????????????????
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// ?????????????????????
                // ??????ProgressDialog ?????????????????????????????????
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);// ???????????????Dialog???????????????Dialog?????????
                progressDialog.setMax(100);
                progressDialog.show();
            }

            @Override
            public void onComplete(String path) {

                apkDownloadPath = path;
                Hawk.put(PreferenceKey.OLD_UPDATE_TIME,apkDownloadPath);
                if(progressDialog!=null){
                    progressDialog.dismiss();
                }
                //??????????????????????????????
                InstallUtils.checkInstallPermission(context, new InstallUtils.InstallPermissionCallBack() {
                    @Override
                    public void onGranted() {
                        //?????????APK
                        installApk(apkDownloadPath);
                    }

                    @Override
                    public void onDenied() {
                        //????????????????????????
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setTitle("????????????")
                                .setMessage("????????????????????????APK????????????????????????")
                                .setNegativeButton("??????", null)
                                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //??????????????????
                                        InstallUtils.openInstallPermissionSetting(context, new InstallUtils.InstallPermissionCallBack() {
                                            @Override
                                            public void onGranted() {
                                                //?????????APK
                                                installApk(apkDownloadPath);
                                            }

                                            @Override
                                            public void onDenied() {
                                                //????????????????????????
                                                AlertDialog alertDialog = new AlertDialog.Builder(context)
                                                        .setTitle("????????????")
                                                        .setMessage("????????????????????????APK????????????????????????")
                                                        .setNegativeButton("??????", null)
                                                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                //??????????????????
                                                                InstallUtils.openInstallPermissionSetting(context, new InstallUtils.InstallPermissionCallBack() {
                                                                    @Override
                                                                    public void onGranted() {
                                                                        //?????????APK
                                                                        installApk(apkDownloadPath);
                                                                    }

                                                                    @Override
                                                                    public void onDenied() {
                                                                        //????????????????????????
                                                                        Toast.makeText(context, "???????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            }
                                                        })
                                                        .create();
                                                alertDialog.show();
                                            }
                                        });
                                    }
                                })
                                .create();
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onLoading(long total, long current) {
                //?????????????????????onLoading ????????????progress?????????+1?????????????????????
                int progress = (int) (current * 100 / total);
                Log.e("msg","??????"+progress);
                Message message=new Message();
                message.what=progress;
                processhandler.sendMessage(message);
            }

            @Override
            public void onFail(Exception e) {
                showMessage("?????????????????????????????????????????????????????????");
                if(progressDialog!=null){
                    Message message=new Message();
                    message.what=1001;
                    processhandler.sendMessage(message);
                }
            }
            @Override
            public void cancle() {
            }
        };
    }

    protected void installApk(String path) {
        Hawk.put(PreferenceKey.OLD_UPDATE_TIME,"");
        InstallUtils.installAPK(context, path, new InstallUtils.InstallCallBack() {
            @Override
            public void onSuccess() {
                //onSuccess???????????????????????????????????????
                //??????????????????????????????????????????????????????????????????????????????????????????
                Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFail(Exception e) {
                showMessage("????????????:" + e.toString());
                Log.e("msg","????????????:" + e.toString());
            }
        });
    }

    /**
     * ????????????????????????????????????
     */
    public static boolean isNotificationEnabled(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).areNotificationsEnabled();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;
            try {
                Class<?> appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
                int value = (Integer) opPostNotificationValue.get(Integer.class);
                return (Integer) checkOpNoThrowMethod.invoke(appOps, value, uid, pkg) == 0;
            } catch (NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException | RuntimeException | ClassNotFoundException ignored) {
                return true;
            }
        } else {
            return true;
        }
    }
     BottomDialogUtil bottomDialogUtil;
    private void myToast(String message) {
        if(bottomDialogUtil==null){
            bottomDialogUtil = new BottomDialogUtil.Builder()
                    .setContext(context) //?????? context
                    .setContentView(R.layout.toast) //??????????????????
                    .setOutSideCancel(false) //????????????????????????
                    .builder()
                    .show();
        }
        TextView tvtoast= (TextView) bottomDialogUtil.getItemView(R.id.tvtoast);
        tvtoast.setText(message);

        new Handler().postDelayed(new Runnable(){
            public void run() {

                try {
                    //??????dialog
                    if(bottomDialogUtil!=null){
                        bottomDialogUtil.dismiss();
                        bottomDialogUtil=null;
                    }
                }catch (Exception e){

                }finally {
                    if(bottomDialogUtil!=null){
                        bottomDialogUtil.dismiss();
                    }
                    bottomDialogUtil=null;
                }

            }
        }, 1000);   //5???

    }

    private void turnToMain() {
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.ID,""))){//????????????
            if(userInfoDto==null){
                if(userInfoDto.getRole()==1){//??????
                    Log.e("msg","????????????");
                    readyGo(MainActivity.class);
                }else if(userInfoDto.getRole()==4){//?????????
                    readyGo(ConsigneeMainActivity.class);
                }
            }else {
                readyGo(LoginActivity.class);
            }

        }else {
            Log.e("msg","????????????");
            readyGo(LoginActivity.class);
        }
    }
}


