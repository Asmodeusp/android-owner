package com.saimawzc.shipper.dto.order;

/*****
 * 指派二级页面
 * */
public class OrderAssignmentSecondDto {
    private String id;
    private String cysId;
    private String cysCode;
    private String cysName;
    private String cysPhone;
    private String zpTime;

    public String getZpTime() {
        return zpTime;
    }

    public void setZpTime(String zpTime) {
        this.zpTime = zpTime;
    }

    private double trantPrice;

    public double getTrantPrice() {
        return trantPrice;
    }

    public void setTrantPrice(double trantPrice) {
        this.trantPrice = trantPrice;
    }

    public double getTrantNum() {
        return trantNum;
    }

    public void setTrantNum(double trantNum) {
        this.trantNum = trantNum;
    }

    private double trantNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCysId() {
        return cysId;
    }

    public void setCysId(String cysId) {
        this.cysId = cysId;
    }

    public String getCysCode() {
        return cysCode;
    }

    public void setCysCode(String cysCode) {
        this.cysCode = cysCode;
    }

    public String getCysName() {
        return cysName;
    }

    public void setCysName(String cysName) {
        this.cysName = cysName;
    }

    public String getCysPhone() {
        return cysPhone;
    }

    public void setCysPhone(String cysPhone) {
        this.cysPhone = cysPhone;
    }
}
