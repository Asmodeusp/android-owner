package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.view.order.OrderCarriveSecondView;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverSecondListener;

import java.util.List;

/***
 * 审核
 * **/
public interface AssignDelationModel {
    void getAssignDelationList(AssignDelationView view,
                               AssignDelationListener listener, String id,int page, String type);


}
