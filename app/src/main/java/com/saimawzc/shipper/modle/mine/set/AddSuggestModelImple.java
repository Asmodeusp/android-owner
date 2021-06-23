package com.saimawzc.shipper.modle.mine.set;

import android.content.Context;
import android.util.Log;


import com.saimawzc.shipper.base.CameraListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.mine.set.AddSuggetsView;
import com.saimawzc.shipper.weight.utils.CameraDialogUtil;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2020/8/13.
 */

public class AddSuggestModelImple extends BasEModeImple implements AddSuggestModel {

    @Override
    public void submit(final AddSuggetsView view, String miaoshu, String img) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("content",miaoshu);
            jsonObject.put("picture",img);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.addSuggest(body).enqueue(new CallBack<EmptyDto>() {
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
    private CameraDialogUtil cameraDialogUtil;
    @Override
    public void showCamera(final AddSuggetsView view, Context context) {
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
}
