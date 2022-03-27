package com.saimawzc.shipper.ui.order.creatorder;


import android.text.TextUtils;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.creatorder.AssiginDelationAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.presenter.order.AssginDelationPresenter;
import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/****
 * 订单指派详情  分配详情
 * **/
public class AssignDelationFragment extends BaseFragment implements AssignDelationView {

    private AssiginDelationAdapter adapter;
    private List<AssignDelationDto.listdata>mDatas=new ArrayList<>();
    private int page=1;
    private AssginDelationPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rvRecycle)
    RecyclerView rv;
    private String id;
    private String type;
    private LoadMoreListener loadMoreListener;

    @Override
    public int initContentView() {
        return R.layout.fragment_assigndelation;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        id=getArguments().getString("id");
        type=getArguments().getString("type");
        context.setToolbar(toolbar,"分配详情");
        adapter=new AssiginDelationAdapter(mDatas,mContext,type);
        layoutManager=new LinearLayoutManager(mContext);
        presenter=new AssginDelationPresenter(this,mContext);
        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getcarrive(id,page,type);
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        if(!TextUtils.isEmpty(id)){
            presenter.getcarrive(id,page,type+"");
        }else {
        }

    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getcarrive(id,page,type);
            }
        });

    }

    @Override
    public void getCarriveList(List<AssignDelationDto.listdata> dtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        adapter.addMoreData(dtos);
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);

    }

    @Override
    public void isLast(boolean islast) {
        if(islast){
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_MORE);
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
