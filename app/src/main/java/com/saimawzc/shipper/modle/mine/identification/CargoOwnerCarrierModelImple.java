package com.saimawzc.shipper.modle.mine.identification;

import android.content.Context;
import android.util.Log;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.base.CameraListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.identification.OwnCrriverIdentificationDto;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.mine.identificaion.CargoOwnerCarrierView;
import com.saimawzc.shipper.weight.utils.CameraDialogUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.identifiction.OwnerCarriverListener;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/3.
 */

public class CargoOwnerCarrierModelImple extends BasEModeImple implements CargoOwnerCarrierModel {
    private CameraDialogUtil cameraDialogUtil;


    @Override
    public void identification(final CargoOwnerCarrierView view, final BaseListener listener) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {
            jsonObject.put("carrierType",1);//承运商类型（1.个人承运商 2.一般纳税人 3.小规模企业）
            jsonObject.put("userName",view.getUser());
            jsonObject.put("systemIdent","1");//安卓获取IOS，安卓1
            jsonObject.put("address",view.getArea());
            jsonObject.put("frontIdCard",view.sringImgPositive());//身份证正面
            jsonObject.put("reverseIdCard",view.sringImgOtherSide());//身份证反面
            jsonObject.put("idCardNum",view.getIdNum());//身份证号码
            jsonObject.put("userAccount",loginDo.getUserAccount());
            jsonObject.put("userCode",loginDo.getUserCode());
            jsonObject.put("userId",loginDo.getId());
            jsonObject.put("userSource","Android");


            jsonObject.put("province",view.proviceName());
            jsonObject.put("provinceId",view.proviceId());
            jsonObject.put("city",view.cityName());
            jsonObject.put("cityId",view.cityId());
            jsonObject.put("area",view.countryName());
            jsonObject.put("areaId",view.countryId());

            if(view.getCompany()!=null){
                jsonObject.put("companyCode",view.getCompany().getCompanyCode());
                jsonObject.put("companyId",view.getCompany().getId());
                jsonObject.put("companyName",view.getCompany().getCompanyName());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.hzInentification(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();

            }
            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });

    }

    @Override
    public void reidentification(final CargoOwnerCarrierView view, final BaseListener listener) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        if(loginDo==null){
            listener.onFail("用户信息为空");
            return;
        }
        try {
            jsonObject.put("carrierType",1);//承运商类型（1.个人承运商 2.一般纳税人 3.小规模企业）
            jsonObject.put("userName",view.getUser());
            jsonObject.put("systemIdent","1");//安卓获取IOS，安卓1
            jsonObject.put("address",view.getArea());
            jsonObject.put("frontIdCard",view.sringImgPositive());//身份证正面
            jsonObject.put("reverseIdCard",view.sringImgOtherSide());//身份证反面
            jsonObject.put("idCardNum",view.getIdNum());//身份证号码
            jsonObject.put("userAccount",loginDo.getUserAccount());
            jsonObject.put("userCode",loginDo.getUserCode());
            jsonObject.put("userId",loginDo.getId());
            jsonObject.put("userSource","Android");


            jsonObject.put("province",view.proviceName());
            jsonObject.put("provinceId",view.proviceId());
            jsonObject.put("city",view.cityName());
            jsonObject.put("cityId",view.cityId());
            jsonObject.put("area",view.countryName());
            jsonObject.put("areaId",view.countryId());

            if(view.getCompany()!=null){
                jsonObject.put("companyCode",view.getCompany().getCompanyCode());
                jsonObject.put("companyId",view.getCompany().getId());
                jsonObject.put("companyName",view.getCompany().getCompanyName());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.rehzInentification(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();

            }
            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });
    }

    @Override
    public void showCamera(Context context, final int type, final BaseListener listener) {
        if(cameraDialogUtil==null){
            cameraDialogUtil=new CameraDialogUtil(context);
        }
        cameraDialogUtil.showDialog(new CameraListener() {//type
            @Override
            public void takePic() {
                if(type==0){
                    listener.successful(0);//0身份证正面拍照
                }else if(type==1){
                    listener.successful(1);//身份证反面拍照
                }

            }

            @Override
            public void chooseLocal() {
                if(type==0){
                    listener.successful(2);//身份正面证本地选择
                }else if(type==1) {
                    listener.successful(3);//身份证反面本地选择
                }
            }
            @Override
            public void cancel() {
                cameraDialogUtil.dialog.dismiss();
                listener.successful(100);
            }
        });

    }

    @Override
    public void dissCamera() {
        if(cameraDialogUtil!=null){
            cameraDialogUtil.dialog.dismiss();
        }

    }

    @Override
    public void getIdentificationInfo(final CargoOwnerCarrierView view, final OwnerCarriverListener listener) {
        view.showLoading();
        mineApi.identification().enqueue(new CallBack<OwnCrriverIdentificationDto>() {
            @Override
            public void success(OwnCrriverIdentificationDto response) {
                view.dissLoading();
                listener.driviceIndetification(response);
            }

            @Override
            public void fail(String code, String message) {

                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
