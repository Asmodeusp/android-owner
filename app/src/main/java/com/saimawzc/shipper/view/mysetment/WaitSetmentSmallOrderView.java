package com.saimawzc.shipper.view.mysetment;


import com.saimawzc.shipper.dto.myselment.WaitDispatchQueryPageDto;
import com.saimawzc.shipper.view.BaseView;

/***
 * 待结算大单
 * **/
public interface WaitSetmentSmallOrderView extends BaseView {
    void getData(WaitDispatchQueryPageDto dto);
    void stopResh();
}
