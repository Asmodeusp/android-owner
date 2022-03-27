package com.saimawzc.shipper.modle.login;

import android.app.Activity;
import android.util.Log;

import com.saimawzc.shipper.base.BaseApplication;
import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.login.LoginView;
import com.saimawzc.shipper.weight.utils.SPUtils;
import com.saimawzc.shipper.weight.utils.StringUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/31.
 * 登录实现
 */

public class LoginModelImple extends BasEModeImple implements LoginModel {




    @Override
    public void login(final LoginView view, final BaseListener listener, final int role,final boolean ischeck) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("loginDevice","");
            jsonObject.put("loginSource","1");//安卓

            jsonObject.put("password", StringUtil.Md5(view.getPass()));
            jsonObject.put("role",role);
            jsonObject.put("userAccount",view.getPhone());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        authApi.login(body).enqueue(new CallBack<UserInfoDto>() {
            @Override
            public void success(UserInfoDto response) {
                view.dissLoading();
                Hawk.put(PreferenceKey.isChange_or_login,"true");
                Hawk.put(PreferenceKey.USER_INFO,response);
                Hawk.put(PreferenceKey.ID,response.getToken());
                Hawk.put(PreferenceKey.IS_TUOYUN,response.getTrustFlag()+"");
                if(ischeck){
                    Hawk.put(PreferenceKey.USER_ACCOUNT,view.getPhone());
                    Hawk.put(PreferenceKey.PASS_WORD,view.getPass());
                    Hawk.put(PreferenceKey.ISREMEMBER_PASS,"1");
                }else {
                    Hawk.put(PreferenceKey.USER_ACCOUNT,"");
                    Hawk.put(PreferenceKey.PASS_WORD,"");
                    Hawk.put(PreferenceKey.ISREMEMBER_PASS,"");
                }
                ((Activity)view.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Http.initHttp(view.getContext());
                    }
                });
                listener.successful(role);
                String  channelId = (String) SPUtils.get(BaseApplication.getInstance(), "channelId", "");
                submitPushInfo(channelId);
            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });

    }
    private void submitPushInfo(String channelId){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("loginSource","1");//安卓
            jsonObject.put("channelId",channelId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        authApi.updatePushInfo(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {

            }
            @Override
            public void fail(String code, String message) {

            }
        });
    }

}
