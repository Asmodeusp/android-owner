package com.saimawzc.shipper.modle.mine.mysetment;

import com.saimawzc.shipper.dto.SearchValueDto;
import com.saimawzc.shipper.dto.myselment.AccountManageDto;
import com.saimawzc.shipper.dto.myselment.AccountQueryPageDto;
import com.saimawzc.shipper.dto.myselment.MySetmentDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.mysetment.AccountManageView;
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

public class AccountManageModelImple extends BasEModeImple implements AccountManageModel {

    @Override
    public void getList(int page, List<SearchValueDto>searchdtos , final AccountManageView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pageNum",page);
            jsonObject.put("pageSize",20);
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
        bmsApi.getAccountList(body).enqueue(new CallBack<AccountQueryPageDto>() {
            @Override
            public void success(AccountQueryPageDto response) {
                view.dissLoading();
                view.getData(response);
                view.stopRefresh();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
                view.stopRefresh();
            }
        });

    }
}
