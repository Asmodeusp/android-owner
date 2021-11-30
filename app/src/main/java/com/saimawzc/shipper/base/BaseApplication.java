package com.saimawzc.shipper.base;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.LatestPointRequest;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.BaseRequest;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.baidu.trace.model.ProcessOption;
import com.bumptech.glide.Glide;
import com.saimawzc.shipper.BuildConfig;
import com.saimawzc.shipper.dto.login.AreaDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.hawk.HawkBuilder;
import com.saimawzc.shipper.weight.utils.hawk.LogLevel;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.loadimg.GlideImageLoader;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.shipper.constants.AppConfig;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import com.tencent.bugly.crashreport.CrashReport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import static com.baidu.mapapi.NetworkUtil.isNetworkAvailable;
/**
 * Author : nsj
 * Date   : 2020/7/27
 */
public class BaseApplication extends Application {

    public static BaseApplication instance;

    public static ArrayList<AreaDto> options1Items;

    /****
     *百度鹰眼
     * ***/
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    /**
     * 轨迹客户端
     */
    public LBSTraceClient mClient = null;
    /**
     * 轨迹服务
     */
    public Trace mTrace = null;
    public SharedPreferences trackConf = null;
    public LocRequest locRequest = null;
    /**
     * 轨迹服务ID
     */
    public long serviceId = 223521;
    /**
     * Entity标识
     */
    public String entityName = "车辆标识";
    public boolean isRegisterReceiver = false;

    /**
     * 服务是否开启标识
     */
    public boolean isTraceStarted = false;

    /**
     * 采集是否开启标识
     */
    public boolean isGatherStarted = false;

    public static int screenWidth = 0;

    public static int screenHeight = 0;
    UserInfoDto userInfoDto;
    /****
     *
     * ***/

    @Override
    public void onCreate() {
        super.onCreate();
        /**严苛模式主要检测两大问题，一个是线程策略，即TreadPolicy，另一个是VM策略，即VmPolicy。*/
        if (AppConfig.IS_DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
        //初始化Hawk
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();
        userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
        if(userInfoDto!=null){
            entityName=userInfoDto.getId();
        }
        //接口请求初始化
        Http.initHttp(getApplicationContext());
        ImageLoadUtil.getInstance();
        instance = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                options1Items= Hawk.get(PreferenceKey.CITY_INFO);
            }
        }).start();
        /***  初始化galallfinal ***/
       initGalalleryFinal();

        if (BuildConfig.DEBUG) {
            initDebugTool();
        } else {
            /**
             * bugly仅release版本才检测更新和上传错误日志
             * **/
            CrashReport.UserStrategy strategy=new CrashReport.UserStrategy(getApplicationContext());
            strategy.setAppChannel("myChannel");
            strategy.setAppVersion(BaseActivity.getVersionName(getApplicationContext()));
            strategy.setAppPackageName("com.saimawzc.shipper");
            CrashReport.initCrashReport(getApplicationContext(), "d43a8d26a1", true,strategy);
        }
        try{
            SDKInitializer.initialize(this);
            mClient = new LBSTraceClient(this);
            mTrace = new Trace(serviceId, entityName);
            trackConf = getSharedPreferences("track_conf", MODE_PRIVATE);
            locRequest = new LocRequest(serviceId);
            mClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
                @Override
                public Map<String, String> onTrackAttributeCallback() {
                    Map<String, String> map = new HashMap<>();
                    map.put("key1", "value1");
                    map.put("key2", "value2");
                    return map;
                }

                @Override
                public Map<String, String> onTrackAttributeCallback(long locTime) {
                    System.out.println("onTrackAttributeCallback, locTime : " + locTime);
                    Map<String, String> map = new HashMap<>();
                    map.put("key1", "value1");
                    map.put("key2", "value2");
                    return map;
                }
            });

        }catch (Exception e){

        }

    }

    public static BaseApplication getInstance() {
        return instance;
    }

    private void initDebugTool() {
        if (!AppConfig.IS_DEBUG) {
            return;
        }
        /**crash异常捕获*/
        if (AppConfig.IS_DEBUG) {
           // CustomActivityOnCrash.install(this);
        }
        /**初始化LeakCanary */

    }


    private void initGalalleryFinal() {
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarTextColor(Color.parseColor("#ffffff")).//标题栏文本字体颜色
                setTitleBarBgColor(Color.parseColor("#1a1819")).//标题栏背景颜色
                setCheckSelectedColor(Color.parseColor("#3232CD")).//选择框选种颜色
                setFabNornalColor(Color.parseColor("#ff4545")).//设置floating按钮nornal状态颜色
                setFabPressedColor(Color.parseColor("#C0D9D9")).build();//设置floating按钮pressed状态颜色

        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)//开启相机功能
                .setEnableEdit(true)//开启编辑功能
                .setEnableCrop(true)//开启裁剪功能
                .setCropHeight(100)
                .setCropHeight(100)
                .setCropSquare(true)//裁剪正方形
                .setRotateReplaceSource(true)
                .setCropReplaceSource(true)//裁剪覆盖原图
                .setRotateReplaceSource(true)//选择时是否替换原图片
                .setForceCropEdit(true)
                .setForceCrop(true)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
                .setEnablePreview(true)
                .setMutiSelectMaxSize(8)
                .build();

        //配置imageloader  .setDebug(BuildConfig.DEBUG) 设置Debug开关
        CoreConfig mCoreConfig = new CoreConfig.Builder(getApplicationContext(), new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(mCoreConfig);
    }
    /***
     * 鹰眼
     * ***/
    /**
     * 获取当前位置
     */
    public void getCurrentLocation(OnEntityListener entityListener, OnTrackListener trackListener) {
        // 网络连接正常，开启服务及采集，则查询纠偏后实时位置；否则进行实时定位

        if(trackConf!=null){
            if (isNetworkAvailable(this)
                    && trackConf.contains("is_trace_started")
                    && trackConf.contains("is_gather_started")
                    && trackConf.getBoolean("is_trace_started", false)
                    && trackConf.getBoolean("is_gather_started", false)) {
                if(mClient==null||trackListener==null){
                   return;
                }
                LatestPointRequest request = new LatestPointRequest(getTag(), serviceId, entityName);
                ProcessOption processOption = new ProcessOption();
                processOption.setNeedDenoise(true);
                processOption.setRadiusThreshold(100);
                request.setProcessOption(processOption);
                mClient.queryLatestPoint(request, trackListener);
            } else {
                if(mClient==null||entityListener==null){
                    return;
                }
                mClient.queryRealTimeLoc(locRequest, entityListener);
            }
        }

    }
    /**
     * 初始化请求公共参数
     *
     * @param request
     */
    public void initRequest(BaseRequest request) {
        request.setTag(getTag());
        request.setServiceId(serviceId);
    }
    /**
     * 获取请求标识
     *
     * @return
     */
    public int getTag() {
        return mSequenceGenerator.incrementAndGet();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    public   void goLoginActivity(){
        Intent intent=new Intent(BaseApplication.getInstance(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}
