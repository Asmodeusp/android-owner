package com.saimawzc.shipper.view.order.fence;


import com.saimawzc.shipper.dto.order.creatorder.DangerousFenceDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface FenceView extends BaseView {

    void getFenceList(List<DangerousFenceDto> dtos);
    void stopResh();
    void isLast(boolean islast);
}
