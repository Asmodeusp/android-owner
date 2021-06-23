package com.saimawzc.shipper.presenter.order.waybill;

import android.content.Context;

import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.modle.order.Imple.OrderBasicInfoModelImple;
import com.saimawzc.shipper.modle.order.Imple.OrderCreatWayBillModelImple;
import com.saimawzc.shipper.modle.order.model.CreatOrderWayBillModel;
import com.saimawzc.shipper.modle.order.model.OrderBasicInfoModel;
import com.saimawzc.shipper.view.order.OrderBasicInfoView;
import com.saimawzc.shipper.view.order.OrderBasicWayBillInfoView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

/****
 *
 * **/
public class OrderBasicinfoWayBillEditPresenter implements OrderDelationListener {

    private Context mContext;
    CreatOrderWayBillModel model;
    OrderBasicWayBillInfoView view;


    public OrderBasicinfoWayBillEditPresenter(OrderBasicWayBillInfoView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderCreatWayBillModelImple();
    }

    public void getUntil(){
        model.getUntil(view);

    }


    public void getpOrderDelation(String id){
        model.getOrderDelation(view,this,id);
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
