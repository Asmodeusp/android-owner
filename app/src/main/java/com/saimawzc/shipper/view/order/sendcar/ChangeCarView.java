package com.saimawzc.shipper.view.order.sendcar;

import com.saimawzc.shipper.dto.order.send.ChangeCarDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface ChangeCarView extends BaseView {

    void getData(List<ChangeCarDto>dtos);
}
