package com.saimawzc.shipper.presenter.order.ordermange;

import android.content.Context;

import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.dto.order.manage.OrderManageDto;
import com.saimawzc.shipper.modle.order.Imple.AssignDelationModleImple;
import com.saimawzc.shipper.modle.order.Imple.ordermanage.OrderManageModleImple;
import com.saimawzc.shipper.modle.order.model.AssignDelationModel;
import com.saimawzc.shipper.modle.order.model.ordermanage.OrderManageModel;
import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.view.order.manage.OrderManageView;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;
import com.saimawzc.shipper.weight.utils.listen.order.ordermanage.OrderManageListener;

import java.util.List;

public class OrderManagePresenter implements OrderManageListener {

    private Context mContext;
    OrderManageModel model;
    OrderManageView view;

    public OrderManagePresenter(OrderManageView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderManageModleImple();
    }

    public void getcarrive(int page,String search){
        model.getOrderManageList(view,this,page, search);
    }

    public void  delete(String id){
        model.delete(view,this,id);

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
    public void back(List<OrderManageDto.manageData> dtos) {
        view.getOrderManageList(dtos);
    }
}
