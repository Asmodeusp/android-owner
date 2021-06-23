package com.saimawzc.shipper.modle.order.Imple.waybill;

import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.dto.order.wallbill.OrderInventoryDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.waybill.WayBillInventoryModel;
import com.saimawzc.shipper.view.order.waybill.WayBillInventoryView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.waybill.WayBillInventoryListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 预运单清单
 * **/
public class WayBillInventoryModelImple extends BasEModeImple implements WayBillInventoryModel {
    @Override
    public void getWayBillList(final WayBillInventoryView view, final WayBillInventoryListener listListener,String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getWayBillQd(body).enqueue(new CallBack<OrderInventoryDto>() {
            @Override
            public void success(final OrderInventoryDto response) {
                view.dissLoading();
                listListener.back(response.getList());

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
