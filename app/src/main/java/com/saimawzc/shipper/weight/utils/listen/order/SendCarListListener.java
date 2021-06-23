package com.saimawzc.shipper.weight.utils.listen.order;



import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.order.SendCarDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 *
 */

public interface SendCarListListener extends BaseListener {


    void getManageOrderList(List<SendCarDto.SendCarData> dtos);

}
