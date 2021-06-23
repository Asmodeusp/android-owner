package com.saimawzc.shipper.view.order;

import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface OrderCarriveSecondView extends BaseView {

    void getCarriveList(List<OrderAssignmentSecondDto> dtos);
    void stopResh();
}
