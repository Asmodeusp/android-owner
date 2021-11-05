package com.saimawzc.shipper.view.order;

import com.saimawzc.shipper.dto.order.consute.ConsuteDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.view.BaseView;

import org.json.JSONArray;

public interface OrderOptionalInfoView extends BaseView {

    String makeOrderTime();//制单时间
    String makePeople();//制单人
    String OrderNum();//客商订单号
    int isApplzc();//1是 2不是
    int isApplxh();//1是 2不是
    int isApplfp();//1是 2不是
    int isApplyh();//1是 2不是
    int isApplzhpz();//1是 2不是
    int isApplxhpz();//1是 2不是

    int inFactoryAlbum();//1是 2不是
    int loadAlbum();//1是 2不是
    int unloadAlbum();//1是 2不是
    int outFactoryAlbum();//1是 2不是
    int arrivalAlbum();//1是 2不是
    int poundAlarm();

    int bindSmartLock();
    int outFactoryPhotos();//出厂榜单
    int stopAlarm();
    int offLineAlarm();
    int deviationAlarm();
    int openCarType();
    int sjSignIn();
    String carTypeId();
    String carTypeName();
    String context();

    String stayTime();
    String pushAlarmRole();
    String alarmHz();
    String xieyi();
    String mark();
    String checkUserList();
    String driverAge();
    String carAge();
    String relationCom();
    String roadLoss();
    void getOrderDelation(OrderDelationDto dto);
    boolean getIschoose();
    void getConsuteDealtion(ConsuteDelationDto dto);
    int fenceClock();

    int openTransport();
    int openFactorySignIn();
    int openArrival();
    int autoTransport();
    String highEnclosureId();
    String highEnclosureName();
    int beiDouStatus();
    String beiDouOffTime();
    String spaceTime();
}
