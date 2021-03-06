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
import com.saimawzc.shipper.ui.sendcar.MapTravelFragment;
import com.saimawzc.shipper.ui.sendcar.SendCarDelationFragment;
import com.saimawzc.shipper.ui.sendcar.YujingFragmeng;
import com.saimawzc.shipper.ui.tab.consignee.ExamineGoodsFragment;

/**
 * ????????????
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

        if(comeFrom.equals("planorder")){//??????????????????
            mCurrentFragment=new PlanOrderFragment();
        }
        if(comeFrom.equals("chooseHzList")){//????????????
            mCurrentFragment=new ChooseHzListFragment();
        }
        if(comeFrom.equals("examgood")){//??????
            mCurrentFragment=new ExamineGoodsFragment();
        }
        if(comeFrom.equals("choosecartype")){//????????????
            mCurrentFragment=new ChooseCarTypeFragment();
        }

        if(comeFrom.equals("addshr")){//???????????????
            mCurrentFragment=new AddNewExamPeopleFragment();
        }
        if(comeFrom.equals("sync")){//????????????
            mCurrentFragment=new SyncDataFragment();
        }
        if(comeFrom.equals("czsc")){//????????????
            mCurrentFragment=new ConsultListFragment();
        }
        if(comeFrom.equals("routedelation")){//????????????
            mCurrentFragment=new RouteDelationFragment();
        }
        if(comeFrom.equals("queryaccount")){//??????????????????
            mCurrentFragment=new SetmentQueryFragment();
        }
        if(comeFrom.equals("route")){//????????????
            mCurrentFragment=new ChooseRouteFragment();
        }
        if(comeFrom.equals("biddrank")){//????????????
            mCurrentFragment=new BiddRankFragment();
        }
        if(comeFrom.equals("biddfirst")){//?????????????????????
            mCurrentFragment=new BiddDelationFirstFragment();
        }
        if(comeFrom.equals("waitaccount")){//???????????????
            mCurrentFragment=new WaitComfirmFragment();
        }
        if(comeFrom.equals("waitdistapch")){//??????????????????
            mCurrentFragment=new WaitDispatchBillFragment();
        }
        if(comeFrom.equals("mysettelment")){//???????????????
            mCurrentFragment=new MySettlementFragment();
        }
        if(comeFrom.equals("accountdelation")){//????????????
            mCurrentFragment=new AccountDelationFragment();
        }
        if(comeFrom.equals("accountmanage")){//????????????
            mCurrentFragment=new AccountManageFragment();
        }
        if(comeFrom.equals("accountquery")){//????????????
            mCurrentFragment=new AccountQueryFragment();
        }
        if(comeFrom.equals("addorder")){//????????????
            mCurrentFragment=new CreatOrderFragment();
        }
        if(comeFrom.equals("ordersh")){//????????????
            mCurrentFragment=new OrderApprovalFragment();
        }
        if(comeFrom.equals("orderzp")){//????????????
            mCurrentFragment=new OrderAssignmentFragment();
        }
        if(comeFrom.equals("ordebidding")){//??????
            mCurrentFragment=new OrderBiddingFragment();
        }
        if(comeFrom.equals("orderbiddhistory")){//????????????
            mCurrentFragment=new OrderBiddHistoryFragment();
        }
        if(comeFrom.equals("biddingdelation")){//????????????
            mCurrentFragment=new OrderBiddingDelationFragment();
        }
        if(comeFrom.equals("ordermanage")){//????????????
            mCurrentFragment=new OrderManageFragment();
        }
        if(comeFrom.equals("ordergroupsecond")){//???????????????2?????????
            mCurrentFragment=new OrderAssignmentSecondFragment();
        }
        if(comeFrom.equals("managesssign")){//????????????
            mCurrentFragment=new OrderManageAssignFragment();
        }
        if(comeFrom.equals("orderqingdan")){//??????
            mCurrentFragment=new OrderWayBillInventoryFragment();
        }
        if(comeFrom.equals("mainwaybillmanage")){//????????????
            mCurrentFragment=new WaybillManageFragment();
        }
        if(comeFrom.equals("mywaybill")){//?????????
            mCurrentFragment=new AdvanceWayBillListFragment();
        }
        if(comeFrom.equals("choosegoodscompany")){//???????????????????????????
            mCurrentFragment=new ChooseGooodsCompanyFragment();
        }
        if(comeFrom.equals("addgoodcompany")){//???????????????????????????
            mCurrentFragment=new AddGoodsCompanyFragment();
        }
        if(comeFrom.equals("sendadress")){//???????????????????????????
            mCurrentFragment=new ChooseArdessFragment();
        }
        if(comeFrom.equals("addadress")){//?????????????????????????????????
            mCurrentFragment=new AddAdressFragment();
        }
        if(comeFrom.equals("choosecontracts")){//???????????????
            mCurrentFragment=new ChooseContractsFragment();
        }
        if(comeFrom.equals("addcontracts")){//???????????????
            mCurrentFragment=new AddContractsFragment();
        }
        if(comeFrom.equals("zpdelation")){//????????????
            mCurrentFragment=new AssignDelationFragment();
        }
        if(comeFrom.equals("stoptrant")){//????????????
            mCurrentFragment=new StopTrantOrderFragment();
        }
        if(comeFrom.equals("editOrder")){//????????????
            mCurrentFragment=new CreatOrderFragment();
        }
        if(comeFrom.equals("addwaybillorder")){//???????????????
            mCurrentFragment=new CreatWayBillOrderFragment();
        }

        if(comeFrom.equals("waybillsh")){//???????????????
            mCurrentFragment=new WayBillApprovalFragment();
        }
        if(comeFrom.equals("editWaybillOrder")){//?????????????????????
            mCurrentFragment=new CreatWayBillOrderFragment();
        }
        if(comeFrom.equals("waybillbiddingdelation")){//?????????????????????
            mCurrentFragment=new WayBillBiddDelationFragment();
        }
        if(comeFrom.equals("waybillzp")){//???????????????
            mCurrentFragment=new WayBillAssignmentFragment();
        }
        if(comeFrom.equals("logistsc")){//????????????
            mCurrentFragment=new YujingFragmeng();
        }
        if(comeFrom.equals("changeinfo")){//????????????

            mCurrentFragment=new ChangeCarInfoFragment();
        }
        if(comeFrom.equals("paichedelation")){//????????????
            mCurrentFragment=new SendCarDelationFragment();
        }
        if(comeFrom.equals("maptravel")){//????????????
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
