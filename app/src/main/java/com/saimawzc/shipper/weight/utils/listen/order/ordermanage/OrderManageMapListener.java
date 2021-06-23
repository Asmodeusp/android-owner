package com.saimawzc.shipper.weight.utils.listen.order.ordermanage;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.manage.OrderManageDto;
import com.saimawzc.shipper.dto.order.manage.OrderManageRoleDto;

import java.util.List;

public interface OrderManageMapListener extends BaseListener {

    void back(OrderManageRoleDto dtos);
}
