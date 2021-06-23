package com.saimawzc.shipper.dto.order.bidd;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/8/7.
 */

public class CarTypeDo implements Serializable {
    private String carCode;
    private String carTypeName;
    private String id;

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
