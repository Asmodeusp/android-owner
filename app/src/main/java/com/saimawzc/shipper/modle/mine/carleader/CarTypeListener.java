package com.saimawzc.shipper.modle.mine.carleader;



import com.saimawzc.shipper.dto.order.bidd.CarTypeDo;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 */

public interface CarTypeListener {

    void callbacktype(List<CarTypeDo> carTypeDos);

}
