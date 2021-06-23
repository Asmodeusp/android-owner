package com.saimawzc.shipper.presenter.order;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.modle.order.Imple.OrderCreatModelImple;
import com.saimawzc.shipper.modle.order.model.CreatOrderModel;
import com.saimawzc.shipper.ui.order.OrderBasicInfoFragment;
import com.saimawzc.shipper.ui.order.OrderOptionalInfoFragment;
import com.saimawzc.shipper.view.order.CreatOrderView;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.List;

public class CreatOrderPresenter implements BaseListener {


    private Context mContext;
    CreatOrderModel model;
    CreatOrderView view;
    private  BaseListener baseListener;

    public CreatOrderPresenter(CreatOrderView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new OrderCreatModelImple();
        baseListener=this;
    }
    private NormalDialog dialog;
    public void creatOrder(final OrderBasicInfoFragment basicInfoFragment,
                              final OrderOptionalInfoFragment optionalInfoFragment,String type,String id){
        if(basicInfoFragment.getAuthorityDto()==null){
            view.Toast("请选择组织机构");
            return;
        }
        if(Hawk.get(PreferenceKey.IS_TUOYUN,"").equals("1")){
            if(basicInfoFragment.getConsignmentCompanyDto()==null){
                view.Toast("请选择托运公司");
                return;
            }
        }
        if(basicInfoFragment.getBilltype()==null){
            view.Toast("请选择单据类型");
            return;
        }
        if(basicInfoFragment.trantWay()==null){
            view.Toast("请选择运输方式信息");
            return;
        }

        if(basicInfoFragment.getSendCompany()==null){
            view.Toast("请选择发货商");
            return;
        }
        if(basicInfoFragment.getSendAdress()==null){
            view.Toast("请选择发货地址");
            return;
        }

        if(TextUtils.isEmpty(basicInfoFragment.getSendBussineTime())){
            view.Toast("请选择发货点营业时间");
            return;
        }
        if(TextUtils.isEmpty(basicInfoFragment.getOrderPeopleId())){
            view.Toast("请选择收货确认人");
            return;
        }
        if(basicInfoFragment.getReceiveCompany()==null){
            view.Toast("请选择收货商");
            return;
        }
        if(basicInfoFragment.getReceiveAdress()==null){
            view.Toast("请选择收货地址");
            return;
        }
        if(TextUtils.isEmpty(basicInfoFragment.getReceiveBussineTime())){
            view.Toast("请选择收货点营业时间");
            return;
        }
        if(basicInfoFragment.getsignStrageDto()==null){
            view.Toast("请选择签收策略");
            return;
        }

        if(TextUtils.isEmpty(basicInfoFragment.getarriveTimeStart())){
            view.Toast("请选择到货起始时间");
            return;
        }
        if(TextUtils.isEmpty(basicInfoFragment.getarriveTimeEnd())){
            view.Toast("请选择到货截止时间");
            return;
        }
        if(basicInfoFragment.getGoodName()==null){
            view.Toast("请选择货物名称");
            return;
        }
        if(TextUtils.isEmpty(basicInfoFragment.getNum())){
            view.Toast("请输入货物数量");
            return;
        }
        if(TextUtils.isEmpty(basicInfoFragment.getPrice())){
            view.Toast("请输入货物价格");
            return;
        }

        if(type.equals("add")){
            Log.e("msg",optionalInfoFragment.mark());
            if(optionalInfoFragment.getIschoose()==false){
                dialog = new NormalDialog(mContext).isTitleShow(false)
                        .content("您是否不填写选填信息?")
                        .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                        .btnNum(2).btnText("取消", "确定");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                dialog.dismiss();
                            }
                        },
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                model.creatOrder(basicInfoFragment,optionalInfoFragment,view,baseListener);
                            }
                        });
                dialog.show();
            }else {
                model.creatOrder(basicInfoFragment,optionalInfoFragment,view,this);

            }

        }else {
            model.editOrder(basicInfoFragment,optionalInfoFragment,view,this,id);
        }

    }
    public void counsute(final OrderBasicInfoFragment basicInfoFragment,
                           final OrderOptionalInfoFragment optionalInfoFragment,String type,String id){
        if(basicInfoFragment.getAuthorityDto()==null){
            view.Toast("请选择组织机构");
            return;
        }
        if(Hawk.get(PreferenceKey.IS_TUOYUN,"").equals("1")){
            if(basicInfoFragment.getConsignmentCompanyDto()==null){
                view.Toast("请选择托运公司");
                return;
            }
        }
        if(basicInfoFragment.getBilltype()==null){
            view.Toast("请选择单据类型");
            return;
        }
        if(basicInfoFragment.trantWay()==null){
            view.Toast("请选择运输方式信息");
            return;
        }

        if(basicInfoFragment.getSendCompany()==null){
            view.Toast("请选择发货商");
            return;
        }
        if(basicInfoFragment.getSendAdress()==null){
            view.Toast("请选择发货地址");
            return;
        }

        if(TextUtils.isEmpty(basicInfoFragment.getSendBussineTime())){
            view.Toast("请选择发货点营业时间");
            return;
        }
        if(TextUtils.isEmpty(basicInfoFragment.getOrderPeopleId())){
            view.Toast("请选择收货确认人");
            return;
        }
        if(basicInfoFragment.getReceiveCompany()==null){
            view.Toast("请选择收货商");
            return;
        }
        if(basicInfoFragment.getReceiveAdress()==null){
            view.Toast("请选择收货地址");
            return;
        }
        if(TextUtils.isEmpty(basicInfoFragment.getReceiveBussineTime())){
            view.Toast("请选择收货点营业时间");
            return;
        }
        if(basicInfoFragment.getsignStrageDto()==null){
            view.Toast("请选择签收策略");
            return;
        }

        if(TextUtils.isEmpty(basicInfoFragment.getarriveTimeStart())){
            view.Toast("请选择到货起始时间");
            return;
        }
        if(TextUtils.isEmpty(basicInfoFragment.getarriveTimeEnd())){
            view.Toast("请选择到货截止时间");
            return;
        }
        if(basicInfoFragment.getGoodName()==null){
            view.Toast("请选择货物名称");
            return;
        }
        if(TextUtils.isEmpty(basicInfoFragment.getNum())){
            view.Toast("请输入货物数量");
            return;
        }
        if(TextUtils.isEmpty(basicInfoFragment.getPrice())){
            view.Toast("请输入货物价格");
            return;
        }
        model.consute(basicInfoFragment,optionalInfoFragment,view,this);

    }
    @Override
    public void successful() {
        view.oncomplete();


    }

    @Override
    public void onFail(String str) {
    view.Toast(str);
    }

    @Override
    public void successful(int type) {

    }


}
