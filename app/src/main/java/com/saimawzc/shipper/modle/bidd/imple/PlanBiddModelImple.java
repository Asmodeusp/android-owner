package com.saimawzc.shipper.modle.bidd.imple;

import android.text.TextUtils;

import com.saimawzc.shipper.dto.order.bidd.PlanBiddDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.bidd.model.PlanBiddModel;
import com.saimawzc.shipper.modle.order.model.OrderBasicInfoModel;
import com.saimawzc.shipper.view.bidd.PlanBiddView;
import com.saimawzc.shipper.view.order.OrderBasicInfoView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;
import com.saimawzc.shipper.weight.utils.listen.order.bidd.PlanBiddListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 竞价
 * **/
public class PlanBiddModelImple extends BasEModeImple implements PlanBiddModel {

    @Override
    public void getBiddLsit(final PlanBiddView view, final PlanBiddListener listener,
                            String type, int page, String searchType, String searchValue) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("waybillType",type);
            if(!TextUtils.isEmpty(searchType)&&!TextUtils.isEmpty(searchValue)){
                jsonObject.put(searchType,searchValue);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getPlanBidd(body).enqueue(new CallBack<PlanBiddDto>() {
            @Override
            public void success(PlanBiddDto response) {
                view.dissLoading();
                view.isLastpage(response.isLastPage());
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
