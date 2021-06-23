package com.saimawzc.shipper.modle.mine.carrive;

import android.content.Context;
import android.util.Log;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.base.CameraListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.dto.identification.OwnCrriverIdentificationDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.mine.identification.CargoOwnerCarrierModel;
import com.saimawzc.shipper.view.mine.carrive.CarriveGroupView;
import com.saimawzc.shipper.view.mine.identificaion.CargoOwnerCarrierView;
import com.saimawzc.shipper.weight.utils.CameraDialogUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.carrive.CarriveGroupListen;
import com.saimawzc.shipper.weight.utils.listen.identifiction.OwnerCarriverListener;
import com.saimawzc.shipper.weight.utils.listen.order.ConsignCompanyListener;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/3.
 */

public class CarriveGroupModelImple extends BasEModeImple implements CarriveGroupModel {



    @Override
    public void getList(final CarriveGroupView view,final CarriveGroupListen listener) {
        view.showLoading();

        mineApi.getCarriveList().enqueue(new CallBack<List<MyCarrierGroupDto>>() {
            @Override
            public void success(List<MyCarrierGroupDto> response) {
                listener.getCarrive(response);
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
