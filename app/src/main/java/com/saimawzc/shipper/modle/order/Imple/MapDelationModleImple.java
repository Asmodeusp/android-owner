package com.saimawzc.shipper.modle.order.Imple;


import com.saimawzc.shipper.dto.order.creatorder.MapDelationDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.MapDelationModel;
import com.saimawzc.shipper.view.order.route.MapDealtionView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MapDelationModleImple extends BasEModeImple implements MapDelationModel {


    @Override
    public void getMapDelation(final MapDealtionView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getMapDelation(body).enqueue(new CallBack<MapDelationDto>() {
            @Override
            public void success(MapDelationDto response) {
                view.dissLoading();
                view.getMapDelation(response);


            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
