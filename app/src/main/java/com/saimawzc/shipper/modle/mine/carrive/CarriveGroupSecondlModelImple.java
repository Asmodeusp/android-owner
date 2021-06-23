package com.saimawzc.shipper.modle.mine.carrive;

import android.util.Log;

import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.dto.carrier.MycarrierGroupSecondDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.mine.carrive.CarriveGroupView;
import com.saimawzc.shipper.view.mine.carrive.CarriveSecondView;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.carrive.CarriveGroupListen;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/3.
 */

public class CarriveGroupSecondlModelImple extends BasEModeImple implements CarriveSecondModel {





    @Override
    public void getList(final CarriveSecondView view,final CarriveSecondView listener,String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getCarrvesecond(body).enqueue(new CallBack<List<MycarrierGroupSecondDto>>() {
            @Override
            public void success(List<MycarrierGroupSecondDto> response) {
                listener.getCarriveList(response);
                view.dissLoading();
                view.stopResh();

            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.stopResh();
                view.Toast(message);

            }
        });

    }
}
