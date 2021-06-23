package com.saimawzc.shipper.dto.order.bidd;

/***
 * 竞价临时变量
 * **/
public class BiddTempDto {
    private String id;
    private String wayBillType;
    private String startTime;
    private String endTime;
    private String biddNum;
    private String  extent;//降价幅度
    private String carTypeId;
    private String carTypeName;
    private String biddWeight;
    private String roleType;
    private String edFloorPrice;//底价
    private String taskTimeStart;
    private String taskTimeEnd;
    private String needBeiDou;//是否需要北斗
    private String moreDispatch;//是否允许一辆车竞价多个派车单（1-是 2-否） roleType为5必填
    private String highBiddNum;//最高清单量
    private String isRank;//是否开启竞价排名

    public String getIsRank() {
        return isRank;
    }

    public void setIsRank(String isRank) {
        this.isRank = isRank;
    }

    public String getHighBiddNum() {
        return highBiddNum;
    }

    public void setHighBiddNum(String highBiddNum) {
        this.highBiddNum = highBiddNum;
    }

    public String getNeedBeiDou() {
        return needBeiDou;
    }

    public void setNeedBeiDou(String needBeiDou) {
        this.needBeiDou = needBeiDou;
    }

    public String getMoreDispatch() {
        return moreDispatch;
    }

    public void setMoreDispatch(String moreDispatch) {
        this.moreDispatch = moreDispatch;
    }

    public String getTaskTimeStart() {
        return taskTimeStart;
    }

    public void setTaskTimeStart(String taskTimeStart) {
        this.taskTimeStart = taskTimeStart;
    }

    public String getTaskTimeEnd() {
        return taskTimeEnd;
    }

    public void setTaskTimeEnd(String taskTimeEnd) {
        this.taskTimeEnd = taskTimeEnd;
    }

    public String getEdFloorPrice() {
        return edFloorPrice;
    }

    public void setEdFloorPrice(String edFloorPrice) {
        this.edFloorPrice = edFloorPrice;
    }

    public String getBiddWeight() {
        return biddWeight;
    }

    public void setBiddWeight(String biddWeight) {
        this.biddWeight = biddWeight;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWayBillType() {
        return wayBillType;
    }

    public void setWayBillType(String wayBillType) {
        this.wayBillType = wayBillType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBiddNum() {
        return biddNum;
    }

    public void setBiddNum(String biddNum) {
        this.biddNum = biddNum;
    }

    public String getExtent() {
        return extent;
    }

    public void setExtent(String extent) {
        this.extent = extent;
    }

    public String getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }
}
