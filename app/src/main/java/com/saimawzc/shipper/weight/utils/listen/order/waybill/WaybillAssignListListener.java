package com.saimawzc.shipper.weight.utils.listen.order.waybill;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.wallbill.OrderWayBillDto;
import com.saimawzc.shipper.dto.order.wallbill.WayBillAssignDto;

import java.util.List;

public interface WaybillAssignListListener extends BaseListener {

    void back(List<WayBillAssignDto.waybillData> dtos);
}
