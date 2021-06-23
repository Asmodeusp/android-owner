package com.saimawzc.shipper.presenter.order.waybill;

import android.content.Context;
import android.text.TextUtils;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.wallbill.OrderInventoryDto;
import com.saimawzc.shipper.modle.order.Imple.OrderCreatWayBillModelImple;
import com.saimawzc.shipper.modle.order.Imple.waybill.WayBillInventoryModelImple;
import com.saimawzc.shipper.modle.order.model.CreatOrderWayBillModel;
import com.saimawzc.shipper.modle.order.model.waybill.WayBillInventoryModel;
import com.saimawzc.shipper.ui.order.OrderBasicInfoWayBillFragment;
import com.saimawzc.shipper.ui.order.OrderOptionalInfoFragment;
import com.saimawzc.shipper.view.BaseView;
import com.saimawzc.shipper.view.order.waybill.WayBillInventoryView;
import com.saimawzc.shipper.weight.utils.listen.order.waybill.WayBillInventoryListener;

import java.util.List;

/**
 * 预运单清单
 * **/
public class WayBillInventoryPresenter implements WayBillInventoryListener {


    private Context mContext;
    WayBillInventoryModel model;
    WayBillInventoryView view;

    public WayBillInventoryPresenter(WayBillInventoryView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WayBillInventoryModelImple();
    }

    public void getInventoryList(String id){
        model.getWayBillList(view,this,id);
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
    public void back(List<OrderInventoryDto.qdData> dtos) {
        view.getInventoryList(dtos);

    }
}
