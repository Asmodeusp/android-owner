package com.saimawzc.shipper.weight.utils;

import android.app.Notification;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.fence.FenceAlarmPushInfo;
import com.baidu.trace.api.fence.MonitoredAction;
import com.baidu.trace.api.track.LatestPoint;
import com.baidu.trace.api.track.LatestPointResponse;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.StatusCodes;
import com.baidu.trace.model.TraceLocation;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.BaseApplication;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.ui.baidu.utils.CommonUtil;
import com.saimawzc.shipper.ui.baidu.utils.Constants;
import com.saimawzc.shipper.ui.baidu.utils.CurrentLocation;
import com.saimawzc.shipper.ui.baidu.utils.MapUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/***
 * 百度鹰眼工具
 * ***/
public class TraceUtils {
    private BaseApplication trackApp;
    /**
     * 地图工具
     */
    private MapUtil mapUtil = null;
    public BaseActivity context;
    private UserInfoDto userInfoDto;
    private String entityName;

    public TraceUtils(BaseApplication application,BaseActivity context){
        this.trackApp=application;
        this.context=context;
        mapUtil = MapUtil.getInstance();
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        entityName=userInfoDto.getId();
        Log.e("msg","当前标识"+entityName);
        if(trackApp.mClient==null){
            initView();
        }else {
            initTrace();
        }
    }

    /**
     * 轨迹服务监听器
     */
    private OnTraceListener traceListener = null;

    /**
     * 轨迹监听器(用于接收纠偏后实时位置回调)
     */
    private OnTrackListener trackListener = null;

    /**
     * Entity监听器(用于接收实时定位回调)
     */
    private OnEntityListener entityListener = null;

    private boolean isRealTimeRunning = true;
    /**
     * 打包周期
     */
    public int packInterval = Constants.DEFAULT_PACK_INTERVAL;

    private RealTimeLocRunnable realTimeLocRunnable = null;

    public  void  stopService(){
        Log.e("msg","当前服务已经开启,停止服务");
        trackApp.mClient.stopTrace(trackApp.mTrace, traceListener);
        stopRealTimeLoc();
    }
    public void startSercive(){
        if (trackApp.isTraceStarted) {
            Log.e("msg","当前服务已经开启");
        } else {
            Log.e("msg","开启服务");
            trackApp.mClient.startTrace(trackApp.mTrace, traceListener);


            if (Constants.DEFAULT_PACK_INTERVAL != packInterval) {
                stopRealTimeLoc();
                startRealTimeLoc(packInterval);
            }
        }
    }

    /****
     * 停止采集
     * */
    public void stopRealTimeLoc() {
        isRealTimeRunning = false;
        if (null != realTimeHandler && null != realTimeLocRunnable) {
            realTimeHandler.removeCallbacks(realTimeLocRunnable);
        }
        trackApp.mClient.stopRealTimeLoc();
    }

    public void startRealTimeLoc(int interval) {
        isRealTimeRunning = true;
        realTimeLocRunnable = new RealTimeLocRunnable(interval);
        realTimeHandler.post(realTimeLocRunnable);
    }

    /**
     * 实时定位任务
     *
     * @author baidu
     */
    class RealTimeLocRunnable implements Runnable {

        private int interval = 0;

        public RealTimeLocRunnable(int interval) {
            this.interval = interval;
        }

        @Override
        public void run() {
            if (isRealTimeRunning) {
                trackApp.getCurrentLocation(entityListener, trackListener);
                realTimeHandler.postDelayed(this, interval * 1000);
            }
        }
    }

    /**
     * 实时定位任务
     */
    private RealTimeHandler realTimeHandler = new RealTimeHandler();

    static class RealTimeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    /***
     *初始化轨迹服务监听器
     * **/
    public void initTrace(){

        trackListener = new OnTrackListener() {

            @Override
            public void onLatestPointCallback(LatestPointResponse response) {
                if (StatusCodes.SUCCESS != response.getStatus()) {
                    return;
                }

                LatestPoint point = response.getLatestPoint();
                if (null == point || CommonUtil.isZeroPoint(point.getLocation().getLatitude(), point.getLocation()
                        .getLongitude())) {
                    return;
                }

                LatLng currentLatLng = mapUtil.convertTrace2Map(point.getLocation());
                if (null == currentLatLng) {
                    return;
                }
                CurrentLocation.locTime = point.getLocTime();
                CurrentLocation.latitude = currentLatLng.latitude;
                CurrentLocation.longitude = currentLatLng.longitude;

                if (null != mapUtil) {
                    mapUtil.updateStatus(currentLatLng, true);
                }
            }
        };


        entityListener = new OnEntityListener() {

            @Override
            public void onReceiveLocation(TraceLocation location) {

                if (StatusCodes.SUCCESS != location.getStatus() || CommonUtil.isZeroPoint(location.getLatitude(),
                        location.getLongitude())) {
                    return;
                }
                LatLng currentLatLng = mapUtil.convertTraceLocation2Map(location);
                if (null == currentLatLng) {
                    return;
                }
                CurrentLocation.locTime = CommonUtil.toTimeStamp(location.getTime());
                CurrentLocation.latitude = currentLatLng.latitude;
                CurrentLocation.longitude = currentLatLng.longitude;

                if (null != mapUtil) {
                    mapUtil.updateStatus(currentLatLng, true);
                }
            }

        };

        traceListener = new OnTraceListener() {

            /**
             * 绑定服务回调接口
             * @param errorNo  状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功 </pre>
             *                <pre>1：失败</pre>
             */
            @Override
            public void onBindServiceCallback(int errorNo, String message) {
                Log.e("msg","鹰眼服务开启结果"+errorNo+message);
            }
            /**
             * 开启服务回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功 </pre>
             *                <pre>10000：请求发送失败</pre>
             *                <pre>10001：服务开启失败</pre>
             *                <pre>10002：参数错误</pre>
             *                <pre>10003：网络连接失败</pre>
             *                <pre>10004：网络未开启</pre>
             *                <pre>10005：服务正在开启</pre>
             *                <pre>10006：服务已开启</pre>
             */
            @Override
            public void onStartTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo
                        || StatusCodes.START_TRACE_NETWORK_CONNECT_FAILED <= errorNo) {
                    // 开启采集
                    trackApp.mClient.startGather(traceListener);
                    trackApp.isTraceStarted = true;
                    if(trackApp.trackConf!=null){
                        Log.e("msg","SUCCESS  ~trackConf!=null");
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.putBoolean("is_trace_started", true);
                        editor.commit();
                    }else {
                        Log.e("msg","SUCCESS  ~trackConf==null");
                        trackApp.trackConf=context.getSharedPreferences("track_conf", MODE_PRIVATE);
                    }

                }
            }
            /**
             * 停止服务回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>11000：请求发送失败</pre>
             *                <pre>11001：服务停止失败</pre>
             *                <pre>11002：服务未开启</pre>
             *                <pre>11003：服务正在停止</pre>
             */
            @Override
            public void onStopTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.CACHE_TRACK_NOT_UPLOAD == errorNo) {
                    trackApp.isTraceStarted = false;
                    trackApp.isGatherStarted = false;
                    // 停止成功后，直接移除is_trace_started记录（便于区分用户没有停止服务，直接杀死进程的情况）
                    if(trackApp.trackConf!=null){
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.remove("is_trace_started");
                        editor.remove("is_gather_started");
                        editor.commit();
                    }else {
                        trackApp.trackConf=context.getSharedPreferences("track_conf", MODE_PRIVATE);
                    }


                    //unregisterPowerReceiver();
                }
            }
            /**
             * 开启采集回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>12000：请求发送失败</pre>
             *                <pre>12001：采集开启失败</pre>
             *                <pre>12002：服务未开启</pre>
             */
            @Override
            public void onStartGatherCallback(int errorNo, String message) {

                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STARTED == errorNo) {
                    trackApp.isGatherStarted = true;
                    if(trackApp.trackConf==null){
                        Log.e("msg","开始采集  ~trackConf==null");
                        trackApp.trackConf=context.getSharedPreferences("track_conf", MODE_PRIVATE);
                    }else {
                        Log.e("msg","开始采集  ~trackConf!=null");
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.putBoolean("is_gather_started", true);
                        editor.commit();
                    }

                }else {
                    Log.e("msg","StatusCodes.GATHER_STARTED"+errorNo);
                }
            }
            /**
             * 停止采集回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>13000：请求发送失败</pre>
             *                <pre>13001：采集停止失败</pre>
             *                <pre>13002：服务未开启</pre>
             */
            @Override
            public void onStopGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STOPPED == errorNo) {

                    if(trackApp.trackConf==null){
                        trackApp.trackConf=context.getSharedPreferences("track_conf", MODE_PRIVATE);
                    }else {
                        trackApp.isGatherStarted = false;
                        SharedPreferences.Editor editor = trackApp.trackConf.edit();
                        editor.remove("is_gather_started");
                        editor.commit();
                    }

                }
            }
            /**
             * 推送消息回调接口
             *
             * @param messageType 状态码
             * @param pushMessage 消息
             *                  <p>
             *                  <pre>0x01：配置下发</pre>
             *                  <pre>0x02：语音消息</pre>
             *                  <pre>0x03：服务端围栏报警消息</pre>
             *                  <pre>0x04：本地围栏报警消息</pre>
             *                  <pre>0x05~0x40：系统预留</pre>
             *                  <pre>0x41~0xFF：开发者自定义</pre>
             */
            @Override
            public void onPushCallback(byte messageType, PushMessage pushMessage) {
                if (messageType < 0x03 || messageType > 0x04) {
                    return;
                }
                FenceAlarmPushInfo alarmPushInfo = pushMessage.getFenceAlarmPushInfo();
                if (null == alarmPushInfo) {

                    return;
                }
                StringBuffer alarmInfo = new StringBuffer();
                alarmInfo.append("您于")
                        .append(CommonUtil.getHMS(alarmPushInfo.getCurrentPoint().getLocTime() * 1000))
                        .append(alarmPushInfo.getMonitoredAction() == MonitoredAction.enter ? "进入" : "离开")
                        .append(messageType == 0x03 ? "云端" : "本地")
                        .append("围栏：").append(alarmPushInfo.getFenceName());

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    Notification notification = new Notification.Builder(trackApp)
                            .setContentTitle("")
                            .setContentText(alarmInfo.toString())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setWhen(System.currentTimeMillis()).build();
                    //notificationManager.notify(notifyId++, notification);
                }
            }
            @Override
            public void onInitBOSCallback(int errorNo, String message) {

            }
        };
    }

    public void initView(){
        trackApp.mClient = new LBSTraceClient(context);
        trackApp.mTrace = new Trace(trackApp.serviceId, entityName);
        trackApp.locRequest = new LocRequest(trackApp.serviceId);
        // 定位周期(单位:秒)
        int gatherInterval = 5;
        // 打包回传周期(单位:秒)
        int packInterval = 10;
        // 设置定位和打包周期
        trackApp.mClient.setInterval(gatherInterval, packInterval);

        trackApp.mClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
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
        initTrace();
    }


}
