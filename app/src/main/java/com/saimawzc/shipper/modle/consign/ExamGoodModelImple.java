package com.saimawzc.shipper.modle.consign;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.base.CameraListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.consign.ExamGoodsView;
import com.saimawzc.shipper.weight.utils.CameraDialogUtil;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ExamGoodModelImple extends BasEModeImple implements ExamGoodModel {




    private CameraDialogUtil cameraDialogUtil;
    @Override
    public void showCamera(final ExamGoodsView view, Context context) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }
        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {
                view.successful(0);
                cameraDialogUtil.dialog.dismiss();

            }
            @Override
            public void chooseLocal() {
                view.successful(1);
                cameraDialogUtil.dialog.dismiss();

            }
            @Override
            public void cancel() {
                cameraDialogUtil.dialog.dismiss();
            }
        });
    }

    @Override
    public void examgood(final ExamGoodsView view, String id, String pics,String extent) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("picture",pics);
            jsonObject.put("extent",extent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.examgoodsubmit(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                view.oncomplete();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }


}
