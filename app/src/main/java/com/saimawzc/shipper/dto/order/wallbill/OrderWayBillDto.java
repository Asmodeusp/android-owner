package com.saimawzc.shipper.dto.order.wallbill;

import java.io.Serializable;
import java.util.List;

/***
 * 预运单
 * **/
public class OrderWayBillDto {


    private boolean isLastPage;
    private List<waybillData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<waybillData> getList() {
        return list;
    }

    public void setList(List<waybillData> list) {
        this.list = list;
    }

    public class waybillData implements Serializable {
        private String id;
        private String fromUserAddress;
        private String fromProName;
        private String fromCityName;
        private String toUserAddress;
        private String toCityName;
        private String toProName;
        private int wayBillStatus;
        private String totalWeight;
        private int weightUnit;
        private  int checkStatus;
        private int  sendType;
        private String companyLogo;
        private String createTime;
        private String wayBillNo;
        private String allPrice;
        private String fromName;
        private String toName;
        private int isBidd;

        public int getIsBidd() {
            return isBidd;
        }

        public void setIsBidd(int isBidd) {
            this.isBidd = isBidd;
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

        public String getAllPrice() {
            return allPrice;
        }

        public void setAllPrice(String allPrice) {
            this.allPrice = allPrice;
        }

        public String getWayBillNo() {
            return wayBillNo;
        }

        public void setWayBillNo(String wayBillNo) {
            this.wayBillNo = wayBillNo;
        }

        public int getWayBillStatus() {
            return wayBillStatus;
        }

        public String getToProName() {
            return toProName;
        }

        public void setToProName(String toProName) {
            this.toProName = toProName;
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

        public String getToCityName() {
            return toCityName;
        }

        public void setToCityName(String toCityName) {
            this.toCityName = toCityName;
        }

        public int getWeightUnit() {
            return weightUnit;
        }

        public void setWeightUnit(int weightUnit) {
            this.weightUnit = weightUnit;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public int getSendType() {
            return sendType;
        }

        public void setSendType(int sendType) {
            this.sendType = sendType;
        }

        public void setWayBillStatus(int wayBillStatus) {
            this.wayBillStatus = wayBillStatus;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }



        public String getFromUserAddress() {
            return fromUserAddress;
        }

        public void setFromUserAddress(String fromUserAddress) {
            this.fromUserAddress = fromUserAddress;
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





        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }
    }


}
