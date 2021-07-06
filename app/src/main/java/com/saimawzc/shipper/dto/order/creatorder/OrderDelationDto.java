package com.saimawzc.shipper.dto.order.creatorder;

import java.util.List;

/***
 * 订单详情
 * */
public class OrderDelationDto {

    private String arrivalStartTime;
    private String arrivalEndTime;
    private int autoSign;
    private int beidouTrack;
    private String companyId;
    private String companyName;
    private String confirmor;
    private String  confirmorStactics;
    private String fromId;
    private String fromName;
    private String fromUserAddress;
    private String fromUserName;
    private String fromUserPhone;
    private String handComName;
    private String handCompanyId;
    private String id;
    private String singStacticsId;
    private String toOperateTime;
    private String toLocation;
    private int businessType;


    private String tranType;
    private String tranTypeName;
    private String trackRouteId;
    private String routeName;

    public String getTrackRouteId() {
        return trackRouteId;
    }

    public void setTrackRouteId(String trackRouteId) {
        this.trackRouteId = trackRouteId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
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

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    private String confirmorName;

    private String  fromLocation;
    private String autoSignName;

    private String confirmorStacticsName;
    private String wayBillTypeName;

    private String fromProName;
    private String fromCityName;
    private String toProName;
    private String toCityName;

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

    public String getWayBillTypeName() {
        return wayBillTypeName;
    }

    public void setWayBillTypeName(String wayBillTypeName) {
        this.wayBillTypeName = wayBillTypeName;
    }

    public String getConfirmorName() {
        return confirmorName;
    }

    public void setConfirmorName(String confirmorName) {
        this.confirmorName = confirmorName;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getAutoSignName() {
        return autoSignName;
    }

    public void setAutoSignName(String autoSignName) {
        this.autoSignName = autoSignName;
    }

    public String getConfirmorStacticsName() {
        return confirmorStacticsName;
    }

    public void setConfirmorStacticsName(String confirmorStacticsName) {
        this.confirmorStacticsName = confirmorStacticsName;
    }

    private List<listdata>  list;

    public List<listdata> getList() {
        return list;
    }

    public void setList(List<listdata> list) {
        this.list = list;
    }

    public choosedata choose;

    public choosedata getChoose() {
        return choose;
    }

    public void setChoose(choosedata choose) {
        this.choose = choose;
    }

    public String getToOperateTime() {
        return toOperateTime;
    }
    public void setToOperateTime(String toOperateTime) {
        this.toOperateTime = toOperateTime;
    }
    private String singStacticsName;
    private String toId;
    private String toName;
    private String toUserAddress;
    private String toUserName;
    private String toUserPhone;
    private String wayBillNo;
    private String wayBillType;
    private String fromOperateTime;

    public String getFromOperateTime() {
        return fromOperateTime;
    }

    public void setFromOperateTime(String fromOperateTime) {
        this.fromOperateTime = fromOperateTime;
    }

    public int getAutoSign() {
        return autoSign;
    }

    public void setAutoSign(int autoSign) {
        this.autoSign = autoSign;
    }

    public int getBeidouTrack() {
        return beidouTrack;
    }

    public void setBeidouTrack(int beidouTrack) {
        this.beidouTrack = beidouTrack;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getConfirmor() {
        return confirmor;
    }

    public void setConfirmor(String confirmor) {
        this.confirmor = confirmor;
    }

    public String getConfirmorStactics() {
        return confirmorStactics;
    }

    public void setConfirmorStactics(String confirmorStactics) {
        this.confirmorStactics = confirmorStactics;
    }

    public String getFromId() {
        return fromId;
    }
    public String getArrivalStartTime() {
        return arrivalStartTime;
    }

    public void setArrivalStartTime(String arrivalStartTime) {
        this.arrivalStartTime = arrivalStartTime;
    }

    public String getArrivalEndTime() {
        return arrivalEndTime;
    }

    public void setArrivalEndTime(String arrivalEndTime) {
        this.arrivalEndTime = arrivalEndTime;
    }
    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromUserAddress() {
        return fromUserAddress;
    }

    public void setFromUserAddress(String fromUserAddress) {
        this.fromUserAddress = fromUserAddress;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserPhone() {
        return fromUserPhone;
    }

    public void setFromUserPhone(String fromUserPhone) {
        this.fromUserPhone = fromUserPhone;
    }

    public String getHandComName() {
        return handComName;
    }

    public void setHandComName(String handComName) {
        this.handComName = handComName;
    }

    public String getHandCompanyId() {
        return handCompanyId;
    }

    public void setHandCompanyId(String handCompanyId) {
        this.handCompanyId = handCompanyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSingStacticsId() {
        return singStacticsId;
    }

    public void setSingStacticsId(String singStacticsId) {
        this.singStacticsId = singStacticsId;
    }

    public String getSingStacticsName() {
        return singStacticsName;
    }

    public void setSingStacticsName(String singStacticsName) {
        this.singStacticsName = singStacticsName;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToUserAddress() {
        return toUserAddress;
    }

    public void setToUserAddress(String toUserAddress) {
        this.toUserAddress = toUserAddress;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserPhone() {
        return toUserPhone;
    }

    public void setToUserPhone(String toUserPhone) {
        this.toUserPhone = toUserPhone;
    }

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    public String getWayBillType() {
        return wayBillType;
    }

    public void setWayBillType(String wayBillType) {
        this.wayBillType = wayBillType;
    }

    public class choosedata{
        private int check;//是否验货
        private int loadPhotos;//是否拍照
        private int provideInvoice;//是否提供发票
        private int provideLoad;//是否提供装车
        private int provideUnload;//是否提供卸货
        private int unloadPhotos;//是否卸货拍照
        private String makerName;
        private String makerTime;
        private String payProtocolId;
        private String payProtocolName;
        private String remark;
        private String thirdPartyNo;
        private  int outFactoryPhotos;
        private int stopAlarm;
        private int alarmTime;
        private int bindSmartLock;
        private int offLineAlarm;
        private int deviationAlarm;
        private String alarmHzName;
        private String alarmHz;
        private String pushAlarmRole;
        private String pushAlarmRoleName;
        private String checkUserList;
        private String checkUserListName;
        private int openCarType;
        private String carTypeId;
        private String carTypeName;
        private String context;
        private String drivingYears;
        private String travelYears;
        private String relationComName;
        private String relationCom;
        private int fenceClock;

        public int getFenceClock() {
            return fenceClock;
        }

        public void setFenceClock(int fenceClock) {
            this.fenceClock = fenceClock;
        }

        public String getRelationComName() {
            return relationComName;
        }

        public void setRelationComName(String relationComName) {
            this.relationComName = relationComName;
        }

        public String getRelationCom() {
            return relationCom;
        }

        public void setRelationCom(String relationCom) {
            this.relationCom = relationCom;
        }

        public String getDrivingYears() {
            return drivingYears;
        }

        public void setDrivingYears(String drivingYears) {
            this.drivingYears = drivingYears;
        }

        public String getTravelYears() {
            return travelYears;
        }

        public void setTravelYears(String travelYears) {
            this.travelYears = travelYears;
        }

        public int getOpenCarType() {
            return openCarType;
        }

        public void setOpenCarType(int openCarType) {
            this.openCarType = openCarType;
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

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getCheckUserList() {
            return checkUserList;
        }

        public void setCheckUserList(String checkUserList) {
            this.checkUserList = checkUserList;
        }

        public String getCheckUserListName() {
            return checkUserListName;
        }

        public void setCheckUserListName(String checkUserListName) {
            this.checkUserListName = checkUserListName;
        }
        public int getOffLineAlarm() {
            return offLineAlarm;
        }

        public void setOffLineAlarm(int offLineAlarm) {
            this.offLineAlarm = offLineAlarm;
        }

        public String getAlarmHz() {
            return alarmHz;
        }

        public void setAlarmHz(String alarmHz) {
            this.alarmHz = alarmHz;
        }

        public String getPushAlarmRole() {
            return pushAlarmRole;
        }

        public void setPushAlarmRole(String pushAlarmRole) {
            this.pushAlarmRole = pushAlarmRole;
        }

        public String getPushAlarmRoleName() {
            return pushAlarmRoleName;
        }

        public void setPushAlarmRoleName(String pushAlarmRoleName) {
            this.pushAlarmRoleName = pushAlarmRoleName;
        }

        public String getAlarmHzName() {
            return alarmHzName;
        }

        public void setAlarmHzName(String alarmHzName) {
            this.alarmHzName = alarmHzName;
        }

        public int getStopAlarm() {
            return stopAlarm;
        }

        public void setStopAlarm(int stopAlarm) {
            this.stopAlarm = stopAlarm;
        }

        public int getAlarmTime() {
            return alarmTime;
        }

        public void setAlarmTime(int alarmTime) {
            this.alarmTime = alarmTime;
        }

        public int getBindSmartLock() {
            return bindSmartLock;
        }

        public void setBindSmartLock(int bindSmartLock) {
            this.bindSmartLock = bindSmartLock;
        }

        public int getDeviationAlarm() {
            return deviationAlarm;
        }

        public void setDeviationAlarm(int deviationAlarm) {
            this.deviationAlarm = deviationAlarm;
        }

        public int getOutFactoryPhotos() {
            return outFactoryPhotos;
        }

        public void setOutFactoryPhotos(int outFactoryPhotos) {
            this.outFactoryPhotos = outFactoryPhotos;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public int getLoadPhotos() {
            return loadPhotos;
        }

        public void setLoadPhotos(int loadPhotos) {
            this.loadPhotos = loadPhotos;
        }

        public int getProvideInvoice() {
            return provideInvoice;
        }

        public void setProvideInvoice(int provideInvoice) {
            this.provideInvoice = provideInvoice;
        }

        public int getProvideLoad() {
            return provideLoad;
        }

        public void setProvideLoad(int provideLoad) {
            this.provideLoad = provideLoad;
        }

        public int getProvideUnload() {
            return provideUnload;
        }

        public void setProvideUnload(int provideUnload) {
            this.provideUnload = provideUnload;
        }

        public int getUnloadPhotos() {
            return unloadPhotos;
        }

        public void setUnloadPhotos(int unloadPhotos) {
            this.unloadPhotos = unloadPhotos;
        }

        public String getMakerName() {
            return makerName;
        }

        public void setMakerName(String makerName) {
            this.makerName = makerName;
        }

        public String getMakerTime() {
            return makerTime;
        }

        public void setMakerTime(String makerTime) {
            this.makerTime = makerTime;
        }

        public String getPayProtocolId() {
            return payProtocolId;
        }

        public void setPayProtocolId(String payProtocolId) {
            this.payProtocolId = payProtocolId;
        }

        public String getPayProtocolName() {
            return payProtocolName;
        }

        public void setPayProtocolName(String payProtocolName) {
            this.payProtocolName = payProtocolName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getThirdPartyNo() {
            return thirdPartyNo;
        }

        public void setThirdPartyNo(String thirdPartyNo) {
            this.thirdPartyNo = thirdPartyNo;
        }
    }

    public class listdata{
        private String id;
        private String materialsId;
        private String materialsName;
        private double price;
        private double weight;
        private int unit;
        private String unitName;

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaterialsId() {
            return materialsId;
        }

        public void setMaterialsId(String materialsId) {
            this.materialsId = materialsId;
        }

        public String getMaterialsName() {
            return materialsName;
        }

        public void setMaterialsName(String materialsName) {
            this.materialsName = materialsName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }
    }


}
