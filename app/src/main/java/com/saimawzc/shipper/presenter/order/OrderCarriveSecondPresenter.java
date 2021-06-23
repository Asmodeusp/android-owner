package com.saimawzc.shipper.presenter.order;

import android.content.Context;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.modle.order.Imple.OrderCarriveSecondModelImple;
import com.saimawzc.shipper.modle.order.model.OrderCarriveSecondModel;
import com.saimawzc.shipper.view.order.OrderCarriveSecondView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderCarriverSecondListener;

import java.util.List;

public class OrderCarriveSecondPresenter implements OrderCarriverSecondListener {

    private Context mContext;
    OrderCarriveSecondModel model;
    OrderCarriveSecondView view;

    public OrderCarriveSecondPresenter(OrderCarriveSecondView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderCarriveSecondModelImple();
    }

    public void getcarrive(String id){
        model.getCarriveSecond(view,this,id);
    }

    public  void orderZp(List<OrderAssignmentSecondDto>dtos,String id){
        model.orderZp(view,this,dtos,id);
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
    public void back(List<OrderAssignmentSecondDto> dtos) {
        view.getCarriveList(dtos);
    }
}
