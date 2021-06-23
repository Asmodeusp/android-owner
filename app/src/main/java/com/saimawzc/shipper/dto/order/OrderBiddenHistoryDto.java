package com.saimawzc.shipper.dto.order;

/***
 * 竞价历史
 * */

public class OrderBiddenHistoryDto {

    private String createTime;
    private String biddPrice;
    private String biddWeight;
    private String sjName;//司机信息
    private String carNo;//车牌
    private String carNum;//车次
    private String roleType;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBiddPrice() {
        return biddPrice;
    }

    public void setBiddPrice(String biddPrice) {
        this.biddPrice = biddPrice;
    }

    public String getBiddWeight() {
        return biddWeight;
    }

    public void setBiddWeight(String biddWeight) {
        this.biddWeight = biddWeight;
    }
}
