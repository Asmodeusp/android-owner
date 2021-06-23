package com.saimawzc.shipper.weight.utils.listen.order.bidd;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.bidd.PlanBiddDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 */

public interface PlanBiddListener extends BaseListener {
    void back(List<PlanBiddDto.planBiddData> dtos);


}
