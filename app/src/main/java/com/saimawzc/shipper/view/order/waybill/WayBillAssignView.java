package com.saimawzc.shipper.view.order.waybill;

import com.saimawzc.shipper.dto.order.wallbill.WayBillAssignDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface WayBillAssignView extends BaseView {
    void getWayBillAssign(List<WayBillAssignDto.waybillData>dtos);
    void stopResh();
    void isLastPage(boolean isLastpage);
}
