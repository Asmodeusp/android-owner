package com.saimawzc.shipper.presenter.order.sendcar;

import android.content.Context;

import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.modle.order.Imple.AssignDelationModleImple;
import com.saimawzc.shipper.modle.order.Imple.sendcar.LogisticsModleImple;
import com.saimawzc.shipper.modle.order.model.AssignDelationModel;
import com.saimawzc.shipper.modle.order.model.sendar.LogisticsModel;
import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.view.order.sendcar.LogisticsView;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;

import java.util.List;

public class LogistiscPresenter  {

    private Context mContext;
    LogisticsModel model;
    LogisticsView view;

    public LogistiscPresenter(LogisticsView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new LogisticsModleImple();
    }

    public void getcarrive(String id){
        model.getLogistics(view,id);
    }






}
