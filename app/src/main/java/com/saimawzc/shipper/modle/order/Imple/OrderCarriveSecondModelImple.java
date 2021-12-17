package com.saimawzc.shipper.modle.order.Imple;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.OrderCarriveSecondModel;
import com.saimawzc.shipper.view.order.OrderCarriveSecondView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverSecondListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 分组实现
 * **/
public class OrderCarriveSecondModelImple extends BasEModeImple implements OrderCarriveSecondModel {



    @Override
    public void getCarriveSecond(final OrderCarriveSecondView view, final OrderCarriverSecondListener listener,String id) {
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


        orderApi.getCarriverSecondList(body).enqueue(new CallBack<List<OrderAssignmentSecondDto>>() {
            @Override
            public void success(List<OrderAssignmentSecondDto> response) {
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
    public void orderZp(final OrderCarriveSecondView view, final OrderCarriverSecondListener listener,
                        List<OrderAssignmentSecondDto> dtos, String id) {

        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            JSONArray array=new JSONArray();
            for(int i=0;i<dtos.size();i++){
                if(dtos.get(i)!=null){
                    JSONObject tempObj=new JSONObject();
                    tempObj.put("cysId",dtos.get(i).getCysId());
                    tempObj.put("cysCode",dtos.get(i).getCysCode());
                    tempObj.put("cysName",dtos.get(i).getCysName());
                    tempObj.put("cysPhone",dtos.get(i).getCysPhone());
                    tempObj.put("price",dtos.get(i).getTrantPrice());
                    tempObj.put("weight",dtos.get(i).getTrantNum());
                    tempObj.put("zpTime",dtos.get(i).getZpTime());
                    array.put(tempObj);
                }
            }
            jsonObject.put("list",array);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());


        orderApi.orderZp(body).enqueue(new CallBack<EmptyDto>() {
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
