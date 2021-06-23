package com.saimawzc.shipper.modle.order.model.ordermanage;

import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.view.order.manage.OrderManageView;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;
import com.saimawzc.shipper.weight.utils.listen.order.ordermanage.OrderManageListener;

public interface OrderManageModel {
    void getOrderManageList(OrderManageView view,
                            OrderManageListener listener, int page,String search);

    void delete(OrderManageView view,
                            OrderManageListener listener, String id);
}
