package com.saimawzc.shipper.weight.utils.http;

import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;


/**
 * Created by zhangdm on 2016/4/20.
 */
public class RequestHeaderInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        String token = Hawk.get(PreferenceKey.ID,"");
        Request originalRequest = chain.request();//旧链接
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .addHeader("WZCTK", token);
        Log.e("msg","添加头部token="+token);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}