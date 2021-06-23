package com.saimawzc.shipper.modle.order.model.waybill;

import com.saimawzc.shipper.view.order.waybill.WayBillInventoryView;
import com.saimawzc.shipper.view.order.waybill.WayBillListView;
import com.saimawzc.shipper.weight.utils.listen.order.waybill.WayBillInventoryListener;
import com.saimawzc.shipper.weight.utils.listen.order.waybill.WayBillListListener;

/***
 * 预运单清单
 * **/
public interface WayBillInventoryModel {
    void getWayBillList(WayBillInventoryView view, WayBillInventoryListener listListener,String id);

}
