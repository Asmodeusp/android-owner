package com.saimawzc.shipper.modle.order.model.waybill;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.order.OrderApprovalView;
import com.saimawzc.shipper.view.order.waybill.WaybillApprovalView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

/***
 * 审核
 * **/
public interface WayBillApprovalModel {
    void approval(WaybillApprovalView view, BaseListener listener, String id, int status);
    void getOrderDelation(WaybillApprovalView view, OrderDelationListener listener, String id);
    void getOrderDelationYhr(WaybillApprovalView view, OrderDelationListener listener, String id);
}
