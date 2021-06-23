package com.saimawzc.shipper.dto.order.send;

import java.util.List;

public class LogistcsDto {

    private String wayBillNo;
    private String sjPicture;
    private String sjName;
    private String sjPhone;
    private String carNo;
    private List<transportLogList>transportLogList;

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    public String getSjPicture() {
        return sjPicture;
    }

    public void setSjPicture(String sjPicture) {
        this.sjPicture = sjPicture;
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

    public List<LogistcsDto.transportLogList> getTransportLogList() {
        return transportLogList;
    }

    public void setTransportLogList(List<LogistcsDto.transportLogList> transportLogList) {
        this.transportLogList = transportLogList;
    }

    public  class transportLogList{
        private int transportType;
        private String name;
        private String picture;
        private String createTime;
        boolean flag;

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public int getTransportType() {
            return transportType;
        }

        public void setTransportType(int transportType) {
            this.transportType = transportType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }


}
