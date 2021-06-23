package com.saimawzc.shipper.presenter.order.waybill;

import android.content.Context;

import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.dto.order.wallbill.OrderWayBillDto;
import com.saimawzc.shipper.modle.order.Imple.AssignDelationModleImple;
import com.saimawzc.shipper.modle.order.Imple.waybill.WayBillListModleImple;
import com.saimawzc.shipper.modle.order.model.AssignDelationModel;
import com.saimawzc.shipper.modle.order.model.waybill.WayBillListModel;
import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.view.order.waybill.WayBillListView;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;
import com.saimawzc.shipper.weight.utils.listen.order.waybill.WayBillListListener;

import java.util.List;

public class WayBillListPresenter implements WayBillListListener {

    private Context mContext;
    WayBillListModel model;
    WayBillListView view;

    public WayBillListPresenter(WayBillListView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WayBillListModleImple();
    }

    public void getcarrive(int page,String search,int status){
        model.getWayBillList(view,this,page,search,status);
    }
    /**删除**/
    public  void delete(String id){
        model.delete(view,this,id);

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
    public void back(List<OrderWayBillDto.waybillData> dtos) {
        view.getWayBillList(dtos);
    }
}
