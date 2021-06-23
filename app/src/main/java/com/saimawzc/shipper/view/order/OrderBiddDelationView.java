package com.saimawzc.shipper.view.order;

import com.saimawzc.shipper.dto.order.BiddingDelationDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

/***
 * 竞价详情
 * **/
public interface OrderBiddDelationView extends BaseView {

    void getOrderBiddLsit(List<BiddingDelationDto> dtos);
    void stopResh();
    void isLastPage(boolean isLastPage);

}
