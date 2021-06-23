package com.saimawzc.shipper.view.mysetment;


import com.saimawzc.shipper.dto.myselment.WaitComfirmQueryPageDto;
import com.saimawzc.shipper.view.BaseView;

/***
 * 待结算大单
 * **/
public interface WaitSetmentView extends BaseView {
    void getData(WaitComfirmQueryPageDto dto);
    void stopResh();
}
