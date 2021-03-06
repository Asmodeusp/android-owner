package com.saimawzc.shipper.ui.push;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.android.pushservice.PushMessageReceiver;
import com.saimawzc.shipper.base.BaseApplication;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.ui.my.PersonCenterActivity;
import com.saimawzc.shipper.weight.utils.SPUtils;
import com.saimawzc.shipper.weight.utils.api.auto.AuthApi;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/*
 * Push消息处理receiver。请编写您需要的回调函数， 一般来说： onBind是必须的，用来处理startWork返回值；
 * onMessage用来接收透传消息； onSetTags、onDelTags、onListTags是tag相关操作的回调；
 * onNotificationClicked在通知被点击时回调； onUnbind是stopWork接口的返回值回调
 * 返回值中的errorCode，解释如下：
 * 0 - Success
 * 10001 - Network Problem
 * 10101 - Integrate Check Error
 * 30600 - Internal Server Error
 * 30601 - Method Not Allowed
 * 30602 - Request Params Not Valid
 * 30603 - Authentication Failed
 * 30604 - Quota Use Up Payment Required
 * 30605 - Data Required Not Found
 * 30606 - Request Time Expires Timeout
 * 30607 - Channel Token Timeout
 * 30608 - Bind Relation Not Found
 * 30609 - Bind Number Too Many
 * 当您遇到以上返回错误时，如果解释不了您的问题，请用同一请求的返回值requestId和errorCode联系我们追查问题。
 */

public class MyPushMessageReceiver extends PushMessageReceiver {
    public AuthApi authApi= Http.http.createApi(AuthApi.class);

    public static final String TAG = "pushmsg";
    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
        Log.d(TAG, responseString);
        if (errorCode == 0) {
            // 绑定成功
            Log.d(TAG, "绑定成功");
            SPUtils.put(BaseApplication.getInstance(),"channelId",channelId);
//            submitPushInfo(channelId);
        }
    }

    /**
     * 接收透传消息的函数。
     * @param context             上下文
     * @param message             推送的消息
     * @param customContentString 自定义内容,为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String message,
                          String customContentString) {
        String messageString = "透传消息 onMessage=\"" + message
                + "\" customContentString=" + customContentString;
        Log.d(TAG, messageString);

        if (!TextUtils.isEmpty(customContentString)) {
            // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
            //updateContent(context, customContentString);
        }
    }

    /**
     * 接收通知到达的函数。
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationArrived(Context context, String title,
                                      String description, String customContentString) {
        String notifyString = "通知到达 onNotificationArrived  title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString;
        Log.d(TAG, notifyString);
        if(!TextUtils.isEmpty(customContentString)){
            //updateContent(context, customContentString);
        }

    }
    /**
     * 接收通知点击的函数。
     *
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationClicked(Context context, String title,
                                      String description, String customContentString) {
        String notifyString = "通知点击 onNotificationClicked title=\"" + title + "\" description=\""
                + description + "\" customContent=" + customContentString;
        Log.d(TAG, notifyString);

        // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
        if (!TextUtils.isEmpty(customContentString)) {
            // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
            updateContent(context, customContentString);
        }
    }
    /**
     * setTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
     * @param successTags 设置成功的tag
     * @param failTags    设置失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
    @Override
    public void onSetTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onSetTags errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.d(TAG, responseString);
        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        updateContent(context, responseString);
    }

    /**
     * delTags() 的回调函数。
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
     * @param successTags 成功删除的tag
     * @param failTags    删除失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
    @Override
    public void onDelTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onDelTags errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.d(TAG, responseString);
        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        ///updateContent(context, responseString);
    }

    /**
     * listTags() 的回调函数。
     * @param context   上下文
     * @param errorCode 错误码。0表示列举tag成功；非0表示失败。
     * @param tags      当前应用设置的所有tag。
     * @param requestId 分配给对云推送的请求的id
     */
    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
                           String requestId) {
        String responseString = "onListTags errorCode=" + errorCode + " tags="
                + tags;
        Log.d(TAG, responseString);
        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑

       // updateContent(context, responseString);
    }

    /**
     * PushManager.stopWork() 的回调函数。
     * @param context   上下文
     * @param errorCode 错误码。0表示从云推送解绑定成功；非0表示失败。
     * @param requestId 分配给对云推送的请求的id
     */
    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        String responseString = "onUnbind errorCode=" + errorCode
                + " requestId = " + requestId;
        Log.d(TAG, responseString);

        if (errorCode == 0) {
            // 解绑定成功
            Log.d(TAG, "解绑成功");

        }
    }

    private void updateContent(Context context, String content) {

        if(!TextUtils.isEmpty(content)){
            UserInfoDto userInfoDto=Hawk.get(PreferenceKey.USER_INFO);
            try {
                JSONObject object=new JSONObject(content);
                String roleType =object.getString("roleType");//1货主  4收货人
                String type=object.getString("type");
                if(!TextUtils.isEmpty(roleType)&&!TextUtils.isEmpty(type)){

                    if(userInfoDto==null){
                        Bundle bundle=new Bundle();
                        startUserActivity(context, LoginActivity.class,bundle);
                        return;
                    }
                    if(!(userInfoDto.getRole()+"").equals(roleType)){
                        //Bundle bundle=new Bundle();
                      //  startUserActivity(context, LoginActivity.class,bundle);
                        return;
                    }
                    if(roleType.equals("1")){//货主
                        if(type.equals("6")){//货物到达 跳转货主端派车列表
                            Bundle bundle=new Bundle();
                            bundle.putInt("from",3);
                            startUserActivity(context, MainActivity.class,bundle);
                        }else if(type.equals("7")){//换车 跳转货主端派车列表
                            Bundle bundle=new Bundle();
                            bundle.putInt("from",3);
                            startUserActivity(context, MainActivity.class,bundle);
                        }else if(type.equals("8")){//换司机 跳转货主端派车列表
                            Bundle bundle=new Bundle();
                            bundle.putInt("from",3);
                            startUserActivity(context, MainActivity.class,bundle);
                        }

                    }else if(roleType.equals("4")){//收货人

                    }

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


//
    }

//    private void submitPushInfo(String channelId){
//        JSONObject jsonObject=new JSONObject();
//        try {
//            jsonObject.put("loginSource","1");//安卓
//            jsonObject.put("channelId",channelId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.e("msg",jsonObject.toString());
//        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
//        authApi.updatePushInfo(body).enqueue(new CallBack<EmptyDto>() {
//            @Override
//            public void success(EmptyDto response) {
//
//            }
//            @Override
//            public void fail(String code, String message) {
//
//            }
//        });
//    }
    /**
     * 启动新的Activity
     * @param context 当前Activity
     * @param cls     要启动的Activity的类
     */
    public  void startUserActivity(Context context , Class cls,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(context, cls);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }
}
