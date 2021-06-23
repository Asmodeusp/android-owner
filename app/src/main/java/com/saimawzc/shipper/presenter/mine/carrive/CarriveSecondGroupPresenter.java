package com.saimawzc.shipper.presenter.mine.carrive;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.dto.carrier.MycarrierGroupSecondDto;
import com.saimawzc.shipper.modle.mine.carrive.CarriveGroupModel;
import com.saimawzc.shipper.modle.mine.carrive.CarriveGroupModelImple;
import com.saimawzc.shipper.modle.mine.carrive.CarriveGroupSecondlModelImple;
import com.saimawzc.shipper.modle.mine.carrive.CarriveSecondModel;
import com.saimawzc.shipper.view.mine.carrive.CarriveGroupView;
import com.saimawzc.shipper.view.mine.carrive.CarriveSecondView;
import com.saimawzc.shipper.weight.utils.listen.carrive.CarriveGroupListen;

import java.util.List;

/**
 * Created by Administrator on 2020/8/3.\
 * 承运商二级分组
 */

public class CarriveSecondGroupPresenter implements BaseListener,CarriveSecondView{

    private Context mContext;
    CarriveSecondModel model;
    CarriveSecondView view;

    public CarriveSecondGroupPresenter(CarriveSecondView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CarriveGroupSecondlModelImple();
    }

    public void getCarreListiv(String id ){
        model.getList(view,this,id); ;
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
    public void showLoading() {
        view.showLoading();

    }

    @Override
    public void dissLoading() {
        view.dissLoading();

    }

    @Override
    public void Toast(String str) {
        view.Toast(str);

    }

    @Override
    public void oncomplete() {
  view.stopResh();
    }

    @Override
    public void stopResh() {
        view.stopResh();
    }

    @Override
    public void getCarriveList(List<MycarrierGroupSecondDto> groupDtos) {
        view.getCarriveList(groupDtos);
    }
}
