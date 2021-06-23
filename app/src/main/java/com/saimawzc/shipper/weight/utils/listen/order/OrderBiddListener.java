package com.saimawzc.shipper.weight.utils.listen.order;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;

import java.util.List;

public interface OrderBiddListener extends BaseListener {

    void back(List<OrderAssignmentSecondDto> dtos);
}
