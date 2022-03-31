package com.saimawzc.shipper.weight.utils.api;

import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.dto.consign.ConsignDto;
import com.saimawzc.shipper.dto.order.BiddingpageDto;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.dto.order.ConsultDto;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.dto.order.OrderBiddenHistoryDto;
import com.saimawzc.shipper.dto.order.PlanOrderPageDto;
import com.saimawzc.shipper.dto.order.SendCarDto;
import com.saimawzc.shipper.dto.order.SignWeightDto;
import com.saimawzc.shipper.dto.order.bidd.BiddFirstDto;
import com.saimawzc.shipper.dto.order.bidd.PlanBiddDto;
import com.saimawzc.shipper.dto.order.bidd.RankPageDto;
import com.saimawzc.shipper.dto.order.consute.ConsuteDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.AdressDto;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.ChooseRouteDto;
import com.saimawzc.shipper.dto.order.creatorder.ContarctsDto;
import com.saimawzc.shipper.dto.order.creatorder.GoodsCompanyDto;
import com.saimawzc.shipper.dto.order.creatorder.GoodsHzListDto;
import com.saimawzc.shipper.dto.order.creatorder.MapDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderCarrierGroupDto;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.dto.order.creatorder.UntilDto;
import com.saimawzc.shipper.dto.order.manage.OrderManageDto;
import com.saimawzc.shipper.dto.order.manage.OrderManageRoleDto;
import com.saimawzc.shipper.dto.order.selectEndStatuesDto;
import com.saimawzc.shipper.dto.order.send.ChangeCarDto;
import com.saimawzc.shipper.dto.order.send.LogistcsDto;
import com.saimawzc.shipper.dto.order.send.RouteDto;
import com.saimawzc.shipper.dto.order.send.SendCarDelationDto;
import com.saimawzc.shipper.dto.order.send.WarnInfoDto;
import com.saimawzc.shipper.dto.order.wallbill.OrderInventoryDto;
import com.saimawzc.shipper.dto.order.wallbill.OrderWayBillDto;
import com.saimawzc.shipper.dto.order.wallbill.WayBillAssignDto;
import com.saimawzc.shipper.dto.travel.BeidouTravelDto;
import com.saimawzc.shipper.dto.travel.PresupTravelDto;
import com.saimawzc.shipper.weight.utils.http.JsonResult;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrderApi {



    //获取组织机构
    @Headers("Content-Type: application/json")
    @POST("oms/hz/company/selectByCompany")
    Call<String> getAuthorityList();

    //获取托运公司
    @Headers("Content-Type: application/json")
    @POST("oms/hz/company/selectShippingCompany")
    Call<JsonResult<List<ConsignmentCompanyDto>>> getConsignmentCompanyList();

    //获取收货商和发货商
    @Headers("Content-Type: application/json")
    @POST("oms/hz/customer/selectCustomer")
    Call<JsonResult<List<GoodsCompanyDto>>> getGoodsCompanyList(@Body RequestBody array);

    //获取单据类型
    @Headers("Content-Type: application/json")
    @POST("oms/hz/omsWayBill/selectWayBillType")
    Call<JsonResult<List<GoodsCompanyDto>>> getBillType(@Body RequestBody array);
    //获取运输方式
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/getTranType")
    Call<JsonResult<List<GoodsCompanyDto>>> getTrantWay(@Body RequestBody array);


    //获取货主列表
    @Headers("Content-Type: application/json")
    @POST("admin/userCardHz/selectByCompanyId")
    Call<JsonResult<GoodsHzListDto>> getHzList(@Body RequestBody array);

    //获取验货人列表
    @Headers("Content-Type: application/json")
    @POST("admin/userCardShr/selectShrByCompany")
    Call<JsonResult<GoodsHzListDto>> getyhrlist(@Body RequestBody array);

    //获取规划路径
    @Headers("Content-Type: application/json")
    @POST("oms/trackRoute/selectCompanyTrack")
    Call<JsonResult<List<ChooseRouteDto>>> getRouteList(@Body RequestBody array);

    //获取签收策略
    @Headers("Content-Type: application/json")
    @POST("oms/sarnSingin/selectAutoSignInName")
    Call<JsonResult<List<GoodsCompanyDto>>> getsignStrageList(@Body RequestBody array);

    //获取收货确认人
    @Headers("Content-Type: application/json")
    @POST("oms/hz/omsWayBill/selectConfirmor")
    Call<JsonResult<List<GoodsCompanyDto>>> getOrderPeopleList(@Body RequestBody array);

    //新增发货商和收货商
    @Headers("Content-Type: application/json")
    @POST("oms/hz/customer/addCustomer")
    Call<JsonResult<EmptyDto> >addCompany(@Body RequestBody array);

    //选择联系人
    @Headers("Content-Type: application/json")
    @POST("oms/hz/customer/selectContacts")
    Call<JsonResult<List<ContarctsDto>> >getContracts(@Body RequestBody array);
    //新增联系人
    @Headers("Content-Type: application/json")
    @POST("oms/hz/customer/addContacts")
    Call<JsonResult<EmptyDto> >addContracts(@Body RequestBody array);
    //新增地址
    @Headers("Content-Type: application/json")
    @POST("oms/hz/customer/addAddress")
    Call<JsonResult<EmptyDto> >addAdress(@Body RequestBody array);

    //获取地址列表
    @Headers("Content-Type: application/json")
    @POST("oms/customer/selectAddress")
    Call<JsonResult<List<AdressDto>> >getAdressList(@Body RequestBody array);

    //获取货物名称
    @Headers("Content-Type: application/json")
    @POST("oms/materials/selectAddress")
    Call<JsonResult<List<GoodsCompanyDto>> >getGoodNameList(@Body RequestBody array);

    //新增计划订单
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("oms/hz/wayPlanBill/addWayBillPlan")
    Call<JsonResult<EmptyDto> >addOrder(@Body RequestBody array);

    //编辑计划订单
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("oms/hz/wayPlanBill/editWayBillPlan")
    Call<JsonResult<EmptyDto> >editOrder(@Body RequestBody array);

    //参照生成
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("oms/hz/wayPlanBill/createWayBillByTypeCommit")
    Call<JsonResult<EmptyDto> >consute(@Body RequestBody array);
    //计划订单列表
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/selectBillPlan")
    Call<JsonResult<PlanOrderPageDto> >getPlanOrderlist(@Body RequestBody array);

    //删除计划订单
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/deleteWayBillPlan")
    Call<JsonResult<EmptyDto> >deletePlanOrder(@Body RequestBody array);

    //获取订单详情
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/selectWayBillPlanById")
    Call<JsonResult<OrderDelationDto> >getOrderDelation(@Body RequestBody array);


    //获取参照生成
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/createWayBillByType")
    Call<JsonResult<ConsuteDelationDto> >getConsuteDelation(@Body RequestBody array);

    //获取单位
    @Headers("Content-Type: application/json")
    @POST("oms/common/wayBill/getUnit")
    Call<JsonResult<List<UntilDto>> >getUntilList(@Body RequestBody array);

    //审核
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/toExamineWayBillPlan")
    Call<JsonResult<EmptyDto> >shOrder(@Body RequestBody array);

    //获取承运商分组
    @Headers("Content-Type: application/json")
    @POST("oms/hz/cysGroup/selectCysGroup")
    Call<JsonResult<List<OrderCarrierGroupDto>> >getCarriverGroupList(@Body RequestBody array);

    //获取承运商分组二级
    @Headers("Content-Type: application/json")
    @POST("oms/hz/cysGroup/cysGroup")
    Call<JsonResult<List<OrderAssignmentSecondDto>> >getCarriverSecondList(@Body RequestBody array);

    //订单指派
    @Headers("Content-Type: application/json")
    @POST("oms/hz/omsWayBill/appointPlan")
    Call<JsonResult<EmptyDto> >orderZp(@Body RequestBody array);

    //获取指派分配详情
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/selectDetails")
    Call<JsonResult<AssignDelationDto> >getAssignDelation(@Body RequestBody array);

    //新建竞价
    @Headers("Content-Type: application/json")
    @POST("oms/hz/omsWayBill/addBidd")
    Call<JsonResult<EmptyDto> >addBidd(@Body RequestBody array);

    //获取竞价详情 分配查询
    @Headers("Content-Type: application/json")
    @POST("oms/hz/omsWayBill/biddCys")
    Call<JsonResult<BiddingpageDto>>getBiddList(@Body RequestBody array);

    //竞价分配 确认分配
    @Headers("Content-Type: application/json")
    @POST("oms/hz/omsWayBill/planBiddAppoint")
    Call<JsonResult<EmptyDto> >biddFenpei(@Body RequestBody array);

    //获取历史竞价
    @Headers("Content-Type: application/json")
    @POST("oms/hz/omsWayBill/selectBiddCys")
    Call<JsonResult<List<OrderBiddenHistoryDto>> >getBiddHistory(@Body RequestBody array);

    //计划单  暂停运输或者终止
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/suspendAndStopTransport")
    Call<JsonResult<EmptyDto> >stopTrantsport(@Body RequestBody array);

    //获取路线规划详情
    @Headers("Content-Type: application/json")
    @POST("oms/trackRoute/selectByIdInfo")
    Call<JsonResult<MapDelationDto> >getMapDelation(@Body RequestBody array);

    //新增预运单
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/addWayBill")
    Call<JsonResult<EmptyDto> >addWayBillOrder(@Body RequestBody array);

    //编辑计划订单
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/editWayBill")
    Call<JsonResult<EmptyDto> >editWayBillOrder(@Body RequestBody array);

    //获取预运单详情
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/selectWayBillById")
    Call<JsonResult<OrderDelationDto> >getWayBillOrderDelation(@Body RequestBody array);

    //获取预运单详情
    @Headers("Content-Type: application/json")
    @POST("oms/shr/wayBill/selectWayBillById")
    Call<JsonResult<OrderDelationDto> >getWayBillShrOrderDelation(@Body RequestBody array);

    //获取预备运单列表
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/selectWayBill")
    Call<JsonResult<OrderWayBillDto> >getWayBillList(@Body RequestBody array);

    //删除预运单
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/deleteWayBill")
    Call<JsonResult<EmptyDto> >deleteWayBill(@Body RequestBody array);

    //预运单审核
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/toExamineWayBill")
    Call<JsonResult<EmptyDto> >WaybillshOrder(@Body RequestBody array);

    //获取预运单清单
    @Headers("Content-Type: application/json")
    @POST("oms/common/wayBill/selectMaterialsList")
    Call<JsonResult<OrderInventoryDto> >getWayBillQd(@Body RequestBody array);

    //预运单确定竞价
    @Headers("Content-Type: application/json")
    @POST("oms/hz/omsWayBill/biddAppoint")
    Call<JsonResult<EmptyDto> >wayBillBiddOrder(@Body RequestBody array);


    //获取预运单指派承运商列表
    @Headers("Content-Type: application/json")
    @POST("oms/hz/cysGroup/selectCysByPage")
    Call<JsonResult<WayBillAssignDto> >getWayBillCys(@Body RequestBody array);

    //预运单指派
    @Headers("Content-Type: application/json")
    @POST("oms/hz/omsWayBill/appoint")
    Call<JsonResult<EmptyDto> >appointWayBill(@Body RequestBody array);

    //获取调度单列表
    @Headers("Content-Type: application/json")
    @POST("oms/common/disPatch/selectDsiPatch")
    Call<JsonResult<OrderManageDto> >getManageOrderList(@Body RequestBody array);

    //删除调度单
    @Headers("Content-Type: application/json")
    @POST("oms/hz/disPatch/deleteHzById")
    Call<JsonResult<EmptyDto> >deleteManageOrder(@Body RequestBody array);

    //获取调度单列表
    @Headers("Content-Type: application/json")
    @POST("oms/common/disPatch/selectWayBillByDispatchId")
    Call<JsonResult<OrderManageRoleDto> >getRuleList(@Body RequestBody array);


    //获取竞价列表
    @Headers("Content-Type: application/json")
    @POST("oms/hz/biddDetail/queryHzForPage")
    Call<JsonResult<PlanBiddDto> >getPlanBidd(@Body RequestBody array);
    //
    @Headers("Content-Type: application/json")
    @POST("oms/hz/disPatch/sendCarList")
    Call<JsonResult<SendCarDto> >getSendCarList(@Body RequestBody array);

    //获取竞价列表
    @Headers("Content-Type: application/json")
    @POST("oms/hz/omsWayBill/selectBidd")
    Call<JsonResult<List<BiddFirstDto>> >getBiddenFirst(@Body RequestBody array);

    //获取价格排行
    @Headers("Content-Type: application/json")
    @POST("oms/common/biddDetail/selectRanking")
    Call<JsonResult<RankPageDto> >getRankList(@Body RequestBody array);

    //货主查询物流信息
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/selectLogistics")
    Call<JsonResult<LogistcsDto> >getWuLiuInfo(@Body RequestBody array);

    //货主签收
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/confirmSignIn")
    Call<JsonResult<EmptyDto> >orderSign(@Body RequestBody array);


    //获取货主签收过磅量
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/getWeight")
    Call<JsonResult<SignWeightDto> >getSinWeight(@Body RequestBody array);

    //获取换车信息
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/selectCarLog")
    Call<JsonResult<List<ChangeCarDto>> >getChangeCar(@Body RequestBody array);

    //获取派车详情
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/sendCarDetails")
    Call<JsonResult<SendCarDelationDto> >getSendCardelation(@Body RequestBody array);


    //获取地图轨迹
    @Headers("Content-Type: application/json")
    @POST("oms/hz/track/planRoute")
    Call<JsonResult<RouteDto> >getRoute(@Body RequestBody array);

    //同意暂停运输
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/agreeCysApply")
    Call<JsonResult<EmptyDto>>agreeCysapply(@Body RequestBody array);
    //榜单存疑
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/doubtSignIn")
    Call<String>doubtSignIn(@Body RequestBody array);
    /**
     *获取北斗轨迹
     */
    @Headers("Content-Type: application/json")
    @POST("tms/track/selectTrackResult")
    Call<JsonResult<BeidouTravelDto>> getBeiDouTravel(@Body RequestBody array);
    //获取预设轨迹
    @Headers("Content-Type: application/json")
    @POST("oms/trackRoute/selectByWaybillIdInfo")
    Call<JsonResult<PresupTravelDto> >getPreSupTravel(@Body RequestBody array);

    //关闭竞价单
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/closeBidd")
    Call<JsonResult<EmptyDto>>endBidd(@Body RequestBody array);

    //同步数据
    @Headers("Content-Type: application/json")
    @POST("oms/docking/yisi/selectWayBillPlan")
    Call<JsonResult<EmptyDto> >sync(@Body RequestBody array);

    //参照生成
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/selectCenterDate")
    Call<JsonResult<ConsultDto> >getConsute(@Body RequestBody array);

    //验货列表
    @Headers("Content-Type: application/json")
    @POST("oms/shr/wayBill/selectCheckBill")
    Call<JsonResult<ConsignDto> >getconsignList(@Body RequestBody array);

    //验货提交
    @Headers("Content-Type: application/json")
    @POST("oms/shr/wayBill/checkGoods")
    Call<JsonResult<EmptyDto> >examgoodsubmit(@Body RequestBody array);

    //选择关联公司
    @Headers("Content-Type: application/json")
    @POST("oms/common/company/queryForTree")
    Call<String> getrelationCom(@Body RequestBody array);
    /**
     *获取预警信息
     */
    @Headers("Content-Type: application/json")
    @POST("oms/common/disPatchCar/selectWaybillWarnInfo")
    Call<JsonResult<List<WarnInfoDto>>> getWarnInfo(@Body RequestBody array);
    /***
     * 是否含有待处理大单
     */
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/selectEndStatues")
    Call<JsonResult<selectEndStatuesDto>> selectEndStatues();
}
