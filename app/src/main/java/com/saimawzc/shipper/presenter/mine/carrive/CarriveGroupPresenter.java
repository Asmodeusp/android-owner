package com.saimawzc.shipper.presenter.mine.carrive;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.modle.mine.carrive.CarriveGroupModel;
import com.saimawzc.shipper.modle.mine.carrive.CarriveGroupModelImple;
import com.saimawzc.shipper.view.mine.carrive.CarriveGroupView;
import com.saimawzc.shipper.weight.utils.listen.carrive.CarriveGroupListen;

import java.util.List;

/**
 * Created by Administrator on 2020/8/3.\
 * 承运商分组
 */

public class CarriveGroupPresenter implements BaseListener,CarriveGroupListen{

    private Context mContext;
    CarriveGroupModel model;
    CarriveGroupView view;

    public CarriveGroupPresenter(CarriveGroupView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CarriveGroupModelImple();
    }

    public void getCarreListiv( ){
        model.getList(view,this); ;
    }
    @Override
    public void successful() {
        view.Toast("提交成功");
        view.oncomplete();
        view.stopResh();
    }
    @Override
    public void onFail(String str) {
        view.Toast(str);
    }

    @Override
    public void successful(int type) {
    }


    @Override
    public void getCarrive(List<MyCarrierGroupDto> dtos) {
        view.getCarriveList(dtos);

    }
}
