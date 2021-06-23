package com.saimawzc.shipper.dto.order.creatorder;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/8/11.
 * 承运商分组
 */

public class OrderCarrierGroupDto implements Serializable {

    private String name;
    private String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
