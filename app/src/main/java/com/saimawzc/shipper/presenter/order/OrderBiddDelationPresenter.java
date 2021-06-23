package com.saimawzc.shipper.presenter.order;

import android.content.Context;

import com.saimawzc.shipper.dto.order.BiddingDelationDto;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.modle.order.Imple.OrderBiddDelationModelImple;
import com.saimawzc.shipper.modle.order.Imple.OrderCarriveSecondModelImple;
import com.saimawzc.shipper.modle.order.model.OrderBiddDelationModel;
import com.saimawzc.shipper.modle.order.model.OrderCarriveSecondModel;
import com.saimawzc.shipper.view.order.OrderBiddDelationView;
import com.saimawzc.shipper.view.order.OrderCarriveSecondView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderBiddListener;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverSecondListener;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListListener;

import java.util.List;

public class OrderBiddDelationPresenter implements OrderDelationListListener {

    private Context mContext;
    OrderBiddDelationModel model;
    OrderBiddDelationView view;

    public OrderBiddDelationPresenter(OrderBiddDelationView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderBiddDelationModelImple();
    }

    public void getcarrive(int page,String id){
        model.getOrderDelationList(view,this,page,id);
    }

    public void order(String id,List<BiddingDelationDto>delationDtos){
        model.orderBidd(view,this,id,delationDtos);
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
    public void back(List<BiddingDelationDto> dtos) {
        view.getOrderBiddLsit(dtos);
    }
}
