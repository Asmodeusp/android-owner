package com.saimawzc.shipper.modle.mine.carleader.imple;




import com.saimawzc.shipper.dto.carleader.SearchTeamDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.mine.carleader.SearchTeamModel;
import com.saimawzc.shipper.view.mine.carleader.SearchTeamView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 *
 */

public class SearchTeamModelImple extends BasEModeImple implements SearchTeamModel {




    @Override
    public void getData(final SearchTeamView view, String carNo, String phone) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carNo",carNo);
            jsonObject.put("phone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getSearchTeam(body).enqueue(new CallBack<SearchTeamDto>() {
            @Override
            public void success(SearchTeamDto response) {
                view.dissLoading();
                view.getPersonList(response);

            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);

            }
        });
    }
}
