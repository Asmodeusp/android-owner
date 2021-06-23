package com.saimawzc.shipper.presenter.order;

import android.content.Context;

import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.OrderListDto;
import com.saimawzc.shipper.modle.order.Imple.ConsignCompanyModelImple;
import com.saimawzc.shipper.modle.order.Imple.PlanOrderModelImple;
import com.saimawzc.shipper.modle.order.model.ConsignSignCompanyModel;
import com.saimawzc.shipper.modle.order.model.PlanOrderModel;
import com.saimawzc.shipper.view.order.ConsignCompanyView;
import com.saimawzc.shipper.view.order.PlanOrderView;
import com.saimawzc.shipper.weight.utils.listen.order.ConsignCompanyListener;
import com.saimawzc.shipper.weight.utils.listen.order.PlanOrderListener;

import java.util.List;

public class PlanOrderPresenter implements PlanOrderListener {

    private Context mContext;
    PlanOrderModel model;
    PlanOrderView view;
    public PlanOrderPresenter(PlanOrderView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new PlanOrderModelImple();
    }
    public void getplanOrder(int page,int type,String search,int status){
        model.getOrderList(view,this,page,type,search,status);
    }
    public void deleteOrder(String Id){
        model.delete(view,this,Id);
    }
    public void stopTrant(String id,int wayBillStatus,String reason){
        model.stopTransport(view,this,id,wayBillStatus,reason);
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
        if(type==1){//删除
            view.oncomplete();
        }
    }
    @Override
    public void back(List<OrderListDto> dtos) {
        view.getOrderList(dtos);
    }
}
