package com.saimawzc.shipper.weight.utils.listen.order;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.dto.carrier.MyCarriveDto;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 */

public interface ConsignCompanyListener extends BaseListener {
    void back(List<ConsignmentCompanyDto> dtos);


}
