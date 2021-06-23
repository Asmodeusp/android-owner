package com.saimawzc.shipper.modle.order.Imple.ordermanage;
import com.saimawzc.shipper.dto.order.manage.OrderManageRoleDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.ordermanage.ManagwMapModel;
import com.saimawzc.shipper.view.order.manage.OrderManageMapView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.ordermanage.OrderManageMapListener;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
public class ManagwMapModelImple extends BasEModeImple implements ManagwMapModel {
    @Override
    public void getOrderManageList(final OrderManageMapView view,
                                   final OrderManageMapListener listener,
                                   String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.getRuleList(body).enqueue(new CallBack<OrderManageRoleDto>() {
            @Override
            public void success(OrderManageRoleDto response) {
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


}
