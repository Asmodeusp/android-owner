package com.saimawzc.shipper.dto.order.send;

/****
 * 换车
 * **/
public class ChangeCarDto {
    private String wayBillNo;
    private String oldCarNo;
    private String carNo;
    private String reason;

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    public String getOldCarNo() {
        return oldCarNo;
    }

    public void setOldCarNo(String oldCarNo) {
        this.oldCarNo = oldCarNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
