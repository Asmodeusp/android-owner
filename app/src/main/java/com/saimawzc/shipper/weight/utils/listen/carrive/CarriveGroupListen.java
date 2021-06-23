package com.saimawzc.shipper.weight.utils.listen.carrive;

import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/11.
 */

public interface CarriveGroupListen {
    void getCarrive(List<MyCarrierGroupDto>dtos);
}
