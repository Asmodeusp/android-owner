package com.saimawzc.shipper.view.order;

import com.saimawzc.shipper.dto.order.ConsultDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface ConsultView extends BaseView {

    void getConsult(List<ConsultDto.data> dtos);
    void stopResh();
    void isLast(boolean islast);
}
