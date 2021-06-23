package com.saimawzc.shipper.modle.order.Imple;

import android.text.TextUtils;

import com.saimawzc.shipper.dto.order.ConsultDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.ConsultModel;
import com.saimawzc.shipper.view.order.ConsultView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ConsultModleImple extends BasEModeImple implements ConsultModel {


    @Override
    public void getConsult(final ConsultView view, String businessType, int page, String searchType, String searchValue) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("businessType",businessType);
            jsonObject.put("pageNum",page);
            if(!TextUtils.isEmpty(searchType)&&!TextUtils.isEmpty(searchValue)){
                if(!searchType.equals("全部")){
                    jsonObject.put(searchType,searchValue);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getConsute(body).enqueue(new CallBack<ConsultDto>() {
            @Override
            public void success(ConsultDto response) {
                view.dissLoading();
                view.getConsult(response.getList());
                view.isLast(response.isLastPage());



            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
