package com.saimawzc.shipper.view.order;

import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.OrderListDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface PlanOrderView extends BaseView {

    void getOrderList(List<OrderListDto> orderListDtos);
    void stopResh();
    void isLastPage(Boolean isLastpage);
}
