package com.saimawzc.shipper.modle.order.Imple.sendcar;

import com.saimawzc.shipper.dto.order.send.ChangeCarDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.sendar.ChangeCarModel;
import com.saimawzc.shipper.view.order.sendcar.ChangeCarView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
public class ChangeCarModleImple extends BasEModeImple implements ChangeCarModel {


    @Override
    public void getLogistics(final ChangeCarView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getChangeCar(body).enqueue(new CallBack<List<ChangeCarDto>>() {
            @Override
            public void success(List<ChangeCarDto> response) {
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
