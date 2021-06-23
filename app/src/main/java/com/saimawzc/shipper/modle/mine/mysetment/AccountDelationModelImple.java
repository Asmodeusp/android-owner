package com.saimawzc.shipper.modle.mine.mysetment;



import com.saimawzc.shipper.dto.myselment.AccountDelationDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.mysetment.AccountDelationView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 * 我的司机
 */

public class AccountDelationModelImple extends BasEModeImple implements AccountDelationModel {



    @Override
    public void datas(String id, final AccountDelationView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getAccountDelation(body).enqueue(new CallBack<AccountDelationDto>() {
            @Override
            public void success(AccountDelationDto response) {
                view.dissLoading();
                view.getData(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
