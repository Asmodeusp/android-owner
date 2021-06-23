package com.saimawzc.shipper.ui.sendcar;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.send.SendCarDelationDto;
import com.saimawzc.shipper.presenter.order.sendcar.SendCarDelationPresenter;
import com.saimawzc.shipper.view.order.sendcar.SendCarDelationView;

import butterknife.BindView;

/***
 * 派车详情
 * **/
public class SendCarDelationFragment extends BaseFragment implements SendCarDelationView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvDanHao) TextView tvDanHao;
    @BindView(R.id.tvsendcarName) TextView tvsendcarName;
    @BindView(R.id.tvDriver) TextView tvDriver;
    @BindView(R.id.tvCar) TextView tvCar;
    @BindView(R.id.tvGood) TextView tvGood;
    @BindView(R.id.tvGoodBieming) TextView tvGoodBieming;
    @BindView(R.id.tvGoodnum) TextView tvGoodnum;
    @BindView(R.id.tvSendCarstatus) TextView tvSendCarstatus;
    @BindView(R.id.tvtrantsigntime) TextView tvtrantsigntime;
    @BindView(R.id.tvSignName) TextView tvSignName;
    @BindView(R.id.tvSignStarus) TextView tvSignStarus;
    @BindView(R.id.tvksnum) TextView tvksnum;
    @BindView(R.id.tvpaichenum) TextView tvpaichenum;
    @BindView(R.id.tvfhCompany) TextView tvfhCompany;
    @BindView(R.id.faAdress) TextView faAdress;
    @BindView(R.id.tvreceiveCompany) TextView tvreceiveCompany;
    @BindView(R.id.tvReceiveAdress) TextView tvReceiveAdress;
    private SendCarDelationPresenter presenter;
    private String id;

    @Override
    public int initContentView() {
        return R.layout.fragment_sendcardelation;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"派车详情");
        id=getArguments().getString("id");
        presenter=new SendCarDelationPresenter(this,mContext);
        presenter.getData(id);
    }

    @Override
    public void initData() {

    }

    @Override
    public void getData(SendCarDelationDto dto) {
        if(dto!=null){
            tvDanHao.setText("预运单号："+dto.getWayBillNo());
            tvsendcarName.setText("派车人："+dto.getCysName());
            tvDriver.setText("司机："+dto.getSjName()+"|"+dto.getSjPhone());
            tvCar.setText("运输车辆："+dto.getCarNo());
            String goodsName="";
            for(int i=0;i<dto.getList().size();i++){
                goodsName+=dto.getList().get(i).getMaterialsName()+"|"
                        +dto.getList().get(i).getWeight()+"|"+dto.getList().get(i).getPrice()+"元";
            }
            tvGood.setText("运输货物："+goodsName);
            tvGoodBieming.setText("物料别名："+dto.getMaterialsAlias());
            tvGoodnum.setText("计量："+dto.getTotalWeight());
            tvSendCarstatus.setText("派车状态："+dto.getSendCarType());
            tvtrantsigntime.setText("签收时间："+dto.getSignTime());
            tvSignName.setText("签收人："+dto.getConfirmName());
            tvSignStarus.setText("签收状态："+dto.getConfirmStatus());
            tvksnum.setText("订单编号："+dto.getThirdPartyNo());
            tvpaichenum.setText("派车单号："+dto.getSendCarNo());
            tvfhCompany.setText("发货单位："+dto.getFromName());
            faAdress.setText("发货地址："+dto.getFromLocation());
            tvreceiveCompany.setText("收货单位："+dto.getToName());
            tvReceiveAdress.setText("收货地址："+dto.getToUserAddress());



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

    }
}
