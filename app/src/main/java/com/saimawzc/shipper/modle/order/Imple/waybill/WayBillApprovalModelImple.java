package com.saimawzc.shipper.modle.order.Imple.waybill;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.OrderApprovalModel;
import com.saimawzc.shipper.modle.order.model.waybill.WayBillApprovalModel;
import com.saimawzc.shipper.view.order.OrderApprovalView;
import com.saimawzc.shipper.view.order.waybill.WaybillApprovalView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 预运单审核实现
 * **/
public class WayBillApprovalModelImple extends BasEModeImple implements WayBillApprovalModel {

    @Override
    public void approval(final WaybillApprovalView view, final BaseListener listener, String id, int status) {
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
        orderApi.WaybillshOrder(body).enqueue(new CallBack<EmptyDto>() {
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
    public void getOrderDelation(final WaybillApprovalView view, final OrderDelationListener listener, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getWayBillOrderDelation(body).enqueue(new CallBack<OrderDelationDto>() {
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
    public void getOrderDelationYhr(final WaybillApprovalView view, final OrderDelationListener listener, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getWayBillShrOrderDelation(body).enqueue(new CallBack<OrderDelationDto>() {
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
