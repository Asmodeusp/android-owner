package com.saimawzc.shipper.view.bidd;

import com.saimawzc.shipper.dto.order.bidd.BiddFirstDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface BiddFirstView extends BaseView {


    void getPlanBiddList(List<BiddFirstDto> dtos);
}
