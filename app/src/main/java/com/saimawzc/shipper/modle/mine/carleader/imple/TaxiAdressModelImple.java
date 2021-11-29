package com.saimawzc.shipper.modle.mine.carleader.imple;




import com.saimawzc.shipper.dto.carleader.TaxiAreaDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.mine.carleader.TaxiAdressModel;
import com.saimawzc.shipper.view.mine.carleader.TaxiAdressView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TaxiAdressModelImple extends BasEModeImple implements TaxiAdressModel {


    @Override
    public void getAdressList(final TaxiAdressView view, String pid) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pid",pid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.getAreaTaxi(body).enqueue(new CallBack<List<TaxiAreaDto>>() {
            @Override
            public void success(List<TaxiAreaDto> response) {
                view.dissLoading();
                view.getadressList(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
