package com.saimawzc.shipper.presenter.order;

import android.content.Context;

import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.dto.order.bidd.BiddTempDto;
import com.saimawzc.shipper.modle.order.Imple.OrderBiddModelImple;
import com.saimawzc.shipper.modle.order.Imple.OrderCarriveSecondModelImple;
import com.saimawzc.shipper.modle.order.model.OrderBiddModel;
import com.saimawzc.shipper.modle.order.model.OrderCarriveSecondModel;
import com.saimawzc.shipper.view.order.OrderBiddView;
import com.saimawzc.shipper.view.order.OrderCarriveSecondView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderBiddListener;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverSecondListener;

import java.util.List;

public class OrderBiddPresenter implements OrderBiddListener {

    private Context mContext;
    OrderBiddModel model;
    OrderBiddView view;

    public OrderBiddPresenter(OrderBiddView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderBiddModelImple();
    }

    public void getcarrive(String id){
        model.getCarriveSecond(view,this,id);
    }

    public void addBidd(BiddTempDto tempDto,List<OrderAssignmentSecondDto>dtos){//新增竞价
        model.orderBidd(view,this,tempDto,dtos);
    }
    public void getCarType(){
        model.getCarType(view);
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
    public void back(List<OrderAssignmentSecondDto> dtos) {
        view.getCarriveList(dtos);
    }
}
