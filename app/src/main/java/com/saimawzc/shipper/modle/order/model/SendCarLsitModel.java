package com.saimawzc.shipper.modle.order.model;


import com.saimawzc.shipper.view.order.SendCarListView;
import com.saimawzc.shipper.weight.utils.listen.order.SendCarListListener;

public interface SendCarLsitModel {

    void getSendCarLsit(SendCarListView view, SendCarListListener listener,
                        int page, String type,String searchType,String searchValue);

    void sign(SendCarListView view, String id , String num);

    void getsignWeight(SendCarListView view, String id );
}
