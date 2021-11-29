package com.saimawzc.shipper.modle.login;

import android.app.Activity;
import android.util.Log;
import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.view.login.ResisterView;
import com.saimawzc.shipper.weight.utils.StringUtil;
import com.saimawzc.shipper.weight.utils.api.auto.AuthApi;
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
 */

public class ResisterModelImple implements  ResisterModel{

    AuthApi authApi= Http.http.createApi(AuthApi.class);

    @Override
    public void getCode(String phone, final BaseListener listener) {

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("mobile",phone);
            jsonObject.put("type","register");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        authApi.getCode(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                listener.successful(55);
            }

            @Override
            public void fail(String code, String message) {
             listener.onFail(message);
            }
        });

    }

    @Override
    public void login(final ResisterView view, final String pass,
                      final BaseListener listener,String phone) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("loginDevice","");
            jsonObject.put("loginSource","1");//安卓
            jsonObject.put("password", StringUtil.Md5(pass));
            if(view.resiserType().contains("货主")){
                jsonObject.put("role",1);
            }
            if(view.resiserType().contains("收货人")){
                jsonObject.put("role",4);
            }
            jsonObject.put("userAccount",phone);


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
                Hawk.put(PreferenceKey.USER_INFO,response);
                Hawk.put(PreferenceKey.ID,response.getToken());
                Hawk.put(PreferenceKey.IS_TUOYUN,response.getTrustFlag()+"");
                int role=response.getRole();
                listener.successful(role);
            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });
    }

    @Override
    public void resiser(final ResisterView view, final BaseListener listener) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("createSource","1");//1安卓

            jsonObject.put("password",StringUtil.Md5(view.getPassWord()));

            if(view.resiserType().contains("货主")){
                jsonObject.put("role",1);
            }
            if(view.resiserType().contains("收货人")){
                jsonObject.put("role",4);
            }
            jsonObject.put("userAccount",view.getPhone().toString());
            jsonObject.put("verificationCode",view.getYzm());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        authApi.resister(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.Toast("注册成功");
                listener.successful(100);
                view.dissLoading();
            }
            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });
    }
}
