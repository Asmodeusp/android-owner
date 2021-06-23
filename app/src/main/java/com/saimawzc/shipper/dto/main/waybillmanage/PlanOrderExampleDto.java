package com.saimawzc.shipper.dto.main.waybillmanage;

/***
 * 计划订单审核
 * **/
public class PlanOrderExampleDto {

    private String  name;
    private String weiht;
    private String time;
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeiht() {
        return weiht;
    }

    public void setWeiht(String weiht) {
        this.weiht = weiht;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
