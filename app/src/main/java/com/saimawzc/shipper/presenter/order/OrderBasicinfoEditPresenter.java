package com.saimawzc.shipper.presenter.order;

import android.content.Context;

import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.modle.order.Imple.OrderApprovalModelImple;
import com.saimawzc.shipper.modle.order.Imple.OrderBasicInfoModelImple;
import com.saimawzc.shipper.modle.order.model.OrderApprovalModel;
import com.saimawzc.shipper.modle.order.model.OrderBasicInfoModel;
import com.saimawzc.shipper.view.order.OrderApprovalView;
import com.saimawzc.shipper.view.order.OrderBasicInfoView;
import com.saimawzc.shipper.view.order.OrderOptionalInfoView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

/****
 * 订单审核
 * **/
public class OrderBasicinfoEditPresenter implements OrderDelationListener {

    private Context mContext;
    OrderBasicInfoModel model;
    OrderBasicInfoView view;


    public OrderBasicinfoEditPresenter(OrderBasicInfoView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderBasicInfoModelImple();
    }




    public void getpOrderDelation(String id){
        model.getOrderDelation(view,this,id);
    }

    public void getConsuteDelation(String code,String type){
        model.getConsuteDelation(view,code,type);
    }

    public void getUntil(){
        model.getUntil(view);

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
