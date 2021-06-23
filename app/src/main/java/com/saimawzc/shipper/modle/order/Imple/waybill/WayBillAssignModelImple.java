package com.saimawzc.shipper.modle.order.Imple.waybill;

import android.view.View;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.dto.order.wallbill.WayBillAssignDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.waybill.WayBillAssignModel;
import com.saimawzc.shipper.view.order.waybill.WayBillAssignView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.waybill.WaybillAssignListListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WayBillAssignModelImple extends BasEModeImple implements WayBillAssignModel {

    @Override
    public void getWayBillAssign(final WayBillAssignView view, final WaybillAssignListListener listListener, int page,String phone) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("cysPhone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
         view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());


        orderApi.getWayBillCys(body).enqueue(new CallBack<WayBillAssignDto>() {
            @Override
            public void success(WayBillAssignDto response) {
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

    @Override
    public void wayBillZp(final WayBillAssignView view, final WaybillAssignListListener listListener,
                          WayBillAssignDto.waybillData data, String id, String price,String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("wayBillType",type);
            JSONObject temp=new JSONObject();
            temp.put("cysId",data.getCysId());
            temp.put("cysCode",data.getCysCode());
            temp.put("cysName",data.getCysName());
            temp.put("cysPhone",data.getCysPhone());
            temp.put("price",price);
            jsonObject.put("cysInfoAppointReq",temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());


        orderApi.appointWayBill(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listListener.successful();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }


}
