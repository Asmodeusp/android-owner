package com.saimawzc.shipper.ui.my.settlement.acount;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.mysetment.AccountManageAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.SearchValueDto;
import com.saimawzc.shipper.dto.myselment.AccountManageDto;
import com.saimawzc.shipper.dto.myselment.AccountQueryPageDto;
import com.saimawzc.shipper.presenter.mine.mysetment.AccountManagePresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.mysetment.AccountManageView;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/****
 * 对账管理
 * **/
public class AccountManageFragment extends BaseFragment implements AccountManageView {

    private AccountManageAdapter adapter;
    private List<AccountManageDto>mDatas=new ArrayList<>();
    @BindView(R.id.cy)
    RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private LoadMoreListener loadMoreListener;
    private int page=1;
    AccountManagePresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.right_btn)
    TextView tvRightBtn;
    private List<SearchValueDto>searchValueDtos;

    public final int seachCode=111;
    @Override
    public int initContentView() {
        return R.layout.fragment_accountmanage;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"对账管理");
        adapter=new AccountManageAdapter(mDatas,mContext);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    presenter.getData(page,searchValueDtos);
                    IS_RESH=true;
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new AccountManagePresenter(this,mContext);
        presenter.getData(page,searchValueDtos);
    }

    @Override
    public void initData() {
        tvRightBtn.setText("查询");
        tvRightBtn.setVisibility(View.VISIBLE);
        tvRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("from","accountquery");
                readyGoForResult(OrderMainActivity.class,seachCode,bundle);

            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getData(page,searchValueDtos);
            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("id",mDatas.get(position).getId());
                bundle.putString("from","accountdelation");
                readyGo(OrderMainActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }



    @Override
    public void getData(AccountQueryPageDto dtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(dtos!=null){
            if(dtos.isLastPage()==false){
                loadMoreListener.isLoading = false;
                adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
            }else {
                loadMoreListener.isLoading = true;
                adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
            }
            adapter.addMoreData(dtos.getList());
        }
    }

    @Override
    public void stopRefresh() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==seachCode&& resultCode == RESULT_OK){
            if(searchValueDtos!=null){
                searchValueDtos.clear();
            }
            searchValueDtos = (List<SearchValueDto>) data.getSerializableExtra("list");
            page=1;
            presenter.getData(page,searchValueDtos);
        }


    }
}
