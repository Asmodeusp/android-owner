package com.saimawzc.shipper.modle.login;

import android.app.Activity;
import android.util.Log;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.login.VCodeLoginView;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/30.
 * 验证码登录实现类
 */
public class VCodeLoginModelImple extends BasEModeImple implements  VCodeLoginModel{

    @Override
    public void getCode(VCodeLoginView view, final BaseListener listener) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("mobile",view.getPhone());
            jsonObject.put("type","login");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        authApi.getCode(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                listener.successful(3);
            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
            }
        });

    }

    @Override
    public void login(final VCodeLoginView view, final BaseListener listener, final int role) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("loginDevice","");
            jsonObject.put("loginSource","1");//安卓
            jsonObject.put("role",role);
            jsonObject.put("userAccount",view.getPhone());
            jsonObject.put("verificationCode",view.getCode());//安卓

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        authApi.loginByCode(body).enqueue(new CallBack<UserInfoDto>() {
            @Override
            public void success(UserInfoDto response) {
                listener.successful(role);

                Hawk.put(PreferenceKey.USER_INFO,response);
                Hawk.put(PreferenceKey.ID,response.getToken());
                Hawk.put(PreferenceKey.IS_TUOYUN,response.getTrustFlag()+"");
                ((Activity)view.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Http.initHttp(view.getContext());
                    }
                });
            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
            }
        });
    }
}
