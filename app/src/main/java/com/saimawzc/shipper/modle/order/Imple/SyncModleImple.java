package com.saimawzc.shipper.modle.order.Imple;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.SyncModel;
import com.saimawzc.shipper.view.order.SyncView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SyncModleImple extends BasEModeImple implements SyncModel {



    @Override
    public void sync(final SyncView view, String startTime, String endTime, String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("startDate",startTime);
            jsonObject.put("endDate",endTime);
            if(type.equals("同步销售通知单")){
                jsonObject.put("businessType",1);
            }else if(type.equals("同步采购通知单")){
                jsonObject.put("businessType",2);
            }else {
                jsonObject.put("businessType",3);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.sync(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                view.oncomplete();


            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
