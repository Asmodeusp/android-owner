package com.saimawzc.shipper.modle.order.Imple;

import android.util.Log;

import com.saimawzc.shipper.dto.order.consute.ConsuteDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.OrderOpintalInfoModel;
import com.saimawzc.shipper.view.order.OrderOptionalInfoView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 审核实现
 * **/
public class OrderOptionalnfoModelImple extends BasEModeImple implements OrderOpintalInfoModel {




    /****
     * 获取订单详情
     * **/
    @Override
    public void getOrderDelation(final OrderOptionalInfoView view, final OrderDelationListener listener, String id,String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        if(type.equals("waybill")){//预运单
            orderApi.getWayBillOrderDelation(body).enqueue(new CallBack<OrderDelationDto>() {
                @Override
                public void success(OrderDelationDto response) {
                    view.dissLoading();
                    Log.d("OrderOptionalnfoModelIm", response.getChoose().toString());
                    listener.back(response);

                }
                @Override
                public void fail(String code, String message) {
                    view.dissLoading();
                    view.Toast(message);
                }
            });
        }else {
            orderApi.getOrderDelation(body).enqueue(new CallBack<OrderDelationDto>() {
                @Override
                public void success(OrderDelationDto response) {
                    view.dissLoading();
                    Log.d("OrderOptionalnfoModelIm", response.getChoose().toString());
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

    @Override
    public void getConsuteDelation(final OrderOptionalInfoView view, String code, String type) {
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
                view.getConsuteDealtion(response);

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
