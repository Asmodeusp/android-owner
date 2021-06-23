package com.saimawzc.shipper.ui.order.creatorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.creatorder.OrderDelationDto;
import com.saimawzc.shipper.presenter.order.OrderApprovalPresenter;
import com.saimawzc.shipper.ui.order.creatorder.richtext.ShowArtActivity;
import com.saimawzc.shipper.view.order.OrderApprovalView;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.saimawzc.shipper.constants.AppConfig.reshPlanOrder;

/***
 * 审核
 * **/

public class OrderApprovalFragment extends BaseFragment implements OrderApprovalView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String id;
    @BindView(R.id.tvAuthority) TextView tvAuthority;//组织机构
    @BindView(R.id.tvconsigncompany) TextView tvConsignCompany;
    @BindView(R.id.tvbilltype) TextView tvBillType;
    @BindView(R.id.tvsendcompany) TextView tvSendCompany;
    @BindView(R.id.tvsendadress) TextView tvSendAdress;
    @BindView(R.id.sendBussinesstime) TextView tvSendBussiTime;
    @BindView(R.id.tvreceivecompany) TextView tvReceiveCompany;
    @BindView(R.id.tvreceiveadress) TextView tvReceiveAdress;
    @BindView(R.id.tvreceivebuniesstime) TextView tvReceiveBuniessTime;
    @BindView(R.id.tvorderpeople) TextView tvOrderPeople;
    @BindView(R.id.tvreceivestrage) TextView tvReceiveStrage;
    @BindView(R.id.tvsign) TextView tvSignStrage;
    @BindView(R.id.tvauthsign) TextView tvIsAutoSign;
    @BindView(R.id.tvreceivetime) TextView tvReceiveTime;
    @BindView(R.id.tvgoodname) TextView tvGoodName;
    @BindView(R.id.tvnum) TextView tvNum;
    @BindView(R.id.tvprice) TextView tvPrice;
    @BindView(R.id.tvkeshangnum) TextView tvKsNum;
    @BindView(R.id.tvmaketime) TextView tvMakeTime;
    @BindView(R.id.tvmakepeople) TextView tvMakePeople;
    @BindView(R.id.tvpayxieyi) TextView tvPayXieyi;
    @BindView(R.id.tvmark) TextView tvMark;
    @BindView(R.id.imgapplyyh) ImageView imgApplyyh;
    @BindView(R.id.imgapplyzh) ImageView imgApplyzh;
    @BindView(R.id.imgapplyxh) ImageView imgApplyxh;
    @BindView(R.id.imgapplyfp) ImageView imgApplyfp;
    @BindView(R.id.imgapplyzhpz) ImageView imgApplyzhpz;
    @BindView(R.id.imgapplyxhpz) ImageView imgApplyxhpz;
    private OrderApprovalPresenter presenter;
    private String type="";
    @BindView(R.id.llBtn)
    LinearLayout llBtn;
    @BindView(R.id.rlyeType)LinearLayout linearYwtype;
    @BindView(R.id.tvyewytype)TextView tvYwtype;
    @BindView(R.id.tvTrantWay)TextView tvTrantWay;
    @BindView(R.id.tvroule)TextView tvRoute;
    @BindView(R.id.tvreceiveobj)TextView tvReceiveObj;
    @BindView(R.id.tvhZname)TextView tvhZname;
    @BindView(R.id.tvstayTime)TextView tvstayTime;

    @BindView(R.id.imgplyj)ImageView imgPyyj;
    @BindView(R.id.imgstayyz)ImageView imgstayyz;
    @BindView(R.id.imglock)ImageView imglock;
    @BindView(R.id.imgguobang)ImageView imgguobang;
    @BindView(R.id.imgoffline)ImageView imgoffline;
    @BindView(R.id.imgcarmodel)ImageView imgCarModel;
    @BindView(R.id.llprice)LinearLayout llPrice;
    @BindView(R.id.tvyhr)TextView tvYhr;

    @BindView(R.id.tvcarmodel)TextView tvcarmodel;
    @BindView(R.id.tvdriverage)TextView tvDriverAge;
    @BindView(R.id.tvcarage)TextView tvCarAge;
    @BindView(R.id.tvaqgz)TextView tvAqgz;
    @BindView(R.id.tvrelacom)TextView tvRelaCom;

    @Override
    public int initContentView() {
        return R.layout.fragment_order_sh;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"订单审核");
        id=getArguments().getString("id");
        presenter=new OrderApprovalPresenter(this,mContext);
        presenter.getpOrderDelation(id);
        try{
            type=getArguments().getString("type");
            if(type.equals("delation")){
                llBtn.setVisibility(View.GONE);
                context.setToolbar(toolbar,"订单详情");
            }
        }catch (Exception e){

        }

    }

    @OnClick({R.id.tvAgreen,R.id.tvRefuse})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvAgreen:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                presenter.approval(id,2);
                break;
            case R.id.tvRefuse:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                presenter.approval(id,3);
                break;
        }


    }

    @Override
    public void initData() {


    }

    @Override
    public void getOrderDelation(final OrderDelationDto dto) {
        if(dto!=null){
            tvAuthority.setText(dto.getCompanyName());
            tvConsignCompany.setText(dto.getHandComName());
            if(dto.getBusinessType()==1){
                tvYwtype.setText("总包");
                linearYwtype.setVisibility(View.VISIBLE);
            }else {
                tvYwtype.setText("自营");
                linearYwtype.setVisibility(View.VISIBLE);
                llPrice.setVisibility(View.GONE);
            }


            if(dto.getWayBillType().equals("2")){
                tvBillType.setText("采购运单");
            }else if(dto.getWayBillType().equals("1")){
                tvBillType.setText("销售运单");
            }else if(dto.getWayBillType().equals("3")){
                tvBillType.setText("调拨运单");
            }else {
                tvBillType.setText(dto.getWayBillType());
            }
            tvSendCompany.setText(dto.getFromName());
            tvTrantWay.setText(dto.getTranTypeName());
            tvSendAdress.setText(dto.getFromUserAddress());
            tvSendBussiTime.setText(dto.getFromOperateTime());
            tvReceiveCompany.setText(dto.getToName());
            tvReceiveAdress.setText(dto.getToUserAddress());
            tvReceiveBuniessTime.setText(dto.getToOperateTime());
            tvOrderPeople.setText(dto.getConfirmorName());
            tvReceiveStrage.setText(dto.getConfirmorStacticsName()+"");
            tvSignStrage.setText(dto.getSingStacticsName());

            if(dto.getAutoSign()==1){
                tvIsAutoSign.setText("是");
            }else {
                tvIsAutoSign.setText("否");
            }
            tvReceiveTime.setText(dto.getArrivalStartTime()+"~"+dto.getArrivalEndTime());
            tvGoodName.setText(dto.getList().get(0).getMaterialsName());
            if(dto.getList().get(0).getUnit()==2){
                tvNum.setText(dto.getList().get(0).getWeight()+"方");
            }else {
                tvNum.setText(dto.getList().get(0).getWeight()+"吨");
            }
            tvPrice.setText(dto.getList().get(0).getPrice()+"元");
            tvKsNum.setText(dto.getChoose().getThirdPartyNo());
            tvMakeTime.setText(dto.getChoose().getMakerTime()+"");
            tvMakePeople.setText(dto.getChoose().getMakerName());
            tvPayXieyi.setText(dto.getChoose().getPayProtocolName());
            tvMark.setText(dto.getChoose().getRemark());

            tvReceiveObj.setText(dto.getChoose().getPushAlarmRoleName());
            tvhZname.setText(dto.getChoose().getAlarmHzName());
            tvRoute.setText(dto.getRouteName());
            tvstayTime.setText(dto.getChoose().getAlarmTime()+"分钟");
            tvYhr.setText(dto.getChoose().getCheckUserListName());
            tvcarmodel.setText(dto.getChoose().getCarTypeName());
            tvDriverAge.setText(dto.getChoose().getDrivingYears()+"年");
            tvCarAge.setText(dto.getChoose().getTravelYears()+"年");
            tvRelaCom.setText(dto.getChoose().getRelationComName());
            if(TextUtils.isEmpty(dto.choose.getContext())){
                tvAqgz.setText("暂无告知");
                tvAqgz.setTextColor(mContext.getResources().getColor(R.color.color_black));
            }else {
                tvAqgz.setText("点击查看");
                tvAqgz.setTextColor(mContext.getResources().getColor(R.color.red));
                tvAqgz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString("data",dto.choose.getContext());
                        readyGo(ShowArtActivity.class,bundle);
                    }
                });
            }
            //提供发票
            if(dto.getChoose().getProvideInvoice()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyfp);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyfp);
            }

            //提供卸货
            if(dto.getChoose().getProvideUnload()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyxh);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyxh);
            }
            //提供发货
            if(dto.getChoose().getProvideLoad()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyzh);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyzh);
            }
            //验货
            if(dto.getChoose().getCheck()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyyh);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyyh);
            }
            //装货拍照
            if(dto.getChoose().getLoadPhotos()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyzhpz);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyzhpz);
            }

            //卸货拍照
            if(dto.getChoose().getUnloadPhotos()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgApplyxhpz);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgApplyxhpz);
            }
            //
            if(dto.getChoose().getDeviationAlarm()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgPyyj);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgPyyj);
            }
            if(dto.getChoose().getStopAlarm()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgstayyz);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgstayyz);
            }
            if(dto.getChoose().getBindSmartLock()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imglock);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imglock);
            }
            if(dto.getChoose().getOutFactoryPhotos()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgguobang);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgguobang);
            }
            if(dto.getChoose().getOffLineAlarm()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgoffline);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgoffline);
            }
            if(dto.getChoose().getOpenCarType()==1){
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_choose,imgCarModel);
            }else {
                ImageLoadUtil.displayImage(mContext,R.drawable.ico_unchoose,imgCarModel);
            }
        }

    }

    @Override
    public void showLoading() {
        context.showLoadingDialog();

    }

    @Override
    public void dissLoading() {
    context.dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
    context.showMessage(str);
    }

    @Override
    public void oncomplete() {
        context.showMessage("审核成功");
        Intent intent = new Intent();
        intent.setAction(reshPlanOrder);
        context.sendBroadcast(intent);
        context.finish();

    }
}
