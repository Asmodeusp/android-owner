package com.saimawzc.shipper.presenter.mine;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.base.CameraListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.mine.PersonCenterModel;
import com.saimawzc.shipper.view.mine.PersonCenterView;
import com.saimawzc.shipper.weight.utils.CameraDialogUtil;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/8.
 * 个人中心
 */

public class PersonCenterModelImple extends BasEModeImple implements PersonCenterModel {
    private CameraDialogUtil cameraDialogUtil;

    @Override
    public void showCamera(Context context, final BaseListener listener) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }
        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {
                listener.successful(0);
                cameraDialogUtil.dialog.dismiss();
            }
            @Override
            public void chooseLocal() {
                listener.successful(1);
                cameraDialogUtil.dialog.dismiss();
            }
            @Override
            public void cancel() {
                listener.successful(100);
                cameraDialogUtil.dialog.dismiss();
            }
        });

    }

    @Override
    public void changePic(final PersonCenterView view, final BaseListener listener) {

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("picture",view.stringHead());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.modifyUserInfo(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                listener.successful();
                view.dissLoading();
            }
            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });

    }

    @Override
    public void changeSex(PersonCenterView view, BaseListener listener) {

    }
}
