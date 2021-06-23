package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.order.PlanOrderView;
import com.saimawzc.shipper.weight.utils.listen.order.PlanOrderListener;

public interface PlanOrderModel {
    void getOrderList(PlanOrderView view, PlanOrderListener listener,int page,
                      int type,String search,int status);

    void delete(PlanOrderView view, BaseListener listener, String id);

     //暂停运输
    void stopTransport(PlanOrderView view, BaseListener listener,
                       String id,int wayBillStatus,String reason);
}
