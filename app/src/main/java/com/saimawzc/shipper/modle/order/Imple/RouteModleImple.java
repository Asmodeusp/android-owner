package com.saimawzc.shipper.modle.order.Imple;


import com.saimawzc.shipper.dto.order.creatorder.ChooseRouteDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.RouteModel;
import com.saimawzc.shipper.view.order.route.RouteView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RouteModleImple extends BasEModeImple implements RouteModel {



    @Override
    public void getRoute(final RouteView view, String routeName) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("routeName",routeName);
            jsonObject.put("fromUserAddress","");
            jsonObject.put("toUserAddress","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        view.stopResh();
        orderApi.getRouteList(body).enqueue(new CallBack<List<ChooseRouteDto>>() {
            @Override
            public void success(List<ChooseRouteDto> response) {
                view.dissLoading();
                view.getRoute(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
