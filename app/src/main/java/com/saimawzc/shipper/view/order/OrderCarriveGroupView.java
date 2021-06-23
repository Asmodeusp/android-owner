package com.saimawzc.shipper.view.order;

import com.saimawzc.shipper.dto.order.creatorder.OrderCarrierGroupDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

/***
 * 承运商分组
 * */
public interface OrderCarriveGroupView extends BaseView {

    void getCarriveList(List<OrderCarrierGroupDto> dtos);
    void stopResh();
}
