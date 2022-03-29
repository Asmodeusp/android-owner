package com.saimawzc.shipper.dto.order.creatorder;

import android.text.TextUtils;

import java.util.List;

public class AssignDelationDto {

    private boolean isLastPage;

    private List<listdata> list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<listdata> getList() {
        return list;
    }

    public void setList(List<listdata> list) {
        this.list = list;
    }

    public class listdata {
        private String cysName;
        private String cysPhone;
        private String pointPrice;
        private String pointWeight;
        private String endOption;
        private String overWeight;

        private String id;
        private int endStatus;

        int status;

        public String getOverWeight() {
            return overWeight;
        }

        public void setOverWeight(String overWeight) {
            this.overWeight = overWeight;
        }

        public String getEndOption() {
            if (!TextUtils.isEmpty(endOption)) {
                return endOption;
            } else {
                return "";
            }

        }

        public void setEndOption(String endOption) {
            this.endOption = endOption;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getEndStatus() {
            return endStatus;
        }

        public void setEndStatus(int endStatus) {
            this.endStatus = endStatus;
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

        public String getPointPrice() {
            return pointPrice;
        }

        public void setPointPrice(String pointPrice) {
            this.pointPrice = pointPrice;
        }

        public String getPointWeight() {
            return pointWeight;
        }

        public void setPointWeight(String pointWeight) {
            this.pointWeight = pointWeight;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }


}
