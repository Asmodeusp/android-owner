package com.saimawzc.shipper.modle.order.Imple;

import android.text.TextUtils;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.SendCarDto;
import com.saimawzc.shipper.dto.order.SignWeightDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.SendCarLsitModel;
import com.saimawzc.shipper.view.order.SendCarListView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.SendCarListListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendCarLsitModelImple extends BasEModeImple implements SendCarLsitModel {


    @Override
    public void getSendCarLsit(final SendCarListView view, final SendCarListListener listener,
                               int page, String type, String searchType, String searchValue) {
        view.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", type);
            jsonObject.put("pageNum", page);
            if (!TextUtils.isEmpty(searchType) && !TextUtils.isEmpty(searchValue)) {
                jsonObject.put(searchType, searchValue);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        orderApi.getSendCarList(body).enqueue(new CallBack<SendCarDto>() {
            @Override
            public void success(SendCarDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listener.getManageOrderList(response.getList());
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void sign(final SendCarListView view, String id, String num) {
        view.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("signInWeight", num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        orderApi.orderSign(body).enqueue(new CallBack<EmptyDto>() {
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

    @Override
    public void getsignWeight(final SendCarListView view, String id) {
        view.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        orderApi.getSinWeight(body).enqueue(new CallBack<SignWeightDto>() {
            @Override
            public void success(SignWeightDto response) {
                view.dissLoading();
                view.getSignWeight(response);


            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                // view.Toast(message);
                view.getSignWeight(null);
            }
        });
    }

    @Override
    public void getDoubtSignIn(final SendCarListView view, final SendCarListListener listener, String id, String doubtPicture, String doubtPoundReMark, String hzSignInWeight) {
        view.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(id)) {
                jsonObject.put("id", id);
            }
            if (!TextUtils.isEmpty(doubtPicture)) {
                jsonObject.put("doubtPicture", doubtPicture);
            } else {
                jsonObject.put("doubtPicture", "");
            }
            if (!TextUtils.isEmpty(doubtPoundReMark)) {
                jsonObject.put("doubtPoundReMark", doubtPoundReMark);
            } else {
                jsonObject.put("doubtPoundReMark", "");
            }
            if (!TextUtils.isEmpty(hzSignInWeight)) {
                jsonObject.put("hzSignInWeight", hzSignInWeight);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        orderApi.doubtSignIn(body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                view.dissLoading();
                view.getDoubtSignIn(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                view.dissLoading();
                view.Toast(throwable.toString());
            }
        });
//        new CallBack<String>() {
//            @Override
//            public void success(EmptyDto response) {
//                view.dissLoading();
//                view.getDoubtSignIn(response);
//            }
//
//            @Override
//            public void fail(String code, String message) {
//                view.dissLoading();
//                view.Toast(message);
//            }
//        }
    }
}
