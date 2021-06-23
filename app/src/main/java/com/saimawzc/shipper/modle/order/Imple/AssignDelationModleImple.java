package com.saimawzc.shipper.modle.order.Imple;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.AssignDelationModel;
import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AssignDelationModleImple extends BasEModeImple implements AssignDelationModel {

    @Override
    public void getAssignDelationList(final AssignDelationView view,
                                      final AssignDelationListener listener, String id, int page,String type) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("pageNum",page);
            jsonObject.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getAssignDelation(body).enqueue(new CallBack<AssignDelationDto>() {
            @Override
            public void success(AssignDelationDto response) {
                view.dissLoading();
                view.isLast(response.isLastPage());
                listener.back(response.getList());


            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
