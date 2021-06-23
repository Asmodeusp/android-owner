package com.saimawzc.shipper.presenter.order;

import android.content.Context;

import com.saimawzc.shipper.dto.order.OrderListDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.modle.order.Imple.OrderApprovalModelImple;
import com.saimawzc.shipper.modle.order.Imple.PlanOrderModelImple;
import com.saimawzc.shipper.modle.order.model.OrderApprovalModel;
import com.saimawzc.shipper.modle.order.model.PlanOrderModel;
import com.saimawzc.shipper.view.order.OrderApprovalView;
import com.saimawzc.shipper.view.order.PlanOrderView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;
import com.saimawzc.shipper.weight.utils.listen.order.PlanOrderListener;

import java.util.List;
/****
 * 订单审核
 * **/
public class OrderApprovalPresenter implements OrderDelationListener {

    private Context mContext;
    OrderApprovalModel model;
    OrderApprovalView view;

    public OrderApprovalPresenter(OrderApprovalView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderApprovalModelImple();
    }
    public void getpOrderDelation(String id){
        model.getOrderDelation(view,this,id);
    }

    public  void approval(String id,int status){
        model.approval(view,this,id,status);
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
    public void back(OrderDelationDto dtos) {
        view.getOrderDelation(dtos);

    }
}
