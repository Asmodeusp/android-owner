package com.saimawzc.shipper.presenter.order.sendcar;

import android.content.Context;

import com.saimawzc.shipper.modle.order.Imple.sendcar.ChangeCarModleImple;
import com.saimawzc.shipper.modle.order.Imple.sendcar.LogisticsModleImple;
import com.saimawzc.shipper.modle.order.model.sendar.ChangeCarModel;
import com.saimawzc.shipper.modle.order.model.sendar.LogisticsModel;
import com.saimawzc.shipper.view.order.sendcar.ChangeCarView;
import com.saimawzc.shipper.view.order.sendcar.LogisticsView;

public class ChangeCarPresenter {

    private Context mContext;
    ChangeCarModel model;
    ChangeCarView view;

    public ChangeCarPresenter(ChangeCarView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ChangeCarModleImple();
    }

    public void getData(String id){
        model.getLogistics(view,id);
    }






}
