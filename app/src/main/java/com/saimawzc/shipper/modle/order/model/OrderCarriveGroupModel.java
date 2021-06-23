package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.order.OrderApprovalView;
import com.saimawzc.shipper.view.order.OrderCarriveGroupView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverGroupListener;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

/***
 * 审核
 * **/
public interface OrderCarriveGroupModel {
    void getCarriveGroup(OrderCarriveGroupView view, OrderCarriverGroupListener listener);
}
