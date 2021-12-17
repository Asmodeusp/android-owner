package com.saimawzc.shipper.dto.order;

public class OrderListDto {

    private int checkStatus;//审核状态，1-审核中，2-审核通过，3-审核未通过
    private String fromUserAddress;
    private String id;
    private String materialsName;
    private double overAllotWeight;
    private double overCheckWeight;
    private int sendType;//0无  1指派  2竞价
    private String toUserAddress;
    private double totalWeight;
    private int weightUnit;
    private String createTime;
    private String fromProName;
    private String toProName;
    private int wayBillStatus;//1-预提单，2-以竞价/以指派 ，3-已确认，
    // 4-以拒绝 5-运输中 6-暂停运输 7-终
    private String companyLogo;
    private String thirdPartyNo;
    private int isBidd;//是1就显示竞价按钮
    private String underWay;
    private String consult;
    private String weightUnitName;
    private int isAppointTime;

    public int getIsAppointTime() {
        return isAppointTime;
    }

    public void setIsAppointTime(int isAppointTime) {
        this.isAppointTime = isAppointTime;
    }

    public String getWeightUnitName() {
        return weightUnitName;
    }

    public void setWeightUnitName(String weightUnitName) {
        this.weightUnitName = weightUnitName;
    }

    public String getUnderWay() {
        return underWay;
    }

    public void setUnderWay(String underWay) {
        this.underWay = underWay;
    }

    public String getConsult() {
        return consult;
    }

    public void setConsult(String consult) {
        this.consult = consult;
    }

    public int getIsBidd() {
        return isBidd;
    }

    public void setIsBidd(int isBidd) {
        this.isBidd = isBidd;
    }

    public String getThirdPartyNo() {
        return thirdPartyNo;
    }

    public void setThirdPartyNo(String thirdPartyNo) {
        this.thirdPartyNo = thirdPartyNo;
    }

    private String fromName;
    private String toName;

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFromProName() {
        return fromProName;
    }

    public void setFromProName(String fromProName) {
        this.fromProName = fromProName;
    }

    public String getToProName() {
        return toProName;
    }

    public void setToProName(String toProName) {
        this.toProName = toProName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public int getWayBillStatus() {
        return wayBillStatus;
    }

    public void setWayBillStatus(int wayBillStatus) {
        this.wayBillStatus = wayBillStatus;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getFromUserAddress() {
        return fromUserAddress;
    }

    public void setFromUserAddress(String fromUserAddress) {
        this.fromUserAddress = fromUserAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterialsName() {
        return materialsName;
    }

    public void setMaterialsName(String materialsName) {
        this.materialsName = materialsName;
    }

    public double getOverAllotWeight() {
        return overAllotWeight;
    }

    public void setOverAllotWeight(double overAllotWeight) {
        this.overAllotWeight = overAllotWeight;
    }

    public double getOverCheckWeight() {
        return overCheckWeight;
    }

    public void setOverCheckWeight(double overCheckWeight) {
        this.overCheckWeight = overCheckWeight;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getToUserAddress() {
        return toUserAddress;
    }

    public void setToUserAddress(String toUserAddress) {
        this.toUserAddress = toUserAddress;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(int weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
