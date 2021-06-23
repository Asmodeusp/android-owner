package com.saimawzc.shipper.ui.sendcar;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.order.LogistoicAdpater;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.send.LogistcsDto;
import com.saimawzc.shipper.presenter.order.sendcar.LogistiscPresenter;
import com.saimawzc.shipper.view.order.sendcar.LogisticsView;
import com.saimawzc.shipper.weight.CircleImageView;
import com.saimawzc.shipper.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/***
 * 物流信息
 * **/
public class LogisticsInfoFragment extends BaseFragment
        implements LogisticsView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvDanHao) TextView tvDanHao;
    @BindView(R.id.imgHead) CircleImageView imgHead;
    @BindView(R.id.tvName)TextView tvName;
    @BindView(R.id.tvPhone)TextView tvPhone;
    @BindView(R.id.carNo)TextView tvcarNo;
    @BindView(R.id.cv) RecyclerView  rv;
    private LogistiscPresenter  presenter;
    private String id;
    private LogistoicAdpater adpater;
    private List<LogistcsDto.transportLogList>mDatas=new ArrayList<>();

    @Override
    public int initContentView() {
        return R.layout.fragment_wuliu;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"物流信息");
        id=getArguments().getString("id");
        presenter=new LogistiscPresenter(this,mContext);

        adpater=new LogistoicAdpater(mDatas,mContext);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);

        presenter.getcarrive(id);

    }

    @Override
    public void initData() {

    }

    @Override
    public void getData(LogistcsDto dto) {
        if(dto!=null){
            tvDanHao.setText(dto.getWayBillNo());
            ImageLoadUtil.displayImage(mContext,dto.getSjPicture(),imgHead);
            tvName.setText(dto.getSjName());
            tvPhone.setText(dto.getSjPhone());
            tvcarNo.setText(dto.getCarNo());
            adpater.addMoreData(dto.getTransportLogList());

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
