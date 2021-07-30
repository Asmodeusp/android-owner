package com.saimawzc.shipper.ui.order;

import android.os.Bundle;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.myselment.WaitDispatchBillFragment;
import com.saimawzc.shipper.ui.homeindex.WaybillManageFragment;
import com.saimawzc.shipper.ui.my.settlement.MySettlementFragment;
import com.saimawzc.shipper.ui.my.settlement.SetmentQueryFragment;
import com.saimawzc.shipper.ui.my.settlement.WaitComfirmFragment;
import com.saimawzc.shipper.ui.my.settlement.acount.AccountDelationFragment;
import com.saimawzc.shipper.ui.my.settlement.acount.AccountManageFragment;
import com.saimawzc.shipper.ui.my.settlement.acount.AccountQueryFragment;
import com.saimawzc.shipper.ui.order.ConsultCreat.ConsultListFragment;
import com.saimawzc.shipper.ui.order.advancewaybill.AdvanceWayBillListFragment;
import com.saimawzc.shipper.ui.order.advancewaybill.CreatWayBillOrderFragment;
import com.saimawzc.shipper.ui.order.advancewaybill.OrderWayBillInventoryFragment;
import com.saimawzc.shipper.ui.order.advancewaybill.WayBillApprovalFragment;
import com.saimawzc.shipper.ui.order.advancewaybill.WayBillAssignmentFragment;
import com.saimawzc.shipper.ui.order.advancewaybill.WayBillBiddDelationFragment;
import com.saimawzc.shipper.ui.order.bidd.BiddDelationFirstFragment;
import com.saimawzc.shipper.ui.order.bidd.BiddRankFragment;
import com.saimawzc.shipper.ui.order.bidd.OrderBiddHistoryFragment;
import com.saimawzc.shipper.ui.order.bidd.OrderBiddingDelationFragment;
import com.saimawzc.shipper.ui.order.bidd.OrderBiddingFragment;
import com.saimawzc.shipper.ui.order.creatorder.AddAdressFragment;
import com.saimawzc.shipper.ui.order.creatorder.AddContractsFragment;
import com.saimawzc.shipper.ui.order.creatorder.AddGoodsCompanyFragment;
import com.saimawzc.shipper.ui.order.creatorder.AddNewExamPeopleFragment;
import com.saimawzc.shipper.ui.order.creatorder.AssignDelationFragment;
import com.saimawzc.shipper.ui.order.creatorder.ChooseArdessFragment;
import com.saimawzc.shipper.ui.order.creatorder.ChooseCarTypeFragment;
import com.saimawzc.shipper.ui.order.creatorder.ChooseContractsFragment;
import com.saimawzc.shipper.ui.order.creatorder.ChooseGooodsCompanyFragment;
import com.saimawzc.shipper.ui.order.creatorder.ChooseHzListFragment;
import com.saimawzc.shipper.ui.order.creatorder.ChooseRouteFragment;
import com.saimawzc.shipper.ui.order.creatorder.OrderApprovalFragment;
import com.saimawzc.shipper.ui.order.creatorder.OrderAssignmentFragment;
import com.saimawzc.shipper.ui.order.creatorder.OrderAssignmentSecondFragment;
import com.saimawzc.shipper.ui.order.creatorder.RouteDelationFragment;
import com.saimawzc.shipper.ui.order.creatorder.StopTrantOrderFragment;
import com.saimawzc.shipper.ui.order.manage.OrderManageFragment;
import com.saimawzc.shipper.ui.order.manage.OrderManageAssignFragment;
import com.saimawzc.shipper.ui.order.planOrder.PlanOrderFragment;
import com.saimawzc.shipper.ui.sendcar.ChangeCarInfoFragment;
import com.saimawzc.shipper.ui.sendcar.LogisticsInfoFragment;
import com.saimawzc.shipper.ui.sendcar.MapTravelFragment;
import com.saimawzc.shipper.ui.sendcar.SendCarDelationFragment;
import com.saimawzc.shipper.ui.tab.consignee.ExamineGoodsFragment;

/**
 * 订单主页
 * ***/
public class OrderMainActivity extends BaseActivity {

    String comeFrom = "";
    BaseFragment mCurrentFragment;

    @Override
    protected int getViewId() {
        return R.layout.activity_ordermain;
    }

    @Override
    protected void init() {

        if(comeFrom.equals("planorder")){//计划订单列表
            mCurrentFragment=new PlanOrderFragment();
        }
        if(comeFrom.equals("chooseHzList")){//货主列表
            mCurrentFragment=new ChooseHzListFragment();
        }
        if(comeFrom.equals("examgood")){//验货
            mCurrentFragment=new ExamineGoodsFragment();
        }
        if(comeFrom.equals("choosecartype")){//选择车型
            mCurrentFragment=new ChooseCarTypeFragment();
        }

        if(comeFrom.equals("addshr")){//新增收货人
            mCurrentFragment=new AddNewExamPeopleFragment();
        }
        if(comeFrom.equals("sync")){//同步数据
            mCurrentFragment=new SyncDataFragment();
        }
        if(comeFrom.equals("czsc")){//参照生成
            mCurrentFragment=new ConsultListFragment();
        }
        if(comeFrom.equals("routedelation")){//路径详情
            mCurrentFragment=new RouteDelationFragment();
        }
        if(comeFrom.equals("queryaccount")){//计划订单列表
            mCurrentFragment=new SetmentQueryFragment();
        }
        if(comeFrom.equals("route")){//选择路径
            mCurrentFragment=new ChooseRouteFragment();
        }
        if(comeFrom.equals("biddrank")){//竞价排名
            mCurrentFragment=new BiddRankFragment();
        }
        if(comeFrom.equals("biddfirst")){//竞价详情第一层
            mCurrentFragment=new BiddDelationFirstFragment();
        }
        if(comeFrom.equals("waitaccount")){//待结算运单
            mCurrentFragment=new WaitComfirmFragment();
        }
        if(comeFrom.equals("waitdistapch")){//待结算派车单
            mCurrentFragment=new WaitDispatchBillFragment();
        }
        if(comeFrom.equals("mysettelment")){//我的结算单
            mCurrentFragment=new MySettlementFragment();
        }
        if(comeFrom.equals("accountdelation")){//对账详情
            mCurrentFragment=new AccountDelationFragment();
        }
        if(comeFrom.equals("accountmanage")){//对账管理
            mCurrentFragment=new AccountManageFragment();
        }
        if(comeFrom.equals("accountquery")){//对账查询
            mCurrentFragment=new AccountQueryFragment();
        }
        if(comeFrom.equals("addorder")){//新增订单
            mCurrentFragment=new CreatOrderFragment();
        }
        if(comeFrom.equals("ordersh")){//订单审核
            mCurrentFragment=new OrderApprovalFragment();
        }
        if(comeFrom.equals("orderzp")){//订单指派
            mCurrentFragment=new OrderAssignmentFragment();
        }
        if(comeFrom.equals("ordebidding")){//竞价
            mCurrentFragment=new OrderBiddingFragment();
        }
        if(comeFrom.equals("orderbiddhistory")){//竞价历史
            mCurrentFragment=new OrderBiddHistoryFragment();
        }
        if(comeFrom.equals("biddingdelation")){//竞价详情
            mCurrentFragment=new OrderBiddingDelationFragment();
        }
        if(comeFrom.equals("ordermanage")){//订单调度
            mCurrentFragment=new OrderManageFragment();
        }
        if(comeFrom.equals("ordergroupsecond")){//承运商分组2级页面
            mCurrentFragment=new OrderAssignmentSecondFragment();
        }
        if(comeFrom.equals("managesssign")){//调度指派
            mCurrentFragment=new OrderManageAssignFragment();
        }
        if(comeFrom.equals("orderqingdan")){//清单
            mCurrentFragment=new OrderWayBillInventoryFragment();
        }
        if(comeFrom.equals("mainwaybillmanage")){//运单管理
            mCurrentFragment=new WaybillManageFragment();
        }
        if(comeFrom.equals("mywaybill")){//预运单
            mCurrentFragment=new AdvanceWayBillListFragment();
        }
        if(comeFrom.equals("choosegoodscompany")){//选择发货商和收货商
            mCurrentFragment=new ChooseGooodsCompanyFragment();
        }
        if(comeFrom.equals("addgoodcompany")){//新增发货商和收货商
            mCurrentFragment=new AddGoodsCompanyFragment();
        }
        if(comeFrom.equals("sendadress")){//发货地址和收货地址
            mCurrentFragment=new ChooseArdessFragment();
        }
        if(comeFrom.equals("addadress")){//新增发货地址和收货地址
            mCurrentFragment=new AddAdressFragment();
        }
        if(comeFrom.equals("choosecontracts")){//选择联系人
            mCurrentFragment=new ChooseContractsFragment();
        }
        if(comeFrom.equals("addcontracts")){//新建联系人
            mCurrentFragment=new AddContractsFragment();
        }
        if(comeFrom.equals("zpdelation")){//指派详情
            mCurrentFragment=new AssignDelationFragment();
        }
        if(comeFrom.equals("stoptrant")){//终止运输
            mCurrentFragment=new StopTrantOrderFragment();
        }
        if(comeFrom.equals("editOrder")){//编辑订单
            mCurrentFragment=new CreatOrderFragment();
        }
        if(comeFrom.equals("addwaybillorder")){//新增预运单
            mCurrentFragment=new CreatWayBillOrderFragment();
        }

        if(comeFrom.equals("waybillsh")){//预运单审核
            mCurrentFragment=new WayBillApprovalFragment();
        }
        if(comeFrom.equals("editWaybillOrder")){//重新编辑预运单
            mCurrentFragment=new CreatWayBillOrderFragment();
        }
        if(comeFrom.equals("waybillbiddingdelation")){//预运单竞价详情
            mCurrentFragment=new WayBillBiddDelationFragment();
        }
        if(comeFrom.equals("waybillzp")){//预运单指派
            mCurrentFragment=new WayBillAssignmentFragment();
        }
        if(comeFrom.equals("logistsc")){//物流信息
            mCurrentFragment=new LogisticsInfoFragment();
        }
        if(comeFrom.equals("changeinfo")){//换车信息

            mCurrentFragment=new ChangeCarInfoFragment();
        }
        if(comeFrom.equals("paichedelation")){//派车详情
            mCurrentFragment=new SendCarDelationFragment();
        }
        if(comeFrom.equals("maptravel")){//地图轨迹
            mCurrentFragment=new MapTravelFragment();
        }
        //
        if(mCurrentFragment!=null){
            mCurrentFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, mCurrentFragment)
                    .commit();
        }
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        if(bundle!=null){
            try{
                comeFrom=bundle.getString("from");
            }catch (Exception E){

            }
        }

    }
}
