package com.saimawzc.shipper.weight.utils.listen.order;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderCarrierGroupDto;

import java.util.List;

public interface OrderCarriverSecondListener extends BaseListener {

    void back(List<OrderAssignmentSecondDto> dtos);
}
