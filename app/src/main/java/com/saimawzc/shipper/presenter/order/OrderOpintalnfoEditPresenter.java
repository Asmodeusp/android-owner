package com.saimawzc.shipper.presenter.order;

import android.content.Context;

import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.modle.order.Imple.OrderOptionalnfoModelImple;
import com.saimawzc.shipper.modle.order.model.OrderOpintalInfoModel;
import com.saimawzc.shipper.view.order.OrderOptionalInfoView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

/****
 * 订单审核
 * **/
public class OrderOpintalnfoEditPresenter implements OrderDelationListener {

    private Context mContext;
    OrderOpintalInfoModel model;
    OrderOptionalInfoView view;



    public OrderOpintalnfoEditPresenter(OrderOptionalInfoView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderOptionalnfoModelImple();
    }




    public void getpOrderDelation(String id,String type){
        model.getOrderDelation(view,this,id,type);
    }
    public void getConsuteDelation(String code,String type){
        model.getConsuteDelation(view,code,type);
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
