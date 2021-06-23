package com.saimawzc.shipper.dto.order.send;

import java.util.List;

public class SendCarDelationDto {

    private String wayBillNo;
    private String cysName;
    private String sjName;
    private String sjPhone;
    private String carNo;
    private String materialsAlias;
    private String totalWeight;
    private String confirmWeight;
    private String signInWeight;
    private String sendCarType;
    private String signTime;
    private String confirmName;
    private String confirmStatus;
    private String thirdPartyNo;
    private String sendCarNo;
    private String fromName;
    private String fromUserAddress;
    private String toName;
    private String toUserAddress;
    private List<material>list;
    private String orderNo;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    public String getCysName() {
        return cysName;
    }

    public void setCysName(String cysName) {
        this.cysName = cysName;
    }

    public String getSjName() {
        return sjName;
    }

    public void setSjName(String sjName) {
        this.sjName = sjName;
    }

    public String getSjPhone() {
        return sjPhone;
    }

    public void setSjPhone(String sjPhone) {
        this.sjPhone = sjPhone;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getMaterialsAlias() {
        return materialsAlias;
    }

    public void setMaterialsAlias(String materialsAlias) {
        this.materialsAlias = materialsAlias;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getConfirmWeight() {
        return confirmWeight;
    }

    public void setConfirmWeight(String confirmWeight) {
        this.confirmWeight = confirmWeight;
    }

    public String getSignInWeight() {
        return signInWeight;
    }

    public void setSignInWeight(String signInWeight) {
        this.signInWeight = signInWeight;
    }

    public String getSendCarType() {
        return sendCarType;
    }

    public void setSendCarType(String sendCarType) {
        this.sendCarType = sendCarType;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getConfirmName() {
        return confirmName;
    }

    public void setConfirmName(String confirmName) {
        this.confirmName = confirmName;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getThirdPartyNo() {
        return thirdPartyNo;
    }

    public void setThirdPartyNo(String thirdPartyNo) {
        this.thirdPartyNo = thirdPartyNo;
    }

    public String getSendCarNo() {
        return sendCarNo;
    }

    public void setSendCarNo(String sendCarNo) {
        this.sendCarNo = sendCarNo;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromLocation() {
        return fromUserAddress;
    }

    public void setFromLocation(String fromLocation) {
        this.fromUserAddress = fromLocation;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToUserAddress() {
        return toUserAddress;
    }

    public void setToUserAddress(String toUserAddress) {
        this.toUserAddress = toUserAddress;
    }

    public List<material> getList() {
        return list;
    }

    public void setList(List<material> list) {
        this.list = list;
    }

    public class material{
        private String materialsName;
        private String weight;
        private String price;

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

}
