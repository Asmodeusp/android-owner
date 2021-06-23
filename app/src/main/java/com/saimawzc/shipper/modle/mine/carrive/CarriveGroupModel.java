package com.saimawzc.shipper.modle.mine.carrive;

import com.saimawzc.shipper.view.mine.carrive.CarriveGroupView;
import com.saimawzc.shipper.weight.utils.listen.carrive.CarriveGroupListen;

/**
 * Created by Administrator on 2020/8/11.
 */

public interface CarriveGroupModel {

    void getList(CarriveGroupView view, CarriveGroupListen listener);
}
