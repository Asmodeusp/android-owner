package com.saimawzc.shipper.dto.order;

import java.util.List;

/***
 * 派车
 * */
public class SendCarDto {

    private  boolean isLastPage;
    private List<SendCarData>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<SendCarData> getList() {
        return list;
    }

    public void setList(List<SendCarData> list) {
        this.list = list;
    }

    public class SendCarData{
        private String id;
        private String toProName;
        private String toCityName;
        private String fromProName;
        private String fromCityName;
        private String dispatchNo;
        private String sjName;
        private String carNo;
        private String companyLogo;
        private String materialsName;
        private String sjId;
        private double startTime;
        private double endTime;
        private int  status;
        private String cysPhone;
        private String fromName;
        private String toName;
        private String fromUserAddress;
        private String toUserAddress;

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getToName() {
            return toName;
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

        public void setToName(String toName) {
            this.toName = toName;
        }

        public String getCysPhone() {
            return cysPhone;
        }

        public void setCysPhone(String cysPhone) {
            this.cysPhone = cysPhone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getStartTime() {
            return startTime;
        }

        public void setStartTime(double startTime) {
            this.startTime = startTime;
        }

        public double getEndTime() {
            return endTime;
        }

        public void setEndTime(double endTime) {
            this.endTime = endTime;
        }

        public String getSjId() {
            return sjId;
        }

        public void setSjId(String sjId) {
            this.sjId = sjId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
        }
    }
}
