package com.saimawzc.shipper.modle.mine.mysetment;

import android.util.Log;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.SearchValueDto;
import com.saimawzc.shipper.dto.myselment.MySetmentDto;
import com.saimawzc.shipper.dto.myselment.MySetmentPageQueryDto;
import com.saimawzc.shipper.dto.pic.AdListDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.mine.BannerModel;
import com.saimawzc.shipper.view.mine.BannerView;
import com.saimawzc.shipper.view.mysetment.MySetmentView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/3.
 */

public class MySetmentModelImple extends BasEModeImple implements MySetmentModel {

    @Override
    public void getList(int page, int type, List<SearchValueDto>searchdtos, final MySetmentView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("pageSize",20);
            jsonObject.put("checkStatus",type);
            if(searchdtos!=null){
                for(int i=0;i<searchdtos.size();i++){
                    jsonObject.put(searchdtos.get(i).getSearchName(),searchdtos.get(i).getGetSearchValue());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.getmysetmentlist(body).enqueue(new CallBack<MySetmentPageQueryDto>() {
            @Override
            public void success(MySetmentPageQueryDto response) {
                view.dissLoading();
                view.getMySetment(response);
                view.stopResh();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.stopResh();
            }
        });
    }

    @Override
    public void comfirm(int type, String id, final MySetmentView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("confirmType",type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        bmsApi.confirm(body).enqueue(new CallBack<EmptyDto>() {
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
