package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.order.OrderApprovalView;
import com.saimawzc.shipper.view.order.OrderBasicInfoView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

/***
 * 审核
 * **/
public interface OrderBasicInfoModel {
    void getOrderDelation(OrderBasicInfoView view, OrderDelationListener listener, String id);
    void getUntil(OrderBasicInfoView view);

    void getConsuteDelation(OrderBasicInfoView view, String code,String type);
}
