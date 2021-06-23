package com.saimawzc.shipper.modle.order.Imple;

import com.saimawzc.shipper.dto.order.OrderBiddenHistoryDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.OrderBiddHistoryModel;
import com.saimawzc.shipper.view.order.OrderBiddHistoryView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.OrderBiddHistoryListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrderBiddHistoryModelImple extends BasEModeImple implements OrderBiddHistoryModel {

    @Override
    public void getOrderList(final OrderBiddHistoryView view, final OrderBiddHistoryListener listener,
                             String id, String cysId) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("cysId",cysId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());


        orderApi.getBiddHistory(body).enqueue(new CallBack<List<OrderBiddenHistoryDto>>() {
            @Override
            public void success(List<OrderBiddenHistoryDto> response) {
                view.dissLoading();
                listener.back(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
