package com.saimawzc.shipper.modle.mine.carleader.imple;



import com.saimawzc.shipper.dto.carleader.FaceQueryDto;
import com.saimawzc.shipper.dto.carleader.TeamDelationDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.mine.carleader.TeamDelationModel;
import com.saimawzc.shipper.view.mine.carleader.TeamDelationView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 *
 */

public class TeamDelationModelImple extends BasEModeImple implements TeamDelationModel {




    @Override
    public void getData(final TeamDelationView view, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getTeamDelation(body).enqueue(new CallBack<TeamDelationDto>() {
            @Override
            public void success(TeamDelationDto response) {
                view.dissLoading();
                view.getDelation(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);

            }
        });
    }

    @Override
    public void getPerInfo(final TeamDelationView view, final String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getFaceINfo(body).enqueue(new CallBack<FaceQueryDto.Facedata>() {
            @Override
            public void success(FaceQueryDto.Facedata response) {
                view.dissLoading();
                view.getPersonifo(response,id);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);

            }
        });
    }
}
