package com.saimawzc.shipper.weight.utils.listen.order.waybill;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.dto.order.wallbill.OrderWayBillDto;

import java.util.List;

public interface WayBillListListener extends BaseListener {

    void back(List<OrderWayBillDto.waybillData> dtos);
}
