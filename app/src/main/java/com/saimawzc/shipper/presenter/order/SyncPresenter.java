package com.saimawzc.shipper.presenter.order;

import android.content.Context;

import com.saimawzc.shipper.modle.order.Imple.SyncModleImple;
import com.saimawzc.shipper.modle.order.model.SyncModel;
import com.saimawzc.shipper.view.order.SyncView;


import java.util.List;

public class SyncPresenter {

    private Context mContext;
    SyncModel model;
    SyncView view;

    public SyncPresenter(SyncView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SyncModleImple();
    }

    public void sync(String startTme,String endTime,String type){
        model.sync(view,startTme,endTime,type);
    }

}
