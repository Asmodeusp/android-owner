package com.saimawzc.shipper.modle.order.Imple.sendcar;

import android.text.TextUtils;


import com.saimawzc.shipper.dto.order.send.WarnInfoDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.sendar.WarnInfoModel;
import com.saimawzc.shipper.view.order.sendcar.WarnInfoView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WarnInfoModelImple extends BasEModeImple implements WarnInfoModel {


    @Override
    public void getData(final WarnInfoView view, String id, String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getWarnInfo(body).enqueue(new CallBack<List<WarnInfoDto>>() {
            @Override
            public void success(List<WarnInfoDto> response) {
                view.dissLoading();
                view.getData(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
