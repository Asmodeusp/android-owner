package com.saimawzc.shipper.modle.order.model.sendar;

import com.saimawzc.shipper.view.order.sendcar.LogisticsView;
import com.saimawzc.shipper.view.order.sendcar.SendCarDelationView;

/***
 * 审核
 * **/
public interface SendCarDelationModel {
    void getSendCarDelation(SendCarDelationView view, String id);


}
