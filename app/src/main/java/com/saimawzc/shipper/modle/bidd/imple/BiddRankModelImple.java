package com.saimawzc.shipper.modle.bidd.imple;
import com.saimawzc.shipper.dto.order.bidd.RankPageDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.bidd.model.RankBiddModel;
import com.saimawzc.shipper.view.bidd.BiddRandView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 竞价
 * **/
public class BiddRankModelImple extends BasEModeImple implements RankBiddModel {



    @Override
    public void getRankLsit(final BiddRandView view, int page,String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("pageNum",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getRankList(body).enqueue(new CallBack<RankPageDto>() {
            @Override
            public void success(RankPageDto response) {
                view.dissLoading();
                view.getRandLise(response.getList());
                view.isLastPage(response.isLastPage());
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
