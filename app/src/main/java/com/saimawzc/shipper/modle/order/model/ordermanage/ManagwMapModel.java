package com.saimawzc.shipper.modle.order.model.ordermanage;

import com.saimawzc.shipper.view.order.manage.OrderManageMapView;
import com.saimawzc.shipper.weight.utils.listen.order.ordermanage.OrderManageMapListener;

public interface ManagwMapModel {
    void getOrderManageList(OrderManageMapView view,
                            OrderManageMapListener listener, String id);


}
