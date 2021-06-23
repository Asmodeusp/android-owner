package com.saimawzc.shipper.dto.order.creatorder;

import java.io.Serializable;
import java.util.List;

/***
 * 订单详情
 * */
public class UntilDto {
    private String id;
    private String unit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
