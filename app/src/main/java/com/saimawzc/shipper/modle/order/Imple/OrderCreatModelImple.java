package com.saimawzc.shipper.modle.order.Imple;

import android.text.TextUtils;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.modle.order.model.CreatOrderModel;
import com.saimawzc.shipper.ui.order.OrderBasicInfoFragment;
import com.saimawzc.shipper.ui.order.OrderOptionalInfoFragment;
import com.saimawzc.shipper.view.order.CreatOrderView;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrderCreatModelImple extends BasEModeImple implements CreatOrderModel {

    @Override
    public void creatOrder(OrderBasicInfoFragment basicInfoFragment,
                           OrderOptionalInfoFragment optionalInfoFragment, final CreatOrderView view, final BaseListener listener) {
       view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("companyName",basicInfoFragment.getAuthorityDto().getCompanyName());
            jsonObject.put("companyId",basicInfoFragment.getAuthorityDto().getId());
            if(basicInfoFragment.getConsignmentCompanyDto()!=null){
                jsonObject.put("handComName",basicInfoFragment.getConsignmentCompanyDto().getCompanyName());
                jsonObject.put("handCompanyId",basicInfoFragment.getConsignmentCompanyDto().getId());
            }
            jsonObject.put("tranType",basicInfoFragment.trantWay().getId());
            jsonObject.put("wayBillType",basicInfoFragment.getBilltype().getId());
            jsonObject.put("fromName",basicInfoFragment.getSendCompany().getName());
            jsonObject.put("fromId",basicInfoFragment.getSendCompany().getId());
            jsonObject.put("fromUserAddress",basicInfoFragment.getSendAdress().getAllAddress());
            jsonObject.put("fromLocation",basicInfoFragment.getSendAdress().getLocation());
            jsonObject.put("fromOperateTime",basicInfoFragment.getSendBussineTime());
            jsonObject.put("fromUserName",basicInfoFragment.getSendAdress().getContactsName());
            jsonObject.put("fromUserPhone",basicInfoFragment.getSendAdress().getContactsPhone());
            jsonObject.put("confirmorStactics",basicInfoFragment.getreceivesing());
            if(basicInfoFragment.getRouteDto()!=null){
                jsonObject.put("trackRouteId",basicInfoFragment.getRouteDto().getId());
            }

            jsonObject.put("confirmor",basicInfoFragment.getOrderPeopleId());//???????????????

            jsonObject.put("autoSign",basicInfoFragment.isAuthSign());
            jsonObject.put("arrivalStartTime",basicInfoFragment.getarriveTimeStart());
            jsonObject.put("arrivalEndTime",basicInfoFragment.getarriveTimeEnd());
            jsonObject.put("singStacticsId",basicInfoFragment.getsignStrageDto().getId());
            jsonObject.put("singStacticsName",basicInfoFragment.getsignStrageDto().getName());
            jsonObject.put("toId",basicInfoFragment.getReceiveCompany().getId());
            jsonObject.put("toName",basicInfoFragment.getReceiveCompany().getName());
            jsonObject.put("toOperateTime",basicInfoFragment.getReceiveBussineTime());
            jsonObject.put("toUserAddress",basicInfoFragment.getReceiveAdress().getAllAddress());
            jsonObject.put("toUserName",basicInfoFragment.getReceiveAdress().getContactsName());
            jsonObject.put("toUserPhone",basicInfoFragment.getReceiveAdress().getContactsPhone());
            jsonObject.put("toLocation",basicInfoFragment.getReceiveAdress().getLocation());

            jsonObject.put("fromEnclosureType",basicInfoFragment.getSendAdress().getEnclosureType());
            jsonObject.put("fromErrorRange",basicInfoFragment.getSendAdress().getErrorRange());
            jsonObject.put("fromRadius",basicInfoFragment.getSendAdress().getRadius());
            jsonObject.put("fromAddressType",basicInfoFragment.getSendAdress().getAddressTye());
            jsonObject.put("toEnclosureType",basicInfoFragment.getReceiveAdress().getEnclosureType());
            jsonObject.put("toErrorRange",basicInfoFragment.getReceiveAdress().getErrorRange());
            jsonObject.put("toRadius",basicInfoFragment.getReceiveAdress().getRadius());
            jsonObject.put("toAddressType",basicInfoFragment.getReceiveAdress().getAddressTye());
            jsonObject.put("toRegion",basicInfoFragment.getReceiveAdress().getAddress());

            jsonObject.put("fromRegion",basicInfoFragment.getSendAdress().getAddress());
            jsonObject.put("fromProName",basicInfoFragment.getSendAdress().getProName());
            jsonObject.put("fromCityName",basicInfoFragment.getSendAdress().getCityName());
            jsonObject.put("toProName",basicInfoFragment.getReceiveAdress().getProName());
            jsonObject.put("toCityName",basicInfoFragment.getReceiveAdress().getCityName());


                  if(basicInfoFragment.yewuType().equals("??????")){
                      jsonObject.put("businessType",1);
                  }else if(basicInfoFragment.yewuType().equals("??????")){
                      jsonObject.put("businessType",2);
                  }else {
                      jsonObject.put("businessType",3);
                  }


            JSONObject choose=new JSONObject();
            choose.put("remark",optionalInfoFragment.mark());
            choose.put("makerTime",optionalInfoFragment.makeOrderTime());
            choose.put("payProtocolId",0);//????????????ID
            choose.put("payProtocolName","???");
            choose.put("thirdPartyNo",optionalInfoFragment.OrderNum());

            choose.put("provideLoad",optionalInfoFragment.isApplzc());//????????????
            choose.put("provideUnload",optionalInfoFragment.isApplxh());//??????
            choose.put("provideInvoice",optionalInfoFragment.isApplfp());//??????
            choose.put("check",optionalInfoFragment.isApplyh());//??????
            choose.put("loadPhotos",optionalInfoFragment.isApplzhpz());
            choose.put("unloadPhotos",optionalInfoFragment.isApplxhpz());
            choose.put("outFactoryPhotos",optionalInfoFragment.outFactoryPhotos());//??????????????????
            choose.put("stopAlarm",optionalInfoFragment.stopAlarm());//
            choose.put("alarmTime",optionalInfoFragment.stayTime());//
            choose.put("deviationAlarm",optionalInfoFragment.deviationAlarm());//
            choose.put("bindSmartLock",optionalInfoFragment.bindSmartLock());//
            choose.put("pushAlarmRole",optionalInfoFragment.pushAlarmRole());//
            choose.put("alarmHz",optionalInfoFragment.alarmHz());
            choose.put("offLineAlarm",optionalInfoFragment.offLineAlarm());
            choose.put("checkUserList",optionalInfoFragment.checkUserList());
            /**
             * ????????????????????????
             */
            choose.put("deviateShortMessage",optionalInfoFragment.deviateShortMessage());
            /**
             * ????????????????????????
             */
            choose.put("deviateStation",optionalInfoFragment.deviateStation());
            /**
             * ????????????????????????
             */
            choose.put("deviateCargoOwner",optionalInfoFragment.deviateCargoOwner());
            /**
             * ????????????????????????
             */
            choose.put("OfflineShortMessage",optionalInfoFragment.OfflineShortMessage());

            /**
             * ????????????????????????
             */
            choose.put("OfflineStation",optionalInfoFragment.OfflineStation());

            /**
             * ????????????????????????
             */
            choose.put("OfflineCargoOwner",optionalInfoFragment.OfflineCargoOwner());

            /**
             * ????????????????????????
             */
            choose.put("poundListShortMessage",optionalInfoFragment.poundListShortMessage());

            /**
             * ????????????????????????
             */
            choose.put("poundListStation",optionalInfoFragment.poundListStation());

            /**
             * ????????????????????????
             */
            choose.put("poundListCargoOwner",optionalInfoFragment.poundListCargoOwner());

            /**
             * ????????????????????????
             */
            choose.put("stopShortMessage",optionalInfoFragment.stopShortMessage());
            /**
             * ????????????????????????
             */
            choose.put("stopStation",optionalInfoFragment.stopStation());
            /**
             * ????????????????????????
             */
            choose.put("stopCargoOwner",optionalInfoFragment.stopCargoOwner());


            choose.put("openCarType",optionalInfoFragment.openCarType());
            choose.put("carTypeId",optionalInfoFragment.carTypeId());
            choose.put("carTypeName",optionalInfoFragment.carTypeName());
            choose.put("context",optionalInfoFragment.context());
            choose.put("drivingYears",optionalInfoFragment.driverAge());
            choose.put("travelYears",optionalInfoFragment.carAge());
            choose.put("relationCom",optionalInfoFragment.relationCom());
            choose.put("fenceClock",optionalInfoFragment.fenceClock());

            choose.put("openTransport",optionalInfoFragment.openTransport());
            choose.put("openFactorySignIn",optionalInfoFragment.openFactorySignIn());
            choose.put("openArrival",optionalInfoFragment.openArrival());

            choose.put("autoTransport",optionalInfoFragment.autoTransport());
            choose.put("roadLoss",optionalInfoFragment.roadLoss());

            choose.put("inFactoryAlbum",optionalInfoFragment.inFactoryAlbum());
            choose.put("loadAlbum",optionalInfoFragment.loadAlbum());
            choose.put("unloadAlbum",optionalInfoFragment.unloadAlbum());
            choose.put("outFactoryAlbum",optionalInfoFragment.outFactoryAlbum());
            choose.put("arrivalAlbum",optionalInfoFragment.arrivalAlbum());
            choose.put("poundAlarm",optionalInfoFragment.poundAlarm());
            choose.put("highEnclosureId",optionalInfoFragment.highEnclosureId());
            choose.put("highEnclosureName",optionalInfoFragment.highEnclosureName());
            choose.put("beiDouStatus",optionalInfoFragment.beiDouStatus());
            choose.put("sjSignIn",optionalInfoFragment.sjSignIn());
            choose.put("beiDouOffTime",optionalInfoFragment.beiDouOffTime());
            choose.put("spaceTime",optionalInfoFragment.spaceTime());
            /**
             * ????????????????????????
             */
            choose.put("deviateShortMessage",optionalInfoFragment.deviateShortMessage());
            /**
             * ????????????????????????
             */
            choose.put("deviateStation",optionalInfoFragment.deviateStation());
            /**
             * ????????????????????????
             */
            choose.put("deviateCargoOwner",optionalInfoFragment.deviateCargoOwner());
            /**
             * ????????????????????????
             */
            choose.put("OfflineShortMessage",optionalInfoFragment.OfflineShortMessage());

            /**
             * ????????????????????????
             */
            choose.put("OfflineStation",optionalInfoFragment.OfflineStation());

            /**
             * ????????????????????????
             */
            choose.put("OfflineCargoOwner",optionalInfoFragment.OfflineCargoOwner());

            /**
             * ????????????????????????
             */
            choose.put("poundListShortMessage",optionalInfoFragment.poundListShortMessage());

            /**
             * ????????????????????????
             */
            choose.put("poundListStation",optionalInfoFragment.poundListStation());

            /**
             * ????????????????????????
             */
            choose.put("poundListCargoOwner",optionalInfoFragment.poundListCargoOwner());

            /**
             * ????????????????????????
             */
            choose.put("stopShortMessage",optionalInfoFragment.stopShortMessage());
            /**
             * ????????????????????????
             */
            choose.put("stopStation",optionalInfoFragment.stopStation());
            /**
             * ????????????????????????
             */
            choose.put("stopCargoOwner",optionalInfoFragment.stopCargoOwner());
            /**
             *  ???????????????????????????
             */
            choose.put("HighRiskSms",optionalInfoFragment.HighRiskSms());

            /**
             *  ???????????????????????????
             */
            choose.put("HighRiskStation",optionalInfoFragment.HighRiskStation());
            /**
             *  ????????????????????????
             */
            choose.put("HighRiskCargo",optionalInfoFragment.HighRiskCargo());
            jsonObject.put("choose",choose);

            JSONArray array=new JSONArray();
            JSONObject arrObj=new JSONObject();
            arrObj.put("materialsName",basicInfoFragment.getGoodName().getName());

            arrObj.put("materialsId",basicInfoFragment.getGoodName().getId());
            arrObj.put("price",basicInfoFragment.getPrice());
            arrObj.put("goodsPrice",basicInfoFragment.getGoodPrice());
            if(TextUtils.isEmpty(basicInfoFragment.getWeightUtil())){
                arrObj.put("unit",1+"");//1??? 2 ???
            }else {
                arrObj.put("unit",basicInfoFragment.getWeightUtil());//1??? 2 ???
            }
            arrObj.put("weight",basicInfoFragment.getNum());
            array.put(arrObj);

            jsonObject.put("list",array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.addOrder(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);

            }
        });
    }

    @Override
    public void editOrder(final OrderBasicInfoFragment basicInfoFragment,
                          final OrderOptionalInfoFragment optionalInfoFragment, final CreatOrderView view, final BaseListener listener,String id) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("companyName",basicInfoFragment.getAuthorityDto().getCompanyName());
            jsonObject.put("companyId",basicInfoFragment.getAuthorityDto().getId());

          if(basicInfoFragment.getConsignmentCompanyDto()!=null){
              jsonObject.put("handComName",basicInfoFragment.getConsignmentCompanyDto().getCompanyName());
              jsonObject.put("handCompanyId",basicInfoFragment.getConsignmentCompanyDto().getId());
          }
            if(basicInfoFragment.getRouteDto()!=null){
                jsonObject.put("trackRouteId",basicInfoFragment.getRouteDto().getId());
            }
            jsonObject.put("tranType",basicInfoFragment.trantWay().getId());
            jsonObject.put("wayBillType",basicInfoFragment.getBilltype().getId());
            jsonObject.put("fromName",basicInfoFragment.getSendCompany().getName());
            jsonObject.put("fromId",basicInfoFragment.getSendCompany().getId());
            jsonObject.put("fromUserAddress",basicInfoFragment.getSendAdress().getAllAddress());
            jsonObject.put("fromLocation",basicInfoFragment.getSendAdress().getLocation());
            jsonObject.put("fromOperateTime",basicInfoFragment.getSendBussineTime());
            jsonObject.put("fromUserName",basicInfoFragment.getSendAdress().getContactsName());
            jsonObject.put("fromUserPhone",basicInfoFragment.getSendAdress().getContactsPhone());
            jsonObject.put("confirmorStactics",basicInfoFragment.getreceivesing());
            jsonObject.put("confirmor",basicInfoFragment.getOrderPeopleId());//???????????????
            jsonObject.put("autoSign",basicInfoFragment.isAuthSign());
            jsonObject.put("arrivalStartTime",basicInfoFragment.getarriveTimeStart());
            jsonObject.put("arrivalEndTime",basicInfoFragment.getarriveTimeEnd());
            jsonObject.put("singStacticsId",basicInfoFragment.getsignStrageDto().getId());
            jsonObject.put("singStacticsName",basicInfoFragment.getsignStrageDto().getName());
            jsonObject.put("toId",basicInfoFragment.getReceiveCompany().getId());
            jsonObject.put("toName",basicInfoFragment.getReceiveCompany().getName());
            jsonObject.put("toOperateTime",basicInfoFragment.getReceiveBussineTime());
            jsonObject.put("toUserAddress",basicInfoFragment.getReceiveAdress().getAllAddress());
            jsonObject.put("toUserName",basicInfoFragment.getReceiveAdress().getContactsName());
            jsonObject.put("toUserPhone",basicInfoFragment.getReceiveAdress().getContactsPhone());
            jsonObject.put("toLocation",basicInfoFragment.getReceiveAdress().getLocation());
            jsonObject.put("toRegion",basicInfoFragment.getReceiveAdress().getAddress());
            jsonObject.put("fromRegion",basicInfoFragment.getSendAdress().getAddress());
            jsonObject.put("fromProName",basicInfoFragment.getSendAdress().getProName());
            jsonObject.put("fromCityName",basicInfoFragment.getSendAdress().getCityName());
            jsonObject.put("toProName",basicInfoFragment.getReceiveAdress().getProName());
            jsonObject.put("toCityName",basicInfoFragment.getReceiveAdress().getCityName());
            jsonObject.put("fromEnclosureType",basicInfoFragment.getSendAdress().getEnclosureType());
            jsonObject.put("fromErrorRange",basicInfoFragment.getSendAdress().getErrorRange());
            jsonObject.put("fromRadius",basicInfoFragment.getSendAdress().getRadius());
            jsonObject.put("fromAddressType",basicInfoFragment.getSendAdress().getAddressTye());
            jsonObject.put("toEnclosureType",basicInfoFragment.getReceiveAdress().getEnclosureType());
            jsonObject.put("toErrorRange",basicInfoFragment.getReceiveAdress().getErrorRange());
            jsonObject.put("toRadius",basicInfoFragment.getReceiveAdress().getRadius());
            jsonObject.put("toAddressType",basicInfoFragment.getReceiveAdress().getAddressTye());



            JSONObject choose=new JSONObject();
            choose.put("remark",optionalInfoFragment.mark());
            choose.put("makerTime",optionalInfoFragment.makeOrderTime());
            choose.put("payProtocolId",0);//????????????ID
            choose.put("payProtocolName","???");

            choose.put("thirdPartyNo",optionalInfoFragment.OrderNum());

            choose.put("provideLoad",optionalInfoFragment.isApplzc());
            choose.put("provideUnload",optionalInfoFragment.isApplxh());
            choose.put("provideInvoice",optionalInfoFragment.isApplfp());
            choose.put("check",optionalInfoFragment.isApplyh());
            choose.put("loadPhotos",optionalInfoFragment.isApplzhpz());
            choose.put("unloadPhotos",optionalInfoFragment.isApplxhpz());

            choose.put("outFactoryPhotos",optionalInfoFragment.outFactoryPhotos());//??????????????????
            choose.put("stopAlarm",optionalInfoFragment.stopAlarm());//
            choose.put("alarmTime",optionalInfoFragment.stayTime());//
            choose.put("deviationAlarm",optionalInfoFragment.deviationAlarm());//
            choose.put("bindSmartLock",optionalInfoFragment.bindSmartLock());//
            choose.put("pushAlarmRole",optionalInfoFragment.pushAlarmRole());//
            choose.put("alarmHz",optionalInfoFragment.alarmHz());
            choose.put("offLineAlarm",optionalInfoFragment.offLineAlarm());
            choose.put("checkUserList",optionalInfoFragment.checkUserList());

                if(basicInfoFragment.yewuType().equals("??????")){
                    jsonObject.put("businessType",1);
                }else if(basicInfoFragment.yewuType().equals("??????")){
                    jsonObject.put("businessType",2);
                }else {
                    jsonObject.put("businessType",3);
                }

            choose.put("makerName",optionalInfoFragment.makePeople());

            choose.put("openCarType",optionalInfoFragment.openCarType());
            choose.put("carTypeId",optionalInfoFragment.carTypeId());
            choose.put("carTypeName",optionalInfoFragment.carTypeName());
            choose.put("context",optionalInfoFragment.context());
            choose.put("drivingYears",optionalInfoFragment.driverAge());
            choose.put("travelYears",optionalInfoFragment.carAge());
            choose.put("relationCom",optionalInfoFragment.relationCom());
            choose.put("fenceClock",optionalInfoFragment.fenceClock());

            choose.put("openTransport",optionalInfoFragment.openTransport());
            choose.put("openFactorySignIn",optionalInfoFragment.openFactorySignIn());
            choose.put("openArrival",optionalInfoFragment.openArrival());
            choose.put("autoTransport",optionalInfoFragment.autoTransport());
            choose.put("roadLoss",optionalInfoFragment.roadLoss());
            choose.put("inFactoryAlbum",optionalInfoFragment.inFactoryAlbum());
            choose.put("loadAlbum",optionalInfoFragment.loadAlbum());
            choose.put("unloadAlbum",optionalInfoFragment.unloadAlbum());
            choose.put("outFactoryAlbum",optionalInfoFragment.outFactoryAlbum());
            choose.put("arrivalAlbum",optionalInfoFragment.arrivalAlbum());
            choose.put("poundAlarm",optionalInfoFragment.poundAlarm());
            choose.put("highEnclosureId",optionalInfoFragment.highEnclosureId());
            choose.put("highEnclosureName",optionalInfoFragment.highEnclosureName());
            choose.put("beiDouStatus",optionalInfoFragment.beiDouStatus());
            choose.put("sjSignIn",optionalInfoFragment.sjSignIn());
            choose.put("beiDouOffTime",optionalInfoFragment.beiDouOffTime());
            choose.put("spaceTime",optionalInfoFragment.spaceTime());
            /**
             * ????????????????????????
             */
            choose.put("deviateShortMessage",optionalInfoFragment.deviateShortMessage());
            /**
             * ????????????????????????
             */
            choose.put("deviateStation",optionalInfoFragment.deviateStation());
            /**
             * ????????????????????????
             */
            choose.put("deviateCargoOwner",optionalInfoFragment.deviateCargoOwner());
            /**
             * ????????????????????????
             */
            choose.put("OfflineShortMessage",optionalInfoFragment.OfflineShortMessage());

            /**
             * ????????????????????????
             */
            choose.put("OfflineStation",optionalInfoFragment.OfflineStation());

            /**
             * ????????????????????????
             */
            choose.put("OfflineCargoOwner",optionalInfoFragment.OfflineCargoOwner());

            /**
             * ????????????????????????
             */
            choose.put("poundListShortMessage",optionalInfoFragment.poundListShortMessage());

            /**
             * ????????????????????????
             */
            choose.put("poundListStation",optionalInfoFragment.poundListStation());

            /**
             * ????????????????????????
             */
            choose.put("poundListCargoOwner",optionalInfoFragment.poundListCargoOwner());

            /**
             * ????????????????????????
             */
            choose.put("stopShortMessage",optionalInfoFragment.stopShortMessage());
            /**
             * ????????????????????????
             */
            choose.put("stopStation",optionalInfoFragment.stopStation());
            /**
             * ????????????????????????
             */
            choose.put("stopCargoOwner",optionalInfoFragment.stopCargoOwner());
            /**
             *  ???????????????????????????
             */
            choose.put("HighRiskSms",optionalInfoFragment.HighRiskSms());

            /**
             *  ???????????????????????????
             */
            choose.put("HighRiskStation",optionalInfoFragment.HighRiskStation());
            /**
             *  ????????????????????????
             */
            choose.put("HighRiskCargo",optionalInfoFragment.HighRiskCargo());
            jsonObject.put("choose",choose);
            JSONArray array=new JSONArray();
            JSONObject arrObj=new JSONObject();
            arrObj.put("materialsName",basicInfoFragment.getGoodName().getName());

            arrObj.put("materialsId",basicInfoFragment.getGoodName().getId());
            arrObj.put("price",basicInfoFragment.getPrice());
            arrObj.put("goodsPrice",basicInfoFragment.getGoodPrice());
            if(TextUtils.isEmpty(basicInfoFragment.getWeightUtil())){
                arrObj.put("unit",1+"");//1??? 2 ???
            }else {
                arrObj.put("unit",basicInfoFragment.getWeightUtil());//1??? 2 ???
            }
            arrObj.put("weight",basicInfoFragment.getNum());
            array.put(arrObj);

            jsonObject.put("list",array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.editOrder(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);

            }
        });
    }

   /*****????????????*****/
    @Override
    public void consute(OrderBasicInfoFragment basicInfoFragment, OrderOptionalInfoFragment optionalInfoFragment, final CreatOrderView view,final BaseListener listener) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("companyName",basicInfoFragment.getAuthorityDto().getCompanyName());
            jsonObject.put("companyId",basicInfoFragment.getAuthorityDto().getId());

            if(basicInfoFragment.getConsignmentCompanyDto()!=null){
                jsonObject.put("handComName",basicInfoFragment.getConsignmentCompanyDto().getCompanyName());
                jsonObject.put("handCompanyId",basicInfoFragment.getConsignmentCompanyDto().getId());
            }
            if(basicInfoFragment.getRouteDto()!=null){
                jsonObject.put("trackRouteId",basicInfoFragment.getRouteDto().getId());
            }
            jsonObject.put("tranType",basicInfoFragment.trantWay().getId());
            jsonObject.put("wayBillType",basicInfoFragment.getBilltype().getId());
            jsonObject.put("fromName",basicInfoFragment.getSendCompany().getName());
            jsonObject.put("fromId",basicInfoFragment.getSendCompany().getId());
            jsonObject.put("fromUserAddress",basicInfoFragment.getSendAdress().getAllAddress());
            jsonObject.put("fromLocation",basicInfoFragment.getSendAdress().getLocation());
            jsonObject.put("fromOperateTime",basicInfoFragment.getSendBussineTime());
            jsonObject.put("fromUserName",basicInfoFragment.getSendAdress().getContactsName());
            jsonObject.put("fromUserPhone",basicInfoFragment.getSendAdress().getContactsPhone());
            jsonObject.put("confirmorStactics",basicInfoFragment.getreceivesing());
            jsonObject.put("confirmor",basicInfoFragment.getOrderPeopleId());//???????????????
            jsonObject.put("autoSign",basicInfoFragment.isAuthSign());
            jsonObject.put("arrivalStartTime",basicInfoFragment.getarriveTimeStart());
            jsonObject.put("arrivalEndTime",basicInfoFragment.getarriveTimeEnd());
            jsonObject.put("singStacticsId",basicInfoFragment.getsignStrageDto().getId());
            jsonObject.put("singStacticsName",basicInfoFragment.getsignStrageDto().getName());
            jsonObject.put("toId",basicInfoFragment.getReceiveCompany().getId());
            jsonObject.put("toName",basicInfoFragment.getReceiveCompany().getName());
            jsonObject.put("toOperateTime",basicInfoFragment.getReceiveBussineTime());
            jsonObject.put("toUserAddress",basicInfoFragment.getReceiveAdress().getAllAddress());
            jsonObject.put("toUserName",basicInfoFragment.getReceiveAdress().getContactsName());
            jsonObject.put("toUserPhone",basicInfoFragment.getReceiveAdress().getContactsPhone());
            jsonObject.put("toLocation",basicInfoFragment.getReceiveAdress().getLocation());
            jsonObject.put("toRegion",basicInfoFragment.getReceiveAdress().getAddress());
            jsonObject.put("fromRegion",basicInfoFragment.getSendAdress().getAddress());
            jsonObject.put("fromProName",basicInfoFragment.getSendAdress().getProName());
            jsonObject.put("fromCityName",basicInfoFragment.getSendAdress().getCityName());
            jsonObject.put("toProName",basicInfoFragment.getReceiveAdress().getProName());
            jsonObject.put("toCityName",basicInfoFragment.getReceiveAdress().getCityName());
            jsonObject.put("fromEnclosureType",basicInfoFragment.getSendAdress().getEnclosureType());
            jsonObject.put("fromErrorRange",basicInfoFragment.getSendAdress().getErrorRange());
            jsonObject.put("fromRadius",basicInfoFragment.getSendAdress().getRadius());
            jsonObject.put("fromAddressType",basicInfoFragment.getSendAdress().getAddressTye());
            jsonObject.put("toEnclosureType",basicInfoFragment.getReceiveAdress().getEnclosureType());
            jsonObject.put("toErrorRange",basicInfoFragment.getReceiveAdress().getErrorRange());
            jsonObject.put("toRadius",basicInfoFragment.getReceiveAdress().getRadius());
            jsonObject.put("toAddressType",basicInfoFragment.getReceiveAdress().getAddressTye());

            JSONObject choose=new JSONObject();
            choose.put("remark",optionalInfoFragment.mark());
            choose.put("makerTime",optionalInfoFragment.makeOrderTime());
            choose.put("payProtocolId",0);//????????????ID
            choose.put("payProtocolName","???");

            choose.put("thirdPartyNo",optionalInfoFragment.OrderNum());

            choose.put("provideLoad",optionalInfoFragment.isApplzc());
            choose.put("provideUnload",optionalInfoFragment.isApplxh());
            choose.put("provideInvoice",optionalInfoFragment.isApplfp());
            choose.put("check",optionalInfoFragment.isApplyh());
            choose.put("loadPhotos",optionalInfoFragment.isApplzhpz());
            choose.put("unloadPhotos",optionalInfoFragment.isApplxhpz());

            choose.put("outFactoryPhotos",optionalInfoFragment.outFactoryPhotos());//??????????????????
            choose.put("stopAlarm",optionalInfoFragment.stopAlarm());//
            choose.put("alarmTime",optionalInfoFragment.stayTime());//
            choose.put("deviationAlarm",optionalInfoFragment.deviationAlarm());//
            choose.put("bindSmartLock",optionalInfoFragment.bindSmartLock());//
            choose.put("pushAlarmRole",optionalInfoFragment.pushAlarmRole());//
            choose.put("alarmHz",optionalInfoFragment.alarmHz());
            choose.put("offLineAlarm",optionalInfoFragment.offLineAlarm());
            choose.put("roadLoss",optionalInfoFragment.roadLoss());
                if(basicInfoFragment.yewuType().equals("??????")){
                    jsonObject.put("businessType",1);
                }else if(basicInfoFragment.yewuType().equals("??????")){
                    jsonObject.put("businessType",2);
                }else {
                    jsonObject.put("businessType",3);
                }

            choose.put("makerName",optionalInfoFragment.makePeople());
            choose.put("checkUserList",optionalInfoFragment.checkUserList());

            choose.put("openCarType",optionalInfoFragment.openCarType());
            choose.put("carTypeId",optionalInfoFragment.carTypeId());
            choose.put("carTypeName",optionalInfoFragment.carTypeName());
            choose.put("context",optionalInfoFragment.context());
            choose.put("drivingYears",optionalInfoFragment.driverAge());
            choose.put("travelYears",optionalInfoFragment.carAge());
            choose.put("relationCom",optionalInfoFragment.relationCom());
            choose.put("fenceClock",optionalInfoFragment.fenceClock());

            choose.put("openTransport",optionalInfoFragment.openTransport());
            choose.put("openFactorySignIn",optionalInfoFragment.openFactorySignIn());
            choose.put("openArrival",optionalInfoFragment.openArrival());
            choose.put("autoTransport",optionalInfoFragment.autoTransport());
            choose.put("inFactoryAlbum",optionalInfoFragment.inFactoryAlbum());
            choose.put("loadAlbum",optionalInfoFragment.loadAlbum());
            choose.put("unloadAlbum",optionalInfoFragment.unloadAlbum());
            choose.put("outFactoryAlbum",optionalInfoFragment.outFactoryAlbum());
            choose.put("arrivalAlbum",optionalInfoFragment.arrivalAlbum());
            choose.put("poundAlarm",optionalInfoFragment.poundAlarm());
            choose.put("highEnclosureId",optionalInfoFragment.highEnclosureId());
            choose.put("highEnclosureName",optionalInfoFragment.highEnclosureName());
            choose.put("beiDouStatus",optionalInfoFragment.beiDouStatus());
            choose.put("sjSignIn",optionalInfoFragment.sjSignIn());
            choose.put("beiDouOffTime",optionalInfoFragment.beiDouOffTime());
            choose.put("spaceTime",optionalInfoFragment.spaceTime());
            /**
             * ????????????????????????
             */
            choose.put("deviateShortMessage",optionalInfoFragment.deviateShortMessage());
            /**
             * ????????????????????????
             */
            choose.put("deviateStation",optionalInfoFragment.deviateStation());
            /**
             * ????????????????????????
             */
            choose.put("deviateCargoOwner",optionalInfoFragment.deviateCargoOwner());
            /**
             * ????????????????????????
             */
            choose.put("OfflineShortMessage",optionalInfoFragment.OfflineShortMessage());

            /**
             * ????????????????????????
             */
            choose.put("OfflineStation",optionalInfoFragment.OfflineStation());

            /**
             * ????????????????????????
             */
            choose.put("OfflineCargoOwner",optionalInfoFragment.OfflineCargoOwner());

            /**
             * ????????????????????????
             */
            choose.put("poundListShortMessage",optionalInfoFragment.poundListShortMessage());

            /**
             * ????????????????????????
             */
            choose.put("poundListStation",optionalInfoFragment.poundListStation());

            /**
             * ????????????????????????
             */
            choose.put("poundListCargoOwner",optionalInfoFragment.poundListCargoOwner());

            /**
             * ????????????????????????
             */
            choose.put("stopShortMessage",optionalInfoFragment.stopShortMessage());
            /**
             * ????????????????????????
             */
            choose.put("stopStation",optionalInfoFragment.stopStation());
            /**
             * ????????????????????????
             */
            choose.put("stopCargoOwner",optionalInfoFragment.stopCargoOwner());
            /**
             *  ???????????????????????????
             */
            choose.put("HighRiskSms",optionalInfoFragment.HighRiskSms());

            /**
             *  ???????????????????????????
             */
            choose.put("HighRiskStation",optionalInfoFragment.HighRiskStation());
            /**
             *  ????????????????????????
             */
            choose.put("HighRiskCargo",optionalInfoFragment.HighRiskCargo());
            jsonObject.put("choose",choose);
            JSONArray array=new JSONArray();
            JSONObject arrObj=new JSONObject();
            arrObj.put("materialsName",basicInfoFragment.getGoodName().getName());

            arrObj.put("materialsId",basicInfoFragment.getGoodName().getId());
            arrObj.put("price",basicInfoFragment.getPrice());
            arrObj.put("goodsPrice",basicInfoFragment.getGoodPrice());
            if(TextUtils.isEmpty(basicInfoFragment.getWeightUtil())){
                arrObj.put("unit",1+"");//1??? 2 ???
            }else {
                arrObj.put("unit",basicInfoFragment.getWeightUtil());//1??? 2 ???
            }
            arrObj.put("weight",basicInfoFragment.getNum());
            array.put(arrObj);

            jsonObject.put("list",array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        orderApi.consute(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                view.dissLoading();
                listener.successful();
            }

            @Override
            public void fail(String code, String message) {
                view.dissLoading();
                view.Toast(message);

            }
        });
    }
}
