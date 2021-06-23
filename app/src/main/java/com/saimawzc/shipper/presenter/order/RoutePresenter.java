package com.saimawzc.shipper.presenter.order;

import android.content.Context;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.modle.order.Imple.RouteModleImple;
import com.saimawzc.shipper.modle.order.model.RouteModel;
import com.saimawzc.shipper.view.order.route.RouteView;


import java.util.List;

public class RoutePresenter  {

    private Context mContext;
    RouteModel model;
    RouteView view;

    public RoutePresenter(RouteView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new RouteModleImple();
    }

    public void getRoute(String routeName){
        model.getRoute(view,routeName);
    }

}
