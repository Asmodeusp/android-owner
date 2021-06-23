package com.saimawzc.shipper.weight.utils.listen.order;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;

import java.util.List;

public interface AssignDelationListener extends BaseListener {

    void back(List<AssignDelationDto.listdata> dtos);
}
