package com.saimawzc.shipper.dto.myselment;


import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.mysetment.WaitDispatchAdpater;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.presenter.mine.mysetment.WaitSetmentSmallOrderPresenter;
import com.saimawzc.shipper.view.mysetment.WaitSetmentSmallOrderView;
import com.saimawzc.shipper.weight.WrapContentLinearLayoutManager;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 待结算派车单
 * **/
public class WaitDispatchBillFragment extends BaseFragment implements
        WaitSetmentSmallOrderView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private WaitDispatchAdpater adpater;
    private List<WaitDispatchDto>mDatas=new ArrayList<>();
    private WaitSetmentSmallOrderPresenter presenter;
    private String id;
    private int page=1;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.right_btn)
    TextView rightBtn;
    @Override
    public int initContentView() {
        return R.layout.fragment_waitdispatch;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"待结算小单");

        adpater=new WaitDispatchAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);
        presenter=new WaitSetmentSmallOrderPresenter(this,mContext);
        id=getArguments().getString("id");
        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                isLoading = false;
                presenter.getData(page,id);

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        if(!TextUtils.isEmpty(id)){
            presenter.getData(page,id);
        }

    }
    private ArrayList<String>idList=new ArrayList<>();
    @Override
    public void initData() {
        rightBtn.setText("确定");
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idList.size()<=0){
                    context.showMessage("请选择订单");
                    return;
                }
                presenter.addSetmenet(idList);

            }
        });
        adpater.setOnItemClickListener(new WaitDispatchAdpater.OnItemCheckListener() {
            @Override
            public void onItemClick(View view, int position, boolean isselect) {
                if(mDatas.size()<=position){
                    return;
                }
                if(isselect==true){
                    idList.add(mDatas.get(position).getWayBillId());
                }else {
                    idList.remove(mDatas.get(position).getWayBillId());
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

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
        context.finish();

    }

    @Override
    public void getData(WaitDispatchQueryPageDto dto) {
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
