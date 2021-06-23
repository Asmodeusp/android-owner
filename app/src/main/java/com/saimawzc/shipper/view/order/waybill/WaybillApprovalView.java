package com.saimawzc.shipper.view.order.waybill;

import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.view.BaseView;

/***
 * 订单审核
 * */
public interface WaybillApprovalView extends BaseView {

    void getOrderDelation(OrderDelationDto dto);
}
