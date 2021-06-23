package com.saimawzc.shipper.dto.order;

/****
 * 竞价详情
 * **/
public class BiddingDelationDto {

    private String wayBillId;
    private String wayBillNo;
    private String cysId;
    private String cysCode;//
    private String cysName;//
    private double biddPrice;//
    private double biddWeight;
    private int countNum;
    private double machNum;//分配量
    private int pointStatus ;//是否分配 1-否 2-是
    private String pointWeight;
    private String roleType;//竞价类型 2-承运商 3-司机 5-车次 不做显示
    private String sjName;
    private String carNo;//车牌号
    private String carNum;//竞价车次
    private double pointCarNum;//分配车次

    public double getPointCarNum() {
        return pointCarNum;
    }

    public void setPointCarNum(double pointCarNum) {
        this.pointCarNum = pointCarNum;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public int getPointStatus() {
        return pointStatus;
    }

    public void setPointStatus(int pointStatus) {
        this.pointStatus = pointStatus;
    }

    public String getPointWeight() {
        return pointWeight;
    }

    public void setPointWeight(String pointWeight) {
        this.pointWeight = pointWeight;
    }

    public double getMachNum() {
        return machNum;
    }

    public void setMachNum(double machNum) {
        this.machNum = machNum;
    }

    public String getCysId() {
        return cysId;
    }

    public void setCysId(String cysId) {
        this.cysId = cysId;
    }

    public String getWayBillId() {
        return wayBillId;
    }

    public void setWayBillId(String wayBillId) {
        this.wayBillId = wayBillId;
    }

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
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

    public double getBiddPrice() {
        return biddPrice;
    }

    public void setBiddPrice(double biddPrice) {
        this.biddPrice = biddPrice;
    }

    public double getBiddWeight() {
        return biddWeight;
    }

    public void setBiddWeight(double biddWeight) {
        this.biddWeight = biddWeight;
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }
}
