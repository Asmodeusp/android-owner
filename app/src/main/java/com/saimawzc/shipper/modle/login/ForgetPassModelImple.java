package com.saimawzc.shipper.modle.login;

import android.util.Log;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.login.ForgetPassView;
import com.saimawzc.shipper.weight.utils.StringUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/31.
 * 忘记密码
 */

public class ForgetPassModelImple extends BasEModeImple implements ForgetPassModel  {


    @Override
    public void getCode(ForgetPassView view, final BaseListener listener) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("mobile",view.getPhone());
            jsonObject.put("type","changePwd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        authApi.getCode(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                listener.successful(1);
            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
            }
        });
    }

    @Override
    public void updatePass(final ForgetPassView view, final BaseListener listener) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("password", StringUtil.Md5(view.getPass()));
            jsonObject.put("userAccount",view.getPhone());
            jsonObject.put("verificationCode",view.getCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        authApi.forgetPass(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                Hawk.put(PreferenceKey.PASS_WORD,view.getPass());
                listener.successful(2);

            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
            }
        });
    }
}
