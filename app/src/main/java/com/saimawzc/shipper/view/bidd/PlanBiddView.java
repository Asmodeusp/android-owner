package com.saimawzc.shipper.view.bidd;

import com.saimawzc.shipper.dto.order.bidd.PlanBiddDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface PlanBiddView extends BaseView {

    void stopResh();

    void isLastpage(boolean islastpage);
    void getPlanBiddList(List<PlanBiddDto.planBiddData>dtos);

}
