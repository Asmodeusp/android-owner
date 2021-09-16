package com.saimawzc.shipper.modle.order.Imple.fence;

import com.saimawzc.shipper.dto.order.creatorder.FencePageDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.fence.FenceModel;
import com.saimawzc.shipper.view.order.fence.FenceView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
public class FenceModelImple extends BasEModeImple implements FenceModel {


    @Override
    public void getFenceList(final FenceView view, String pageNum, String enclosureName) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("enclosureName",enclosureName);
            jsonObject.put("pageNum",pageNum);
            jsonObject.put("pageSize","20");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        authApi.getFenceList(body).enqueue(new CallBack<FencePageDto>() {
            @Override
            public void success(FencePageDto response) {
                view.dissLoading();
                if(response!=null){
                    view.stopResh();
                    view.isLast(response.isLastPage());
                    view.getFenceList(response.getList());
                }else {
                    view.Toast("未获取到围栏数据");
                }
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
