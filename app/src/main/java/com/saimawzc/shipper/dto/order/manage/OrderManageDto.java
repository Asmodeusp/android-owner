package com.saimawzc.shipper.dto.order.manage;

import java.io.Serializable;
import java.util.List;

public class OrderManageDto {


    private boolean isLastPage;

    private List<manageData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<manageData> getList() {
        return list;
    }

    public void setList(List<manageData> list) {
        this.list = list;
    }

    public class manageData implements Serializable {
        private String id;
        private String dispatchNo;
        private String fromUserAddress;
        private String fromProName;
        private String fromCityName;
        private String toUserAddress;

        private String toProName;
        private String toCityName;
        private String hzName;
        private String createTime;
        private String carTypeName;
        private String totalWeight;
        private String price;
        private int  sendType;
        private int status;
        private String companyLogo;
        private String carTypeId;
        private double allPrice;
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

        public double getAllPrice() {
            return allPrice;
        }

        public void setAllPrice(double allPrice) {
            this.allPrice = allPrice;
        }

        public String getCarTypeId() {
            return carTypeId;
        }

        public void setCarTypeId(String carTypeId) {
            this.carTypeId = carTypeId;
        }

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDispatchNo() {
            return dispatchNo;
        }

        public void setDispatchNo(String dispatchNo) {
            this.dispatchNo = dispatchNo;
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

        public String getHzName() {
            return hzName;
        }

        public void setHzName(String hzName) {
            this.hzName = hzName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCarTypeName() {
            return carTypeName;
        }

        public void setCarTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
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

        public int getSendType() {
            return sendType;
        }

        public void setSendType(int sendType) {
            this.sendType = sendType;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }





}
