package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.view.order.OrderCarriveGroupView;
import com.saimawzc.shipper.view.order.OrderCarriveSecondView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverGroupListener;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverSecondListener;

import java.util.List;

/***
 * 审核
 * **/
public interface OrderCarriveSecondModel {
    void getCarriveSecond(OrderCarriveSecondView view,
                          OrderCarriverSecondListener listener,String id);

    void orderZp(OrderCarriveSecondView view,
                 OrderCarriverSecondListener listener, List<OrderAssignmentSecondDto> dtos,String id);
}
