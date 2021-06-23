package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.view.order.ConsignCompanyView;
import com.saimawzc.shipper.weight.utils.listen.order.ConsignCompanyListener;

public interface ConsignSignCompanyModel {

    void getCompanyList(ConsignCompanyView view, ConsignCompanyListener listener);
}
