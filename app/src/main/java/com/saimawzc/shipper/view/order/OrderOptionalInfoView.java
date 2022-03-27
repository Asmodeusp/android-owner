package com.saimawzc.shipper.view.order;

import com.saimawzc.shipper.dto.order.consute.ConsuteDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.view.BaseView;

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

    /**
     * 偏离预警短信对象
     */
    String deviateShortMessage();

    /**
     * 偏离预警站内对象
     */
    String deviateStation();

    /**
     * 偏离预警货主列表
     */
    String deviateCargoOwner();

    /**
     * 离线预警短信对象
     */
    String OfflineShortMessage();

    /**
     * 离线预警站内对象
     */
    String OfflineStation();

    /**
     * 离线预警货主列表
     */
    String OfflineCargoOwner();

    /**
     * 磅单预警短信对象
     */
    String poundListShortMessage();

    /**
     * 磅单预警站内对象
     */
    String poundListStation();

    /**
     * 磅单预警货主列表
     */
    String poundListCargoOwner();

    /**
     * 停留预警短信对象
     */

    String stopShortMessage();

    /**
     * 停留预警站内对象
     */
    String stopStation();

    /**
     * 停留预警货主列表
     */
    String stopCargoOwner();
    /**
     *  高危围栏短信接收人
     */
     String  HighRiskSms () ;;


    /**
     *  高危围栏站内接收人
     */
     String HighRiskStation () ;;
    /**
     *  高危围栏货主列表
     */
     String HighRiskCargo () ;;

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
