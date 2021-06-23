package com.saimawzc.shipper.modle.mine.set;



import com.saimawzc.shipper.dto.set.SuggestDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.mine.set.MySuggestListView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MySuggestModelImple extends BasEModeImple implements MySuggestModel {

    @Override
    public void getSuggestList(final MySuggestListView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.mySuggestList(body).enqueue(new CallBack<List<SuggestDto>>() {
            @Override
            public void success(List<SuggestDto> respon) {
                view.dissLoading();
                view.getErrorList(respon);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
