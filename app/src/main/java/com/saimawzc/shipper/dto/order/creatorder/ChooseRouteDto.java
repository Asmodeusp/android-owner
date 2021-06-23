package com.saimawzc.shipper.dto.order.creatorder;

import java.io.Serializable;

/*****
 * 路线规划Dto
 * **/
public class ChooseRouteDto implements Serializable {
    private String id;
    private String routeName;
    private String fromUserAddress;
    private String toUserAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getFromUserAddress() {
        return fromUserAddress;
    }

    public void setFromUserAddress(String fromUserAddress) {
        this.fromUserAddress = fromUserAddress;
    }

    public String getToUserAddress() {
        return toUserAddress;
    }

    public void setToUserAddress(String toUserAddress) {
        this.toUserAddress = toUserAddress;
    }
}
