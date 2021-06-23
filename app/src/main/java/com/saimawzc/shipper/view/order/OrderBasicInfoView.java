package com.saimawzc.shipper.view.order;

import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.consute.ConsuteDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.AdressDto;
import com.saimawzc.shipper.dto.order.creatorder.AuthorityDtoSerializ;
import com.saimawzc.shipper.dto.order.creatorder.GoodsCompanyDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.ChooseRouteDto;
import com.saimawzc.shipper.dto.order.creatorder.UntilDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface OrderBasicInfoView extends BaseView {

    AuthorityDtoSerializ getAuthorityDto();//获取组织机构
    ConsignmentCompanyDto getConsignmentCompanyDto();//获取托运公司
    GoodsCompanyDto getBilltype();//单据类型
    GoodsCompanyDto getSendCompany();//获取发货商
    GoodsCompanyDto getReceiveCompany();//获取收货商
    AdressDto getSendAdress();//获取发货地址
    AdressDto getReceiveAdress();//获取收货地址

    GoodsCompanyDto getorderPeopelDto();//收货确认人
    GoodsCompanyDto getsignStrageDto();//获取签收策略
    GoodsCompanyDto getGoodName();//获取货物名称
    String getSendBussineTime();//营业时间
    String getReceiveBussineTime();//营业时间
    String isAuthSign();//是否自动签收
    String getreceivesing();//收货策略
    String getarriveTimeStart();//
    String getarriveTimeEnd();
    ChooseRouteDto getRouteDto();

    String getNum();
    String getPrice();
    String getOrderPeopleId();

    String getWeightUtil();

    void getOrderDelation(OrderDelationDto dto);

    String yewuType();//业务类型
    GoodsCompanyDto trantWay();//运输方式
    void getUntil(List<UntilDto> untilDtos);
    void getConsuteDelation(ConsuteDelationDto dto);
}
