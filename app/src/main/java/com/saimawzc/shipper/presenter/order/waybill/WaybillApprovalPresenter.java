package com.saimawzc.shipper.presenter.order.waybill;

import android.content.Context;

import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.modle.order.Imple.OrderApprovalModelImple;
import com.saimawzc.shipper.modle.order.Imple.waybill.WayBillApprovalModelImple;
import com.saimawzc.shipper.modle.order.model.OrderApprovalModel;
import com.saimawzc.shipper.modle.order.model.waybill.WayBillApprovalModel;
import com.saimawzc.shipper.view.order.OrderApprovalView;
import com.saimawzc.shipper.view.order.waybill.WaybillApprovalView;
import com.saimawzc.shipper.weight.utils.listen.order.OrderDelationListener;

/****
 * 订单审核
 * **/
public class WaybillApprovalPresenter implements OrderDelationListener {

    private Context mContext;
    WayBillApprovalModel model;
    WaybillApprovalView view;

    public WaybillApprovalPresenter(WaybillApprovalView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WayBillApprovalModelImple();
    }
    public void getpOrderDelation(String id){
        model.getOrderDelation(view,this,id);
    }
    public void getpOrderDelationyhr(String id){
        model.getOrderDelationYhr(view,this,id);
    }
    public  void approval(String id,int status){
        model.approval(view,this,id,status);
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
    public void back(OrderDelationDto dtos) {
        view.getOrderDelation(dtos);

    }
}
