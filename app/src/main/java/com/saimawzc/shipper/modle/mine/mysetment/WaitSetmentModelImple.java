package com.saimawzc.shipper.modle.mine.mysetment;




import com.saimawzc.shipper.dto.myselment.WaitComfirmQueryPageDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.mysetment.WaitSetmentView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/3.
 */

public class WaitSetmentModelImple extends BasEModeImple implements WaitSetmentModel {

    @Override
    public void getList(int page, final WaitSetmentView view) {

        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getBigorderlist(body).enqueue(new CallBack<WaitComfirmQueryPageDto>() {
            @Override
            public void success(WaitComfirmQueryPageDto response) {
                view.dissLoading();
                view.getData(response);
                view.stopResh();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.stopResh();
            }
        });
    }
}
