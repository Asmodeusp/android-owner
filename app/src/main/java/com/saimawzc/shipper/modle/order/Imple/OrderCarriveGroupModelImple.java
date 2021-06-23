package com.saimawzc.shipper.modle.order.Imple;

import com.saimawzc.shipper.dto.order.PlanOrderPageDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderCarrierGroupDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.OrderCarriveGroupModel;
import com.saimawzc.shipper.view.order.OrderCarriveGroupView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverGroupListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 分组实现
 * **/
public class OrderCarriveGroupModelImple extends BasEModeImple implements OrderCarriveGroupModel {

    @Override
    public void getCarriveGroup(final OrderCarriveGroupView view, final OrderCarriverGroupListener listener) {
        view.showLoading();
        view.stopResh();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getCarriverGroupList(body).enqueue(new CallBack<List<OrderCarrierGroupDto>>() {
            @Override
            public void success(List<OrderCarrierGroupDto> response) {
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
