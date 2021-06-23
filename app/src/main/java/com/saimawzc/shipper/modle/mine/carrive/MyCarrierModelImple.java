package com.saimawzc.shipper.modle.mine.carrive;


import com.saimawzc.shipper.dto.carrier.MyCarriveDto;
import com.saimawzc.shipper.dto.carrier.MycarrierGroupSecondDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.mine.carrive.SearchCarriveView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.carrive.SearchCarriveListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/10.
 */

public class MyCarrierModelImple extends BasEModeImple implements SearchCarrierModel {

    @Override
    public void getCarrier(final SearchCarriveView view, final SearchCarriveListener listener, String phone) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.searchCarrivebyphone(body).enqueue(new CallBack<MyCarriveDto>() {
            @Override
            public void success(MyCarriveDto response) {
                listener.callbackbrand(response);
                view.dissLoading();


            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);

            }
        });
    }
}
