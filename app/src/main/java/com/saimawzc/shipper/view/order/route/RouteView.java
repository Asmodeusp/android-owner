package com.saimawzc.shipper.view.order.route;

import com.saimawzc.shipper.dto.order.creatorder.ChooseRouteDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface RouteView extends BaseView {
    void getRoute(List<ChooseRouteDto> dtos);
    void  stopResh();
}
