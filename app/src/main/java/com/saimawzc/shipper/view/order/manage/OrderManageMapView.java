package com.saimawzc.shipper.view.order.manage;

import com.saimawzc.shipper.dto.order.manage.OrderManageRoleDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface OrderManageMapView extends BaseView {
    void getList(OrderManageRoleDto  dtos);
}
