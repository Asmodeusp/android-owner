package com.saimawzc.shipper.ui.my.settlement.acount;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.myselment.AccountDelationDto;
import com.saimawzc.shipper.presenter.mine.mysetment.AccountDelationPresenter;
import com.saimawzc.shipper.view.mysetment.AccountDelationView;

import butterknife.BindView;

/****
 * 对账详情
 * **/
public class AccountDelationFragment extends BaseFragment implements AccountDelationView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String id;
    private AccountDelationPresenter presenter;

    @BindView(R.id.wayBillNo)
    TextView tvWayBillNo;
    @BindView(R.id.tvfromAdress)TextView tvFromAdress;
    @BindView(R.id.tvToadress)TextView tvToadress;

    @BindView(R.id.tvuserName)TextView tvUserName;
    @BindView(R.id.tvsijiInfo)TextView tvSijiInfo;
    @BindView(R.id.tvGoodInfo)TextView tvGoodInfo;
    @BindView(R.id.tvgoodnum)TextView tvGoodnum;
    @BindView(R.id.tvsijiSign)TextView tvSijiSign;
    @BindView(R.id.tvhzSign)TextView tvhzSign;
    @BindView(R.id.tvptaccount)TextView tvPtAccount;
    @BindView(R.id.tvhzaccount)TextView tvHzaccount;
    @BindView(R.id.tvcysaccount)TextView tvcysaccount;
    @BindView(R.id.tvqsrtz)TextView tvqsrtz;
    @BindView(R.id.tvreason)TextView tvreason;
    @BindView(R.id.tvtranttime)TextView tvTranttime;

    @Override
    public int initContentView() {
        return R.layout.fragment_account_delation;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"对账详情");
        mContext=getActivity();
        context.setToolbar(toolbar,"对账详情");
        presenter=new AccountDelationPresenter(this,mContext);
        id=getArguments().getString("id");
        presenter.getData(id);
    }

    @Override
    public void initData() {

    }

    @Override
    public void getData(AccountDelationDto dto) {
        if(dto!=null){
            tvWayBillNo.setText(dto.getWayBillNo());
            tvFromAdress.setText(dto.getFromUserAddress());
            tvToadress.setText(dto.getToUserAddress());
            tvUserName.setText(dto.getHzName());
            tvSijiInfo.setText(dto.getSjName()+"|"+dto.getCarNo());
            tvGoodInfo.setText(dto.getMaterialsName()+"|"+dto.getPrice());
            tvGoodnum.setText("预提"+dto.getTotalWeight()+"|过磅"+dto.getWeighing()+"|签收"+dto.getHzSignInWeight());
            tvSijiSign.setText(dto.getSjName()+"|"+dto.getSjSignInWeight()+"|"+dto.getSjSignTime());
            tvhzSign.setText(dto.getHzName()+"|"+dto.getHzSignInWeight()+"|"+dto.getSignTime());
            tvPtAccount.setText(dto.getPlatReconName()+"|"+dto.getPlatReconTime());
            tvHzaccount.setText(dto.getHzReconName()+"|"+dto.getHzReconTime());
            tvcysaccount.setText(dto.getCysReconName()+"|"+dto.getCysReconTime());
            tvqsrtz.setText(dto.getHzAdjustName());
            tvreason.setText(dto.getDeductReason());
            tvTranttime.setText(dto.getTranStartTime()+"-"+dto.getTranEndTime());


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
