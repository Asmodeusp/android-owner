package com.saimawzc.shipper.modle.order.Imple;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.StopTrantOrderModel;
import com.saimawzc.shipper.view.BaseView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class StopTrantOderModelImple extends BasEModeImple implements StopTrantOrderModel {

    @Override
    public void stopTrantOrder(final BaseView view, final BaseListener listener,
                               String id, int wayBillStatus, String reason) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("wayBillStatus",wayBillStatus);
            jsonObject.put("endOpinion",reason);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.stopTrantsport(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
