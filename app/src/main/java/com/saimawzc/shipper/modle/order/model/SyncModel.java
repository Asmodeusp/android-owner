package com.saimawzc.shipper.modle.order.model;


import com.saimawzc.shipper.view.order.SyncView;

/***
 * 审核
 * **/
public interface SyncModel {
    void sync(SyncView view, String startTime, String endTime, String type);


}
