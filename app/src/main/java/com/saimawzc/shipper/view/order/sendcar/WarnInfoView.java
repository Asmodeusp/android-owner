package com.saimawzc.shipper.view.order.sendcar;



import com.saimawzc.shipper.dto.order.send.WarnInfoDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface WarnInfoView extends BaseView {
    void getData(List<WarnInfoDto> list);
}
