package com.saimawzc.shipper.view.mysetment;

import com.saimawzc.shipper.dto.myselment.MySetmentDto;
import com.saimawzc.shipper.dto.myselment.MySetmentPageQueryDto;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface MySetmentView extends BaseView {

    void getMySetment(MySetmentPageQueryDto dtos);
    void stopResh();
}
