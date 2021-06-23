package com.saimawzc.shipper.weight.utils.listen.order;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;

import java.util.List;

public interface OrderDelationListener extends BaseListener {

    void back(OrderDelationDto dtos);
}
