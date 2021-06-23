package com.saimawzc.shipper.modle.bidd.model;

import com.saimawzc.shipper.view.bidd.PlanBiddView;
import com.saimawzc.shipper.weight.utils.listen.order.bidd.PlanBiddListener;

public interface PlanBiddModel {

    void getBiddLsit(PlanBiddView view, PlanBiddListener listener, String type,int page,String searchType
    ,String searchValue);

}
