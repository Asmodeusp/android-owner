package com.saimawzc.shipper.modle.order.Imple.waybill;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.dto.order.wallbill.OrderWayBillDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.AssignDelationModel;
import com.saimawzc.shipper.modle.order.model.waybill.WayBillListModel;
import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.view.order.waybill.WayBillListView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;
import com.saimawzc.shipper.weight.utils.listen.order.waybill.WayBillListListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WayBillListModleImple extends BasEModeImple implements WayBillListModel {



    @Override
    public void getWayBillList(final WayBillListView view, final WayBillListListener listListener,
                               int page,String search,int status) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            if(!search.equals("全部")){
                jsonObject.put("condition",search);
            }
            if(status<10){
                jsonObject.put("status",status);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getWayBillList(body).enqueue(new CallBack<OrderWayBillDto>() {
            @Override
            public void success(OrderWayBillDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listListener.back(response.getList());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    /***删除**/
    @Override
    public void delete(final WayBillListView view, final WayBillListListener listListener, String id) {
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
        orderApi.deleteWayBill(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                view.oncomplete();

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
