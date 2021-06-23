package com.saimawzc.shipper.modle.order.model.waybill;

import com.saimawzc.shipper.dto.order.wallbill.WayBillAssignDto;
import com.saimawzc.shipper.view.order.waybill.WayBillAssignView;
import com.saimawzc.shipper.weight.utils.listen.order.waybill.WaybillAssignListListener;

public interface WayBillAssignModel {

    void getWayBillAssign(WayBillAssignView view,
                          WaybillAssignListListener listListener, int page,String phone);

    void wayBillZp(WayBillAssignView view, WaybillAssignListListener listListener,
                   WayBillAssignDto.waybillData data,String id,String price,String type);
}
