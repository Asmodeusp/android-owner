package com.saimawzc.shipper.ui.order.bidd;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.order.bidd.OrderBiddHistoryAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.OrderBiddenHistoryDto;
import com.saimawzc.shipper.presenter.order.OrderBiddHistoryPresenter;
import com.saimawzc.shipper.view.order.OrderBiddHistoryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 竞价历史
 * ***/

public class OrderBiddHistoryFragment extends BaseFragment implements OrderBiddHistoryView {


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv) RecyclerView rv;
    private OrderBiddHistoryAdapter adapter;
    private List<OrderBiddenHistoryDto> mDatas=new ArrayList<>();
    private String id;
    private String cysId;
    private OrderBiddHistoryPresenter presenter;

    @Override
    public int initContentView() {
        return R.layout.fragment_bidden_history;
    }

    @Override
    public void initView() {
        mContext=getContext();
        id=getArguments().getString("id");
        cysId=getArguments().getString("cysId");
        context.setToolbar(toolbar,"历史竞价");
        adapter = new OrderBiddHistoryAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new OrderBiddHistoryPresenter(this,mContext);
        presenter.getcarrive(id,cysId);
    }

    @Override
    public void initData() {

    }

    @Override
    public void getBiddHistory(List<OrderBiddenHistoryDto> dtos) {
        adapter.addMoreData(dtos);

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
