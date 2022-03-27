package com.saimawzc.shipper.ui.order.creatorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.order.creatorder.ConsignCompanyAdapter;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.presenter.order.ConsignCompanyPresenter;
import com.saimawzc.shipper.view.order.ConsignCompanyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * Created by Administrator on 2020/8/6.
 *
 * 选择托运公司
 */

public class ChooseConsignCompanyActivity extends BaseActivity
        implements ConsignCompanyView {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private ConsignCompanyAdapter companyAdapter;
    private List<ConsignmentCompanyDto>mDatas=new ArrayList<>();
    private ConsignCompanyPresenter presenter;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Override
    protected int getViewId() {
        return R.layout.activity_chooseconsign;
    }
    @Override
    protected void init() {
        setToolbar(toolbar,"托运公司");
        companyAdapter=new ConsignCompanyAdapter(mDatas,this);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(companyAdapter);
        presenter=new ConsignCompanyPresenter(this,this);
        presenter.getConsinList();
    }

    @Override
    protected void initListener() {
        companyAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("data",mDatas.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getConsinList();
            }
        });
    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).fitsLayoutOverlapEnable(true).
                statusBarDarkFont(true).init();
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
    public void getCompany(List<ConsignmentCompanyDto> companyDtoList) {
        mDatas.clear();
        companyAdapter.addMoreData(companyDtoList);

    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }
}
