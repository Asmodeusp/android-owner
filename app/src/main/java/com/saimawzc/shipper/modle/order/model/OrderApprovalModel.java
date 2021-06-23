package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.order.OrderApprovalView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

/***
 * 审核
 * **/
public interface OrderApprovalModel {
    void approval(OrderApprovalView view, BaseListener listener,String id,int status);
    void getOrderDelation(OrderApprovalView view, OrderDelationListener listener, String id);
}
