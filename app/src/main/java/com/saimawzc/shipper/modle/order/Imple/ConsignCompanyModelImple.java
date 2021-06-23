package com.saimawzc.shipper.modle.order.Imple;

import com.baidu.mapapi.map.MapView;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.ConsignSignCompanyModel;
import com.saimawzc.shipper.view.order.ConsignCompanyView;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.listen.order.ConsignCompanyListener;

import java.util.List;

public class ConsignCompanyModelImple extends BasEModeImple implements ConsignSignCompanyModel {

    @Override
    public void getCompanyList(final ConsignCompanyView view, final ConsignCompanyListener listener) {
        view.showLoading();
        orderApi.getConsignmentCompanyList().enqueue(new CallBack<List<ConsignmentCompanyDto>>() {
            @Override
            public void success(List<ConsignmentCompanyDto> response) {
                view.dissLoading();
                listener.back(response);
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


}
