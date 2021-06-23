package com.saimawzc.shipper.view.mine.carrive;

import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.view.BaseView;
import com.saimawzc.shipper.weight.utils.listen.carrive.CarriveGroupListen;

import java.util.List;

/**
 * Created by Administrator on 2020/8/11.
 */

public interface CarriveGroupView extends BaseView{
    void stopResh();
    void getCarriveList(List<MyCarrierGroupDto>groupDtos);
}
