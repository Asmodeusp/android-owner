package com.saimawzc.shipper.view.order.waybill;

import com.saimawzc.shipper.dto.order.wallbill.OrderInventoryDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

/***
 * 预运单清单
 * */
public interface WayBillInventoryView extends BaseView {
    void getInventoryList(List<OrderInventoryDto.qdData>dtos);
}
