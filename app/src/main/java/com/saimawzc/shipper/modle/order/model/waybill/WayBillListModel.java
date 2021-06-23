package com.saimawzc.shipper.modle.order.model.waybill;

import com.saimawzc.shipper.view.order.waybill.WayBillListView;
import com.saimawzc.shipper.weight.utils.listen.order.waybill.WayBillListListener;

/****
 * 预运单列表
 * */
public interface WayBillListModel {
    void getWayBillList(WayBillListView view, WayBillListListener listListener,int page,String search,int status);

    void delete(WayBillListView view, WayBillListListener listListener,String id);
}
