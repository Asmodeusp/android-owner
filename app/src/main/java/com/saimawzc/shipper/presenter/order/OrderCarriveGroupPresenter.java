package com.saimawzc.shipper.presenter.order;

import android.content.Context;

import com.saimawzc.shipper.dto.order.OrderListDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderCarrierGroupDto;
import com.saimawzc.shipper.modle.order.Imple.OrderCarriveGroupModelImple;
import com.saimawzc.shipper.modle.order.Imple.PlanOrderModelImple;
import com.saimawzc.shipper.modle.order.model.OrderCarriveGroupModel;
import com.saimawzc.shipper.modle.order.model.PlanOrderModel;
import com.saimawzc.shipper.view.order.OrderCarriveGroupView;
import com.saimawzc.shipper.view.order.PlanOrderView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverGroupListener;
import com.saimawzc.shipper.weight.utils.listen.order.PlanOrderListener;

import java.util.List;

public class OrderCarriveGroupPresenter implements OrderCarriverGroupListener {

    private Context mContext;
    OrderCarriveGroupModel model;
    OrderCarriveGroupView view;

    public OrderCarriveGroupPresenter(OrderCarriveGroupView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderCarriveGroupModelImple();
    }
    public void getcarrive(){
        model.getCarriveGroup(view,this);
    }

    @Override
    public void successful() {
    }

    @Override
    public void onFail(String str) {
    view.Toast(str);
    }
    @Override
    public void successful(int type) {

    }

    @Override
    public void back(List<OrderCarrierGroupDto> dtos) {
        view.getCarriveList(dtos);
    }
}
