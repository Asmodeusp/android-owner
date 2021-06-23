package com.saimawzc.shipper.dto.order.bidd;

public class ManageBiddDto {
    private String id;
    private String time;
    private String carmodel;
    private String weiht;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCarmodel() {
        return carmodel;
    }

    public void setCarmodel(String carmodel) {
        this.carmodel = carmodel;
    }

    public String getWeiht() {
        return weiht;
    }

    public void setWeiht(String weiht) {
        this.weiht = weiht;
    }
}
