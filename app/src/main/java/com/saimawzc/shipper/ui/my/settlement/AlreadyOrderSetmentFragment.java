package com.saimawzc.shipper.ui.my.settlement;

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;
import static com.saimawzc.shipper.constants.Constants.reshAccount_confirm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.mysetment.MySetmentAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.SearchValueDto;
import com.saimawzc.shipper.dto.myselment.MySetmentDto;
import com.saimawzc.shipper.dto.myselment.MySetmentPageQueryDto;
import com.saimawzc.shipper.presenter.mine.mysetment.MySetmentPresenter;
import com.saimawzc.shipper.view.mysetment.MySetmentView;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***
 *已确认结算单
 * **/
public class AlreadyOrderSetmentFragment extends BaseFragment
        implements MySetmentView {
    private MySetmentAdapter adapter;
    private List<MySetmentDto> mDatas=new ArrayList<>();
    @BindView(R.id.cy)
    RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private LoadMoreListener loadMoreListener;
    private int page=1;
    private MySetmentPresenter presenter;
    public List<SearchValueDto>searchValueDtos;
    private int checkstatus=3;

    @Override
    public int initContentView() {
        return R.layout.fragment_setlement_;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        adapter=new MySetmentAdapter(mDatas,mContext,2);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    presenter.getData(page,checkstatus,searchValueDtos);
                    IS_RESH=true;
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new MySetmentPresenter(this,mContext);
        presenter.getData(page,checkstatus,searchValueDtos);
        initBroadCastReceiver();
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                if(searchValueDtos!=null){
                    searchValueDtos.clear();
                }
                presenter.getData(page,checkstatus,searchValueDtos);
            }
        });
    }

    @Override
    public void getMySetment(MySetmentPageQueryDto dtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }

        if(dtos.isLastPage()==false){
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }else {
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }
        adapter.addMoreData(dtos.getList());
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
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
    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(reshAccount_confirm);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                page=1;
                searchValueDtos= (List<SearchValueDto>) intent.getSerializableExtra("list");
                presenter.getData(page,checkstatus,searchValueDtos);
            }
        };
        context.registerReceiver(mReceiver, filter);
    }
}
