package com.saimawzc.shipper.presenter.order.sendcar;

import android.content.Context;

import com.saimawzc.shipper.modle.order.Imple.sendcar.WarnInfoModelImple;
import com.saimawzc.shipper.modle.order.model.sendar.WarnInfoModel;
import com.saimawzc.shipper.view.order.sendcar.WarnInfoView;


/**
 * Created by Administrator on 2020/7/30.
 */

public class WarnInfoPresenter {

    private Context mContext;
    WarnInfoView view;
    WarnInfoModel model;
    public WarnInfoPresenter(WarnInfoView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WarnInfoModelImple() ;
    }

    public void getData(String id,String type){
        model.getData(view,id,type);
    }



}
