package com.saimawzc.shipper.ui.order.bidd;


import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.bidd.OrderBiddRankAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.bidd.RankPageDto;
import com.saimawzc.shipper.presenter.bidd.BiddRankPresenter;
import com.saimawzc.shipper.view.bidd.BiddRandView;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***
 * 竞价排名
 * **/
public class BiddRankFragment extends BaseFragment implements BiddRandView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cy)
    RecyclerView rv;
    private OrderBiddRankAdapter adapter;
    BiddRankPresenter presenter;
    private List<RankPageDto.rankDto>mDatas=new ArrayList<>();
    private LoadMoreListener loadMoreListener;
    private String id;
    private int page=1;
    @Override
    public int initContentView() {
        return R.layout.fragment_rank;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"竞价排行");
        id=getArguments().getString("id");
        adapter = new OrderBiddRankAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new BiddRankPresenter(this,mContext);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    presenter.getRankList(page,id);
                    IS_RESH=true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getRankList(page,id);
    }

    @Override
    public void initData() {

    }

    @Override
    public void getRandLise(List<RankPageDto.rankDto> dtos) {
        adapter.addMoreData(dtos);
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

    }
}
