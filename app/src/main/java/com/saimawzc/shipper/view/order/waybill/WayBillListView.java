package com.saimawzc.shipper.view.order.waybill;

import com.saimawzc.shipper.dto.order.wallbill.OrderWayBillDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface WayBillListView extends BaseView {

    void getWayBillList(List<OrderWayBillDto.waybillData> dtos);
    void stopResh();
    void isLastPage(boolean islastpage);
}
