package com.saimawzc.shipper.presenter.order.sendcar;

import android.content.Context;

import com.saimawzc.shipper.modle.order.Imple.sendcar.ChangeCarModleImple;
import com.saimawzc.shipper.modle.order.Imple.sendcar.SendCarDelationModleImple;
import com.saimawzc.shipper.modle.order.model.sendar.ChangeCarModel;
import com.saimawzc.shipper.modle.order.model.sendar.SendCarDelationModel;
import com.saimawzc.shipper.view.order.sendcar.ChangeCarView;
import com.saimawzc.shipper.view.order.sendcar.SendCarDelationView;

public class SendCarDelationPresenter {

    private Context mContext;
    SendCarDelationModel model;
    SendCarDelationView view;

    public SendCarDelationPresenter(SendCarDelationView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SendCarDelationModleImple();
    }

    public void getData(String id){
        model.getSendCarDelation(view,id);
    }






}
