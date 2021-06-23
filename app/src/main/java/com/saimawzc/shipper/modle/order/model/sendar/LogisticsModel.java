package com.saimawzc.shipper.modle.order.model.sendar;

import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.view.order.sendcar.LogisticsView;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;

/***
 * 审核
 * **/
public interface LogisticsModel {
    void getLogistics(LogisticsView view, String id);


}
