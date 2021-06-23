package com.saimawzc.shipper.modle.order.Imple;

import com.saimawzc.shipper.dto.order.consute.ConsuteDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.UntilDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.OrderBasicInfoModel;
import com.saimawzc.shipper.view.order.OrderBasicInfoView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 审核实现
 * **/
public class OrderBasicInfoModelImple extends BasEModeImple implements OrderBasicInfoModel {
    /****
     * 获取订单详情
     * **/
    @Override
    public void getOrderDelation(final OrderBasicInfoView view, final OrderDelationListener listener, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getOrderDelation(body).enqueue(new CallBack<OrderDelationDto>() {
            @Override
            public void success(OrderDelationDto response) {
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

    @Override
    public void getUntil(final OrderBasicInfoView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getUntilList(body).enqueue(new CallBack<List<UntilDto>>() {
            @Override
            public void success(List<UntilDto> response) {
                view.dissLoading();
                view.getUntil(response);

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getConsuteDelation(final OrderBasicInfoView view, String code, String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("businessType",type);
            jsonObject.put("orderCode",code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getConsuteDelation(body).enqueue(new CallBack<ConsuteDelationDto>() {
            @Override
            public void success(ConsuteDelationDto response) {
                view.dissLoading();
                view.getConsuteDelation(response);

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
