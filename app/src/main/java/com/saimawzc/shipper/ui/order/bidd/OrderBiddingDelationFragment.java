package com.saimawzc.shipper.ui.order.bidd;


import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;

import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.bidd.OrderBiddDelationAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.BiddingDelationDto;
import com.saimawzc.shipper.presenter.order.OrderBiddDelationPresenter;
import com.saimawzc.shipper.view.order.OrderBiddDelationView;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 竞价 详情
 * **/
public class OrderBiddingDelationFragment extends BaseFragment implements OrderBiddDelationView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    private String id;
    private List<BiddingDelationDto> mDatas=new ArrayList<>();
    private OrderBiddDelationPresenter presenter;
    private OrderBiddDelationAdapter adapter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @Override
    public int initContentView() {
        return R.layout.fragment_biddingdelation;
    }

    @OnClick(R.id.tvOrder)
    public void click(View view){
        if(!RepeatClickUtil.isFastClick()){
            context.showMessage("您操作太频繁，请稍后再试");
            return;
        }
        List<BiddingDelationDto> temp=new ArrayList<>();
        for(int i=0;i<mDatas.size();i++){
            if(mDatas.get(i).getMachNum()>0&& mDatas.get(i).getPointStatus()!=2){
                if(!TextUtils.isEmpty(mDatas.get(i).getRoleType())){
                    if(mDatas.get(i).getRoleType().equals("5")){
                        if(mDatas.get(i).getPointCarNum()>0){
                            temp.add(mDatas.get(i));
                        }
                    }else {
                        temp.add(mDatas.get(i));
                    }
                }else {
                    temp.add(mDatas.get(i));
                }
            }
        }
        if(temp.size()<=0){
            context.showMessage("请填入需要分配角色的分配量");
            return;
        }
        presenter.order(id,temp);
    }
    @Override
    public void initView() {
        mContext=getContext();
        context.setToolbar(toolbar,"竞价详情");
        id=getArguments().getString("id");
        adapter = new OrderBiddDelationAdapter(mDatas, mContext,id);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new OrderBiddDelationPresenter(this,mContext);

        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    presenter.getcarrive(page,id);
                    IS_RESH=true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getcarrive(page,id);
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                presenter.getcarrive(page,id);
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }
    @Override
    public void getOrderBiddLsit(List<BiddingDelationDto> dtos) {
        adapter.addMoreData(dtos);
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);

    }

    @Override
    public void isLastPage(boolean isLastPage) {
        if(isLastPage==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
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
        context.finish();
    }
}
