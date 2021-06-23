package com.saimawzc.shipper.view.consign;


import com.saimawzc.shipper.dto.consign.ConsignDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface ConsignView extends BaseView {

    void getConsignList(List<ConsignDto.data>dots);
    void isLastPage(boolean isLastPage);
    void stopResh();



}
