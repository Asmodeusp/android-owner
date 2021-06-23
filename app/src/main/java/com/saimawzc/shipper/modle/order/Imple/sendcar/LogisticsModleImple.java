package com.saimawzc.shipper.modle.order.Imple.sendcar;

import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.dto.order.send.LogistcsDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.AssignDelationModel;
import com.saimawzc.shipper.modle.order.model.sendar.LogisticsModel;
import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.view.order.sendcar.LogisticsView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LogisticsModleImple extends BasEModeImple implements LogisticsModel {



    @Override
    public void getLogistics(final LogisticsView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getWuLiuInfo(body).enqueue(new CallBack<LogistcsDto>() {
            @Override
            public void success(LogistcsDto response) {
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
