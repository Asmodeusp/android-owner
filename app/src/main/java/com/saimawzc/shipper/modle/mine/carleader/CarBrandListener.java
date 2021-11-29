package com.saimawzc.shipper.modle.mine.carleader;



import com.saimawzc.shipper.dto.carleader.CarBrandDto;

import java.util.List;

/**
 * Created by Administrator on 2020/8/7.
 */

public interface CarBrandListener {
    void callbackbrand(List<CarBrandDto> carTypeDos);
}
