package com.saimawzc.shipper.modle.mine.identification;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.mine.identificaion.CargoOwnerCarrierView;
import com.saimawzc.shipper.weight.utils.listen.identifiction.OwnerCarriverListener;

/**
 * Created by Administrator on 2020/8/3.
 */

public interface CargoOwnerCarrierModel {
    void identification(CargoOwnerCarrierView view,BaseListener listener);
    void reidentification(CargoOwnerCarrierView view,BaseListener listener);
    void showCamera(Context context,int type,BaseListener listener);

    void dissCamera();
    void getIdentificationInfo(CargoOwnerCarrierView view,OwnerCarriverListener listener);
}
