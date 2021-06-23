package com.saimawzc.shipper.view.order;

import android.widget.LinearLayout;

import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.creatorder.AdressDto;
import com.saimawzc.shipper.dto.order.creatorder.AuthorityDtoSerializ;
import com.saimawzc.shipper.dto.order.creatorder.ChooseRouteDto;
import com.saimawzc.shipper.dto.order.creatorder.GoodsCompanyDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.UntilDto;
import com.saimawzc.shipper.dto.order.creatorder.waybill.AddWayBillGoodsDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface OrderBasicWayBillInfoView extends BaseView {

    AuthorityDtoSerializ getAuthorityDto();//获取组织机构
    ConsignmentCompanyDto getConsignmentCompanyDto();//获取托运公司
    GoodsCompanyDto getBilltype();//单据类型
    GoodsCompanyDto getSendCompany();//获取发货商
    GoodsCompanyDto getReceiveCompany();//获取收货商
    AdressDto getSendAdress();//获取发货地址
    AdressDto getReceiveAdress();//获取收货地址

    GoodsCompanyDto getorderPeopelDto();//收货确认人
    GoodsCompanyDto getsignStrageDto();//获取签收策略
    String getSendBussineTime();//营业时间
    ChooseRouteDto getRouteDto();
    String getReceiveBussineTime();//营业时间
    String isAuthSign();//是否自动签收
    String getreceivesing();//收货策略
    String getarriveTimeStart();//
    String getarriveTimeEnd();
    String getOrderPeopleId();

    String getIstuoyun();

    List<AddWayBillGoodsDto>getGoodList();

    GoodsCompanyDto trangWay();

    void getOrderDelation(OrderDelationDto dto);
    void getUntil(List<UntilDto>dtos);

}
