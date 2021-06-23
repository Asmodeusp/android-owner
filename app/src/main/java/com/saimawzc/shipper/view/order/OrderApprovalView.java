package com.saimawzc.shipper.view.order;

import android.view.View;

import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.view.BaseView;

/***
 * 订单审核
 * */
public interface OrderApprovalView extends BaseView {

    void getOrderDelation(OrderDelationDto dto);
}
