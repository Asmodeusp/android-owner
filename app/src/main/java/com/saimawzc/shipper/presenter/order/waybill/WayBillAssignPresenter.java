package com.saimawzc.shipper.presenter.order.waybill;

import android.content.Context;
import android.text.TextUtils;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.wallbill.WayBillAssignDto;
import com.saimawzc.shipper.modle.order.Imple.OrderCreatWayBillModelImple;
import com.saimawzc.shipper.modle.order.Imple.waybill.WayBillAssignModelImple;
import com.saimawzc.shipper.modle.order.model.CreatOrderWayBillModel;
import com.saimawzc.shipper.modle.order.model.waybill.WayBillAssignModel;
import com.saimawzc.shipper.ui.order.OrderBasicInfoWayBillFragment;
import com.saimawzc.shipper.ui.order.OrderOptionalInfoFragment;
import com.saimawzc.shipper.view.BaseView;
import com.saimawzc.shipper.view.order.waybill.WayBillAssignView;
import com.saimawzc.shipper.weight.utils.listen.order.waybill.WaybillAssignListListener;

import java.util.List;

public class WayBillAssignPresenter implements WaybillAssignListListener {

    private Context mContext;
    WayBillAssignModel model;
    WayBillAssignView view;

    public WayBillAssignPresenter(WayBillAssignView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WayBillAssignModelImple() ;
    }

    public void getWayBillList(int page,String phone){
        model.getWayBillAssign(view,this,page,phone);
    }

    public void wayVillAssgin(WayBillAssignDto.waybillData data,String id,String price,String type){
        model.wayBillZp(view,this,data,id,price, type);
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
    public void back(List<WayBillAssignDto.waybillData> dtos) {
        view.getWayBillAssign(dtos);

    }
}
