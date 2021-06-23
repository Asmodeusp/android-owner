package com.saimawzc.shipper.presenter.order;

import android.content.Context;


import com.saimawzc.shipper.dto.order.OrderBiddenHistoryDto;
import com.saimawzc.shipper.modle.order.Imple.OrderBiddHistoryModelImple;
import com.saimawzc.shipper.modle.order.model.OrderBiddHistoryModel;
import com.saimawzc.shipper.view.order.OrderBiddHistoryView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderBiddHistoryListener;

import java.util.List;

public class OrderBiddHistoryPresenter implements OrderBiddHistoryListener {

    private Context mContext;
    OrderBiddHistoryModel model;
    OrderBiddHistoryView view;

    public OrderBiddHistoryPresenter(OrderBiddHistoryView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderBiddHistoryModelImple();
    }

    public void getcarrive(String id,String cysId){
        model.getOrderList(view,this,id,cysId);
    }




    @Override
    public void successful() {
        view.oncomplete();
    }

    @Override
    public void onFail(String str) {
    view.Toast(str);
    }
    @Override
    public void successful(int type) {

    }


    @Override
    public void back(List<OrderBiddenHistoryDto> dtos) {
        view.getBiddHistory(dtos);
    }
}
