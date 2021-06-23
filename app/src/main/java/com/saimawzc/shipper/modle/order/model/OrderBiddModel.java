package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.dto.order.bidd.BiddTempDto;
import com.saimawzc.shipper.view.order.OrderBiddView;
import com.saimawzc.shipper.view.order.OrderCarriveSecondView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderBiddListener;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverSecondListener;

import java.util.List;

/***
 * 审核
 * **/
public interface OrderBiddModel {
    void getCarriveSecond(OrderBiddView view,
                          OrderBiddListener listener, String id);
    void getCarType( OrderBiddView view);
    void orderBidd(OrderBiddView view,
                   OrderBiddListener listener, BiddTempDto tempDto,
                   List<OrderAssignmentSecondDto>dtos);
}
