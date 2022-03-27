package com.saimawzc.shipper.ui.my.settlement;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.mysetment.WaitComfirmAdpater;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.myselment.WaitComfirmAccountDto;
import com.saimawzc.shipper.dto.myselment.WaitComfirmQueryPageDto;
import com.saimawzc.shipper.presenter.mine.mysetment.WaitSetmentPresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.mysetment.WaitSetmentView;
import com.saimawzc.shipper.weight.WrapContentLinearLayoutManager;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 待结算运单
 * **/

public class WaitComfirmFragment extends BaseFragment implements WaitSetmentView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private WaitComfirmAdpater adpater;
    private List<WaitComfirmAccountDto>mDatas=new ArrayList<>();
    private WaitSetmentPresenter presenter;
    private int page=1;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private LoadMoreListener loadMoreListener;

    @Override
    public int initContentView() {
        return R.layout.fragment_waitconfirmaccount;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"待结算大单");
        adpater=new WaitComfirmAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);
        presenter=new WaitSetmentPresenter(this,mContext);
        presenter.getData(page);

        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getData(page);

            }
        };
        rv.setOnScrollListener(loadMoreListener);
    }

    @Override
    public void initData() {
        adpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("from","waitdistapch");
                bundle.putString("id",mDatas.get(position).getPlanWayBillId());
                readyGo(OrderMainActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getData(page);
            }
        });
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

    @Override
    public void getData(WaitComfirmQueryPageDto dto) {
        if(dto!=null){
            if(page==1){
                mDatas.clear();
                adpater.notifyDataSetChanged();
            }
            if(dto.isLastPage()==false){
                loadMoreListener.isLoading = false;
                adpater.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
            }else {
                loadMoreListener.isLoading = true;
                adpater.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
            }
            adpater.addMoreData(dto.getList());

        }
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);

    }
}
