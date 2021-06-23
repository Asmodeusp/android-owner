package com.saimawzc.shipper.modle.order.Imple.sendcar;



import com.saimawzc.shipper.dto.order.send.RouteDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.sendar.MapTravelModel;
import com.saimawzc.shipper.view.order.sendcar.MapTravelView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MapTravelModelImple extends BasEModeImple implements MapTravelModel {


    @Override
    public void roulete(final MapTravelView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getRoute(body).enqueue(new CallBack<RouteDto>() {
            @Override
            public void success(RouteDto response) {
                view.dissLoading();
                view.getRolte(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
