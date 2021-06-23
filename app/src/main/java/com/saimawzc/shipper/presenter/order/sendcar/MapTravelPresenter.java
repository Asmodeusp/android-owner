package com.saimawzc.shipper.presenter.order.sendcar;

import android.content.Context;

import com.saimawzc.shipper.modle.order.Imple.sendcar.MapTravelModelImple;
import com.saimawzc.shipper.modle.order.model.sendar.MapTravelModel;
import com.saimawzc.shipper.view.order.sendcar.MapTravelView;


/**
 * Created by Administrator on 2020/7/30.
 */

public class MapTravelPresenter {

    private Context mContext;
    MapTravelView view;
    MapTravelModel model;

    public MapTravelPresenter(MapTravelView view, Context context) {
        this.mContext=context;
        this.view=view;
        model=new MapTravelModelImple() ;
    }
    public void getData(String id){
        model.roulete(view,id);
    }
}
