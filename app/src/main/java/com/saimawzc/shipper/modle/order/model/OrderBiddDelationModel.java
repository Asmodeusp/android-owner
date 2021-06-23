package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.BiddingDelationDto;
import com.saimawzc.shipper.view.order.OrderBiddDelationView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListListener;

import java.util.List;

public interface OrderBiddDelationModel {

    void getOrderDelationList(OrderBiddDelationView view, OrderDelationListListener listListener,
                              int page,String id);

    void orderBidd(OrderBiddDelationView view, BaseListener listListener,
                   String id, List<BiddingDelationDto> delationDtos);
}
