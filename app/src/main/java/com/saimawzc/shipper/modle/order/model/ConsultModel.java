package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.view.order.ConsultView;
/***
 *
 * **/
public interface ConsultModel {
    void getConsult(ConsultView view,String businessType, int page, String searchType,String searchValue);


}
