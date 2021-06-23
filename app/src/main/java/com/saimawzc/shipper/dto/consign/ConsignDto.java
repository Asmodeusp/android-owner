package com.saimawzc.shipper.dto.consign;

import java.util.List;

public class ConsignDto {
    private boolean isLastPage;
    private List<data>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<data> getList() {
        return list;
    }

    public void setList(List<data> list) {
        this.list = list;
    }

    public class  data{
        private String id;
        private String wayBillNo;
        private String fromUserAddress;
        private String fromProName;
        private String fromCityName;
        private String toUserAddress;
        private String totalWeight;
        private String price;
        private String toProName;
        private String toCityName;
        private String weightUnit;
        private String checkStatus;
        private String sendType;
        private String businessType;
        private String wayBillStatus;
        private String companyLogo;
        private String createTime;
        private String allPrice;
        private String tranType;
        private String tranTypeName;
        private String fromName;
        private String toName;
        private String isBidd;
        private String hzId;
        private String dispatchNo;
        private String sjName;
        private String carNo;
        private String materialsName;

        public String getDispatchNo() {
            return dispatchNo;
        }

        public void setDispatchNo(String dispatchNo) {
            this.dispatchNo = dispatchNo;
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

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWayBillNo() {
            return wayBillNo;
        }

        public void setWayBillNo(String wayBillNo) {
            this.wayBillNo = wayBillNo;
        }

        public String getFromUserAddress() {
            return fromUserAddress;
        }

        public void setFromUserAddress(String fromUserAddress) {
            this.fromUserAddress = fromUserAddress;
        }

        public String getFromProName() {
            return fromProName;
        }

        public void setFromProName(String fromProName) {
            this.fromProName = fromProName;
        }

        public String getFromCityName() {
            return fromCityName;
        }

        public void setFromCityName(String fromCityName) {
            this.fromCityName = fromCityName;
        }

        public String getToUserAddress() {
            return toUserAddress;
        }

        public void setToUserAddress(String toUserAddress) {
            this.toUserAddress = toUserAddress;
        }

        public String getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(String totalWeight) {
            this.totalWeight = totalWeight;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getToProName() {
            return toProName;
        }

        public void setToProName(String toProName) {
            this.toProName = toProName;
        }

        public String getToCityName() {
            return toCityName;
        }

        public void setToCityName(String toCityName) {
            this.toCityName = toCityName;
        }

        public String getWeightUnit() {
            return weightUnit;
        }

        public void setWeightUnit(String weightUnit) {
            this.weightUnit = weightUnit;
        }

        public String getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(String checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getSendType() {
            return sendType;
        }

        public void setSendType(String sendType) {
            this.sendType = sendType;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getWayBillStatus() {
            return wayBillStatus;
        }

        public void setWayBillStatus(String wayBillStatus) {
            this.wayBillStatus = wayBillStatus;
        }

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAllPrice() {
            return allPrice;
        }

        public void setAllPrice(String allPrice) {
            this.allPrice = allPrice;
        }

        public String getTranType() {
            return tranType;
        }

        public void setTranType(String tranType) {
            this.tranType = tranType;
        }

        public String getTranTypeName() {
            return tranTypeName;
        }

        public void setTranTypeName(String tranTypeName) {
            this.tranTypeName = tranTypeName;
        }

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

        public String getIsBidd() {
            return isBidd;
        }

        public void setIsBidd(String isBidd) {
            this.isBidd = isBidd;
        }

        public String getHzId() {
            return hzId;
        }

        public void setHzId(String hzId) {
            this.hzId = hzId;
        }
    }
}
