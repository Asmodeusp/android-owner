package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.ui.order.OrderBasicInfoFragment;
import com.saimawzc.shipper.ui.order.OrderBasicInfoWayBillFragment;
import com.saimawzc.shipper.ui.order.OrderOptionalInfoFragment;
import com.saimawzc.shipper.view.BaseView;
import com.saimawzc.shipper.view.order.CreatOrderView;
import com.saimawzc.shipper.view.order.OrderBasicInfoView;
import com.saimawzc.shipper.view.order.OrderBasicWayBillInfoView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

public interface CreatOrderWayBillModel {

    void creatOrder(OrderBasicInfoWayBillFragment basicInfoFragment,
                    OrderOptionalInfoFragment optionalInfoFragment, BaseView view, BaseListener listener);

    void editOrder(OrderBasicInfoWayBillFragment basicInfoFragment,
                   OrderOptionalInfoFragment optionalInfoFragment, BaseView view, BaseListener listener, String id);

    void getOrderDelation(OrderBasicWayBillInfoView view, OrderDelationListener listener, String id);
    void getUntil(OrderBasicWayBillInfoView view);
}
