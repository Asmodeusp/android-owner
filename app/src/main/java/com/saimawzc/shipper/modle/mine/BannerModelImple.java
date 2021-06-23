package com.saimawzc.shipper.modle.mine;

import android.content.Context;
import android.util.Log;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.base.CameraListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.identification.OwnCrriverIdentificationDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.dto.pic.AdListDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.mine.identification.CargoOwnerCarrierModel;
import com.saimawzc.shipper.view.mine.BannerView;
import com.saimawzc.shipper.view.mine.identificaion.CargoOwnerCarrierView;
import com.saimawzc.shipper.weight.utils.CameraDialogUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.identifiction.OwnerCarriverListener;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/3.
 */

public class BannerModelImple extends BasEModeImple implements BannerModel {

    @Override
    public void getBanner(final BannerView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id","");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.getBanner(body).enqueue(new CallBack<List<AdListDto>>() {
            @Override
            public void success(List<AdListDto> response) {
                view.dissLoading();
                view.getBanner(response);


            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
