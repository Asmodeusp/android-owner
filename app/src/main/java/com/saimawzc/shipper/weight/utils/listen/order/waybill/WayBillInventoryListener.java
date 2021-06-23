package com.saimawzc.shipper.weight.utils.listen.order.waybill;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.wallbill.OrderInventoryDto;
import com.saimawzc.shipper.dto.order.wallbill.OrderWayBillDto;

import java.util.List;

public interface WayBillInventoryListener extends BaseListener {

    void back(List<OrderInventoryDto.qdData> dtos);
}
