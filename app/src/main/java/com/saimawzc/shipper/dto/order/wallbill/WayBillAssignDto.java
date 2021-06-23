package com.saimawzc.shipper.dto.order.wallbill;

import java.util.List;

/****
 * 调度单指派
 * */
public class WayBillAssignDto {


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

    public class  waybillData{
        private String id;
        private String cysId;
        private String cysCode;
        private String cysName;
        private String cysPhone;
        private String allPrice;

        public String getAllPrice() {
            return allPrice;
        }

        public void setAllPrice(String allPrice) {
            this.allPrice = allPrice;
        }

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




}
