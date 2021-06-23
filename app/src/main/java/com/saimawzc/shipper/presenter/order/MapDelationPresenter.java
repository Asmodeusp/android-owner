package com.saimawzc.shipper.presenter.order;

import android.content.Context;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.modle.order.Imple.MapDelationModleImple;
import com.saimawzc.shipper.modle.order.model.MapDelationModel;
import com.saimawzc.shipper.view.order.route.MapDealtionView;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;
import java.util.List;

public class MapDelationPresenter {

    private Context mContext;
    MapDelationModel model;
    MapDealtionView view;

    public MapDelationPresenter(MapDealtionView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MapDelationModleImple();
    }

    public void getMapDelation(String id){
        model.getMapDelation(view,id);
    }

}
