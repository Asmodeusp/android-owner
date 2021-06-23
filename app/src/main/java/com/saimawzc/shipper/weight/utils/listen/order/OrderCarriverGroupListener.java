package com.saimawzc.shipper.weight.utils.listen.order;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.creatorder.OrderCarrierGroupDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;

import java.util.List;

public interface OrderCarriverGroupListener extends BaseListener {

    void back(List<OrderCarrierGroupDto> dtos);
}
