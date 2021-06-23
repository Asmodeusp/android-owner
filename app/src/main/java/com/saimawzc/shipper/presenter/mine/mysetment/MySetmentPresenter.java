package com.saimawzc.shipper.presenter.mine.mysetment;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.SearchValueDto;
import com.saimawzc.shipper.dto.identification.OwnCrriverIdentificationDto;
import com.saimawzc.shipper.dto.myselment.MySetmentDto;
import com.saimawzc.shipper.modle.mine.identification.CargoOwnerCarrierModel;
import com.saimawzc.shipper.modle.mine.identification.CargoOwnerCarrierModelImple;
import com.saimawzc.shipper.modle.mine.mysetment.MySetmentModel;
import com.saimawzc.shipper.modle.mine.mysetment.MySetmentModelImple;
import com.saimawzc.shipper.view.mine.identificaion.CargoOwnerCarrierView;
import com.saimawzc.shipper.view.mysetment.MySetmentView;
import com.saimawzc.shipper.weight.utils.listen.identifiction.OwnerCarriverListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/3.
 */

public class MySetmentPresenter  {
    private Context mContext;
    MySetmentModel model;
    MySetmentView view;

    public MySetmentPresenter(MySetmentView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MySetmentModelImple();
    }
    public void getData(int page, int type,List<SearchValueDto>dtos){
        model.getList(page,type,dtos,view);
    }

    public void confirm(int type,String id){
        model.comfirm(type,id,view);
    }


}
