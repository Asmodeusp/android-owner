package com.saimawzc.shipper.presenter.order.ordermange;

import android.content.Context;

import com.saimawzc.shipper.dto.order.manage.OrderManageDto;
import com.saimawzc.shipper.dto.order.manage.OrderManageRoleDto;
import com.saimawzc.shipper.modle.order.Imple.ordermanage.ManagwMapModelImple;
import com.saimawzc.shipper.modle.order.Imple.ordermanage.OrderManageModleImple;
import com.saimawzc.shipper.modle.order.model.ordermanage.ManagwMapModel;
import com.saimawzc.shipper.modle.order.model.ordermanage.OrderManageModel;
import com.saimawzc.shipper.view.order.manage.OrderManageMapView;
import com.saimawzc.shipper.view.order.manage.OrderManageView;
import com.saimawzc.shipper.weight.utils.listen.order.ordermanage.OrderManageListener;
import com.saimawzc.shipper.weight.utils.listen.order.ordermanage.OrderManageMapListener;

import java.util.List;

public class OrderManageMapPresenter implements OrderManageMapListener {

    private Context mContext;
    ManagwMapModel model;
    OrderManageMapView view;

    public OrderManageMapPresenter(OrderManageMapView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ManagwMapModelImple();
    }

    public void getcarrive(String id){
        model.getOrderManageList(view,this, id);
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
    public void back(OrderManageRoleDto dtos) {
        view.getList(dtos);
    }
}
