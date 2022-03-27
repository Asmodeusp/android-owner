package com.saimawzc.shipper.view.order;



import com.saimawzc.shipper.dto.order.SendCarDto;
import com.saimawzc.shipper.dto.order.SignWeightDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface SendCarListView extends BaseView {
    void getSendCarList(List<SendCarDto.SendCarData> dtos);
    void stopResh();
    void isLastPage(boolean isLastPage);
    void getSignWeight(SignWeightDto dto);
    void getDoubtSignIn(String dto);

}
