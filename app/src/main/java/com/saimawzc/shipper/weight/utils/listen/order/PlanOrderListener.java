package com.saimawzc.shipper.weight.utils.listen.order;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.OrderListDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 */

public interface PlanOrderListener extends BaseListener {
    void back(List<OrderListDto> dtos);


}
