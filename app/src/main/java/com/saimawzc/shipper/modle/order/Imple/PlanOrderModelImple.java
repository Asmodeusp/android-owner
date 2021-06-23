package com.saimawzc.shipper.modle.order.Imple;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.PlanOrderPageDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.PlanOrderModel;
import com.saimawzc.shipper.view.order.PlanOrderView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.PlanOrderListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PlanOrderModelImple extends BasEModeImple implements PlanOrderModel {




    @Override
    public void getOrderList(final PlanOrderView view, final PlanOrderListener listener,
                             int page,int type,String search,int status) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("pageSize",20);
            jsonObject.put("type",type);
            if(!search.equals("全部")){
                jsonObject.put("condition",search);
            }
            if(status<10){//大于10时不传
                jsonObject.put("status",status);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getPlanOrderlist(body).enqueue(new CallBack<PlanOrderPageDto>() {
            @Override
            public void success(PlanOrderPageDto response) {
                view.dissLoading();
                if(response.isLastPage()){
                    view.isLastPage(true);
                }else {
                    view.isLastPage(false);
                }
                listener.back(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

     /***
      * 删除计划订单
      * **/
    @Override
    public void delete(final PlanOrderView view, final BaseListener listener, String id) {
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
        orderApi.deletePlanOrder(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful(1);

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    /***
     * 暂停运输或者终止订单
     * **/
    @Override
    public void stopTransport(final PlanOrderView view, final BaseListener listener,
                              String id, int wayBillStatus,String reason) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("wayBillStatus",wayBillStatus);
            jsonObject.put("endOpinion",reason);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
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
