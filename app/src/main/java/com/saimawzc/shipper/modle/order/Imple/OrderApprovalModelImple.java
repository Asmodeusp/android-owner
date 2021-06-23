package com.saimawzc.shipper.modle.order.Imple;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.OrderApprovalModel;
import com.saimawzc.shipper.view.order.OrderApprovalView;
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
public class OrderApprovalModelImple extends BasEModeImple implements OrderApprovalModel {

    @Override
    public void approval(final OrderApprovalView view, final BaseListener listener,String id,int status) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("checkStatus",status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.shOrder(body).enqueue(new CallBack<EmptyDto>() {
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


    /****
     * 获取订单详情
     * **/
    @Override
    public void getOrderDelation(final OrderApprovalView view, final OrderDelationListener listener, String id) {
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
}
