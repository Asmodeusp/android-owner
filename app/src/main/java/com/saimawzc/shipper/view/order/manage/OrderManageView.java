package com.saimawzc.shipper.view.order.manage;

import com.saimawzc.shipper.dto.order.manage.OrderManageDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface OrderManageView extends BaseView {

    void stopResh();
    void isLastPage(boolean islastpage);
    void getOrderManageList(List<OrderManageDto.manageData> dtos);
}
