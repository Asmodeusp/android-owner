package com.saimawzc.shipper.modle.order.Imple;
import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.order.BiddingDelationDto;
import com.saimawzc.shipper.dto.order.BiddingpageDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.OrderBiddDelationModel;
import com.saimawzc.shipper.view.order.OrderBiddDelationView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
public class OrderBiddDelationModelImple extends BasEModeImple implements OrderBiddDelationModel {
    @Override
    public void getOrderDelationList(final OrderBiddDelationView view,
                                     final OrderDelationListListener listListener,int page, String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("pageNum",page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getBiddList(body).enqueue(new CallBack<BiddingpageDto>() {
            @Override
            public void success(BiddingpageDto response) {
                view.dissLoading();
                view.isLastPage(response.isLastPage());
                listListener.back(response.getList());


            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }

    /****
     * 确认竞价
     * **/
    @Override
    public void orderBidd(final OrderBiddDelationView view, final BaseListener listListener,
                          String id, final List<BiddingDelationDto> delationDtos) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);

            JSONArray array=new JSONArray();
            for(int i=0;i<delationDtos.size();i++){
                JSONObject temp=new JSONObject();
                temp.put("cysId",delationDtos.get(i).getCysId());
                temp.put("pointWeight",delationDtos.get(i).getMachNum());
                temp.put("carNum",delationDtos.get(i).getPointCarNum());
                array.put(temp);
            }
            jsonObject.put("list",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        view.stopResh();
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.biddFenpei(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listListener.successful();


            }
            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);
            }
        });
    }
}
