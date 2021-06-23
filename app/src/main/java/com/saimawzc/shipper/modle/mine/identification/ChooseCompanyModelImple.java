package com.saimawzc.shipper.modle.mine.identification;

import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.identification.CompanyDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.identifiction.ChooseCopmanyListener;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/6.
 * 选择公司实现类
 */

public class ChooseCompanyModelImple extends BasEModeImple implements ChooseCompanyModel  {


    @Override
    public void getCompanyList(String companyName, final ChooseCopmanyListener listener) {

        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {
            if(!TextUtils.isEmpty(companyName)){
                jsonObject.put("companyName",companyName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.getCompanyList(body).enqueue(new CallBack<List<CompanyDto>>() {
            @Override
            public void success(List<CompanyDto> response) {
                listener.getCompanyList(response);

            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);

            }
        });

    }
}
