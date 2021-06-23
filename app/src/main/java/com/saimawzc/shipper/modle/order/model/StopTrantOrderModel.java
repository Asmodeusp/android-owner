package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.BaseView;
import com.saimawzc.shipper.view.order.OrderBiddView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderBiddListener;

public interface StopTrantOrderModel {
    void stopTrantOrder(BaseView view,
                        BaseListener listener, String id,int wayBillStatus,String resson);
}
