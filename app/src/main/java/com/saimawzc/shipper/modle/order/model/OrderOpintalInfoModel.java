package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.view.order.OrderBasicInfoView;
import com.saimawzc.shipper.view.order.OrderOptionalInfoView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

/***
 * 审核
 * **/
public interface OrderOpintalInfoModel {
    void getOrderDelation(OrderOptionalInfoView view,
                          OrderDelationListener listener, String id,String type);


    void getConsuteDelation(OrderOptionalInfoView view, String code,String type);

}
