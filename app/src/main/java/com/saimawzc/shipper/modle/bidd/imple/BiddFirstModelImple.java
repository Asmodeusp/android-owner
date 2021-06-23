package com.saimawzc.shipper.modle.bidd.imple;


import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.bidd.BiddFirstDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.bidd.model.PBiddFirstModel;
import com.saimawzc.shipper.view.bidd.BiddFirstView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
/***
 * 竞价
 * **/
public class BiddFirstModelImple extends BasEModeImple implements PBiddFirstModel {

    @Override
    public void getBiddLsit(final BiddFirstView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getBiddenFirst(body).enqueue(new CallBack<List<BiddFirstDto>>() {
            @Override
            public void success(List<BiddFirstDto> response) {
                view.dissLoading();
                view.getPlanBiddList(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
    @Override
    public void stop(final BiddFirstView view, String id,int status,String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("status",status);
            jsonObject.put("id",id);
            if(type.equals("orderplan")){
                jsonObject.put("type",1);
            }else if(type.equals("waybill")){
                jsonObject.put("type",2);
            }else {
                jsonObject.put("type",3);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.endBidd(body).enqueue(new CallBack<EmptyDto>() {
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
