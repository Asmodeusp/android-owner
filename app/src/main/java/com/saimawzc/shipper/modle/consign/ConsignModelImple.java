package com.saimawzc.shipper.modle.consign;

import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.shipper.dto.consign.ConsignDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.consign.ConsignView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/31.
 * 忘记密码
 */

public class ConsignModelImple extends BasEModeImple implements ConsignModel {

    @Override
    public void getConsign(final ConsignView view, int page,String searchtype,String searchvalue) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            if(!TextUtils.isEmpty(searchtype)&&!TextUtils.isEmpty(searchvalue)){
                jsonObject.put(searchtype,searchvalue);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        view.stopResh();
        orderApi.getconsignList(body).enqueue(new CallBack<ConsignDto>() {
            @Override
            public void success(ConsignDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                view.getConsignList(response.getList());
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
