package com.saimawzc.shipper.dto.order.bidd;

public class BiddFirstDto {
    private String id;
    private String pointWeight;
    private String biddNum;
    private String extent;
    private String startTime;
    private String endTime;
    private String roleType;
    private String unit;
    private String taskStartTime;
    private String taskEndTime;
    private String type;//竞价类型 2 -承运商 3- 司机 5-车次竞价
    private String carTypeName;//车辆类型名
    private String needBeiDou;//是否需要开启北斗
    private String moreDispatch;//是否允许多次竞价
    private String showRanking;//是否显示竞价排名
    private int status;//状态 0 未开始1 竞价中2 分配中3 已终止4 已分配

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShowRanking() {
        return showRanking;
    }
    public void setShowRanking(String showRanking) {
        this.showRanking = showRanking;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
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

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getPointWeight() {
        return pointWeight;
    }

    public void setPointWeight(String pointWeight) {
        this.pointWeight = pointWeight;
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
}
