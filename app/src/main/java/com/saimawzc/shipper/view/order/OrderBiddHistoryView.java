package com.saimawzc.shipper.view.order;


import com.saimawzc.shipper.dto.order.OrderBiddenHistoryDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface OrderBiddHistoryView extends BaseView {
    void getBiddHistory(List<OrderBiddenHistoryDto> dtos);
}
