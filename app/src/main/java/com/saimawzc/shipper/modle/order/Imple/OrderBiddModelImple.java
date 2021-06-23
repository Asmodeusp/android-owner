package com.saimawzc.shipper.modle.order.Imple;

import android.util.Log;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.dto.order.bidd.BiddTempDto;
import com.saimawzc.shipper.dto.order.bidd.CarTypeDo;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.OrderBiddModel;
import com.saimawzc.shipper.view.order.OrderBiddView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.OrderBiddListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 分组实现
 * **/
public class OrderBiddModelImple extends BasEModeImple implements OrderBiddModel {



    @Override
    public void getCarriveSecond(final OrderBiddView view, final OrderBiddListener listener, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getCarriverSecondList(body).enqueue(new CallBack<List<OrderAssignmentSecondDto>>() {
            @Override
            public void success(List<OrderAssignmentSecondDto> response) {
                view.dissLoading();
                listener.back(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void getCarType(final OrderBiddView view) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        authApi.getCarType(body).enqueue(new CallBack<List<CarTypeDo>>() {
            @Override
            public void success(List<CarTypeDo> response) {
                view.dissLoading();
                view.getCarType(response);
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    @Override
    public void orderBidd(final OrderBiddView view, final OrderBiddListener listener,
                          BiddTempDto tempDto, final List<OrderAssignmentSecondDto> dtos) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",tempDto.getId());
            jsonObject.put("wayBillType",tempDto.getWayBillType());
            jsonObject.put("startTime",tempDto.getStartTime());
            jsonObject.put("endTime",tempDto.getEndTime());
            jsonObject.put("biddNum",tempDto.getBiddNum());
            jsonObject.put("extent",tempDto.getExtent());
            jsonObject.put("carTypeId",tempDto.getCarTypeId());
            jsonObject.put("carTypeName",tempDto.getCarTypeName());
            jsonObject.put("weight",tempDto.getBiddWeight());
            jsonObject.put("roleType",tempDto.getRoleType());
            jsonObject.put("extent",tempDto.getExtent());
            jsonObject.put("floorPrice",tempDto.getEdFloorPrice());
            jsonObject.put("taskStartTime",tempDto.getTaskTimeStart());
            jsonObject.put("taskEndTime",tempDto.getTaskTimeEnd());
            jsonObject.put("highWeight",tempDto.getHighBiddNum());
            jsonObject.put("showRanking",tempDto.getIsRank());
            if(tempDto.getRoleType().equals("5")){
                jsonObject.put("needBeiDou",tempDto.getNeedBeiDou());
                jsonObject.put("moreDispatch",tempDto.getMoreDispatch());
                jsonObject.put("carTypeId",tempDto.getCarTypeId());
                jsonObject.put("carTypeName",tempDto.getCarTypeName());
            }
            JSONArray array=new JSONArray();
            if(dtos!=null&&dtos.size()>0){
                for(int i=0;i<dtos.size();i++){
                    if(dtos.get(i)!=null){
                        JSONObject object=new JSONObject();
                        object.put("cysId",dtos.get(i).getCysId());
                        array.put(object);
                    }
                }
                jsonObject.put("list",array);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.addBidd(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();
            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
