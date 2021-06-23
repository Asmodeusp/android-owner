package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.view.order.OrderBiddHistoryView;
import com.saimawzc.shipper.view.order.PlanOrderView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderBiddHistoryListener;
import com.saimawzc.shipper.weight.utils.listen.order.PlanOrderListener;

public interface OrderBiddHistoryModel {
    void getOrderList(OrderBiddHistoryView view, OrderBiddHistoryListener listener,
                      String id,  String cysId);
}
