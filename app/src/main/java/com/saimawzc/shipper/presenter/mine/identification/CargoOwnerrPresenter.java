package com.saimawzc.shipper.presenter.mine.identification;

import android.content.Context;
import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.identification.OwnCrriverIdentificationDto;
import com.saimawzc.shipper.modle.mine.identification.CargoOwnerCarrierModel;
import com.saimawzc.shipper.modle.mine.identification.CargoOwnerCarrierModelImple;
import com.saimawzc.shipper.view.mine.identificaion.CargoOwnerCarrierView;
import com.saimawzc.shipper.weight.utils.listen.identifiction.OwnerCarriverListener;

/**
 * Created by Administrator on 2020/8/3.
 */

public class CargoOwnerrPresenter implements BaseListener,OwnerCarriverListener{
    private Context mContext;
    CargoOwnerCarrierModel model;
    CargoOwnerCarrierView view;

    public CargoOwnerrPresenter(CargoOwnerCarrierView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new CargoOwnerCarrierModelImple();
    }
    public void getIdentificationInfo(){
        model.getIdentificationInfo(view,this);
    }
    public void showCamera( Context context,  int type){//type 0是身份证正面  1是身份证反面

        model.showCamera(context, type,this) ;

    }
    public void dissCamera( ){//type 0是身份证正面  1是身份证反面
        model.dissCamera(); ;
    }

    public  void carriveRz(){
        model.identification(view,this);
    }
    public  void recarriveRz(){
        model.reidentification(view,this);
    }

    @Override
    public void successful() {
        view.Toast("提交成功");
        view.oncomplete();
    }
    @Override
    public void onFail(String str) {
        view.Toast(str);

    }

    @Override
    public void successful(int type) {
        view.OnDealCamera(type);

    }

    @Override
    public void driviceIndetification(OwnCrriverIdentificationDto identificationDto) {
        view.identification(identificationDto);
    }
}
