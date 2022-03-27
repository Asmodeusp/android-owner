package com.saimawzc.shipper.dto.order;

import java.util.List;

/***
 * 参照生成
 * **/
public class ConsultDto {
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
        private String pid;
        private String orderId;
        private String orderCode;
        private String companyName;
        private String specs;
        private String matericalName;
        private String from_Name;
        private String sendTo_Name;
        private String businessType;
        private String businessTypeName;
        private String synchroName;
        private String quantity;
        private String overQuantity;
        private String fhAddr;
        private String shAddr;
        private String carrierName;
        private String tranType;
        private String tranTypeName;
        private String createTime;
        private String resTxt2;

        public String getResTxt2() {
            return resTxt2;
        }

        public void setResTxt2(String resTxt2) {
            this.resTxt2 = resTxt2;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public String getMatericalName() {
            return matericalName;
        }

        public void setMatericalName(String matericalName) {
            this.matericalName = matericalName;
        }

        public String getFrom_Name() {
            return from_Name;
        }

        public void setFrom_Name(String from_Name) {
            this.from_Name = from_Name;
        }

        public String getSendTo_Name() {
            return sendTo_Name;
        }

        public void setSendTo_Name(String sendTo_Name) {
            this.sendTo_Name = sendTo_Name;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getBusinessTypeName() {
            return businessTypeName;
        }

        public void setBusinessTypeName(String businessTypeName) {
            this.businessTypeName = businessTypeName;
        }

        public String getSynchroName() {
            return synchroName;
        }

        public void setSynchroName(String synchroName) {
            this.synchroName = synchroName;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getOverQuantity() {
            return overQuantity;
        }

        public void setOverQuantity(String overQuantity) {
            this.overQuantity = overQuantity;
        }

        public String getFhAddr() {
            return fhAddr;
        }

        public void setFhAddr(String fhAddr) {
            this.fhAddr = fhAddr;
        }

        public String getShAddr() {
            return shAddr;
        }

        public void setShAddr(String shAddr) {
            this.shAddr = shAddr;
        }

        public String getCarrierName() {
            return carrierName;
        }

        public void setCarrierName(String carrierName) {
            this.carrierName = carrierName;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

}
