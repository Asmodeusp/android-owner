package com.saimawzc.shipper.view.order;

import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface AssignDelationView extends BaseView {

    void getCarriveList(List<AssignDelationDto.listdata> dtos);
    void stopResh();
    void isLast(boolean islast);
}
