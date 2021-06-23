package com.saimawzc.shipper.modle.order.Imple.sendcar;

import com.saimawzc.shipper.dto.order.send.LogistcsDto;
import com.saimawzc.shipper.dto.order.send.SendCarDelationDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.sendar.SendCarDelationModel;
import com.saimawzc.shipper.view.order.sendcar.SendCarDelationView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SendCarDelationModleImple extends BasEModeImple implements SendCarDelationModel {

    @Override
    public void getSendCarDelation(final SendCarDelationView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getSendCardelation(body).enqueue(new CallBack<SendCarDelationDto>() {
            @Override
            public void success(SendCarDelationDto response) {
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
