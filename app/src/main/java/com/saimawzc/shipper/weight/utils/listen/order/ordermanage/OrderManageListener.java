package com.saimawzc.shipper.weight.utils.listen.order.ordermanage;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.dto.order.manage.OrderManageDto;

import java.util.List;

public interface OrderManageListener extends BaseListener {

    void back(List<OrderManageDto.manageData> dtos);
}
