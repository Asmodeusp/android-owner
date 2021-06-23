package com.saimawzc.shipper.presenter.bidd;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.identification.OwnCrriverIdentificationDto;
import com.saimawzc.shipper.dto.order.bidd.PlanBiddDto;
import com.saimawzc.shipper.modle.bidd.imple.PlanBiddModelImple;
import com.saimawzc.shipper.modle.bidd.model.PlanBiddModel;
import com.saimawzc.shipper.modle.mine.identification.CargoOwnerCarrierModel;
import com.saimawzc.shipper.modle.mine.identification.CargoOwnerCarrierModelImple;
import com.saimawzc.shipper.view.bidd.PlanBiddView;
import com.saimawzc.shipper.view.mine.identificaion.CargoOwnerCarrierView;
import com.saimawzc.shipper.weight.utils.listen.identifiction.OwnerCarriverListener;
import com.saimawzc.shipper.weight.utils.listen.order.bidd.PlanBiddListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/3.
 */

public class PlanBiddPresenter implements PlanBiddListener {
    private Context mContext;
    PlanBiddModel model;
    PlanBiddView view;

    public PlanBiddPresenter(PlanBiddView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new PlanBiddModelImple();
    }

    public void getPlanBiddList(String type,int page,String searchType,String searchValue){
        model.getBiddLsit(view,this,type,page,searchType,searchValue);
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
    public void back(List<PlanBiddDto.planBiddData> dtos) {
        view.getPlanBiddList(dtos);


    }
}
