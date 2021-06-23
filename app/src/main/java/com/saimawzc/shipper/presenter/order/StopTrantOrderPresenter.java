package com.saimawzc.shipper.presenter.order;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.modle.order.Imple.OrderCarriveSecondModelImple;
import com.saimawzc.shipper.modle.order.Imple.StopTrantOderModelImple;
import com.saimawzc.shipper.modle.order.model.OrderCarriveSecondModel;
import com.saimawzc.shipper.modle.order.model.StopTrantOrderModel;
import com.saimawzc.shipper.view.BaseView;
import com.saimawzc.shipper.view.order.OrderCarriveSecondView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverSecondListener;

import java.util.List;

public class StopTrantOrderPresenter implements BaseListener {

    private Context mContext;
    StopTrantOrderModel model;
    BaseView view;

    public StopTrantOrderPresenter(BaseView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new StopTrantOderModelImple();
    }



    public  void stopTrantOrder(String id,int waybillstatus,String reason){
        model.stopTrantOrder(view,this,id,waybillstatus,reason);
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


}
