package com.saimawzc.shipper.modle.order.Imple.ordermanage;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.dto.order.manage.OrderManageDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.AssignDelationModel;
import com.saimawzc.shipper.modle.order.model.ordermanage.OrderManageModel;
import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.view.order.manage.OrderManageView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;
import com.saimawzc.shipper.weight.utils.listen.order.ordermanage.OrderManageListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrderManageModleImple extends BasEModeImple implements OrderManageModel {



    @Override
    public void getOrderManageList(final OrderManageView view, final OrderManageListener listener,
                                   int page,String search) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("hzName",search);
            jsonObject.put("pageNum",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getManageOrderList(body).enqueue(new CallBack<OrderManageDto>() {
            @Override
            public void success(OrderManageDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listener.back(response.getList());


            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void delete(final OrderManageView view, final OrderManageListener listener, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.deleteManageOrder(body).enqueue(new CallBack<EmptyDto>() {
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
