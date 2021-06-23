package com.saimawzc.shipper.ui.order.creatorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.carrier.CarrierGroupAdapter;
import com.saimawzc.shipper.adapter.order.OrderAssiginSecondAdapter;
import com.saimawzc.shipper.dto.order.OrderAssignmentSecondDto;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.presenter.order.OrderCarriveSecondPresenter;
import com.saimawzc.shipper.view.order.OrderCarriveSecondView;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.saimawzc.shipper.constants.AppConfig.reshPlanOrder;

/**
 * 订单指派二级页面
 **/
public class OrderAssignmentSecondFragment extends BaseFragment implements OrderCarriveSecondView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv) RecyclerView rv;
    private OrderAssiginSecondAdapter adapter;
    private List<OrderAssignmentSecondDto> mDatas=new ArrayList<>();
    private OrderCarriveSecondPresenter presenter;
    private String id;
    private String orderId;
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;

    @Override
    public int initContentView() {
        return R.layout.fragment_orderassignemt_second;
    }


    @Override
    public void initView() {
        mContext=getActivity();
        orderId=getArguments().getString("orderId");
        context.setToolbar(toolbar,getArguments().getString("name"));
        adapter = new OrderAssiginSecondAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        id=getArguments().getString("id");
        presenter=new OrderCarriveSecondPresenter(this,mContext);
        presenter.getcarrive(id);
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                presenter.getcarrive(id);
            }
        });

    }
    @OnClick({R.id.tvSubmit})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvSubmit:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                 List<OrderAssignmentSecondDto> tempDatas=new ArrayList<>();
                for(int i=0;i<mDatas.size();i++){
                    if(mDatas.get(i)!=null){
                        if(mDatas.get(i).getTrantPrice()>0&&mDatas.get(i).getTrantNum()>0){
                            tempDatas.add(mDatas.get(i));
                        }
                    }

                }
                Log.e("msg",mDatas.size()+"临时"+tempDatas.size());
               presenter.orderZp(tempDatas,orderId);
                break;
        }

    }


    @Override
    public void getCarriveList(List<OrderAssignmentSecondDto> dtos) {
        adapter.addMoreData(dtos);
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
        Intent intent=new Intent();
        context.setResult(Activity.RESULT_OK,intent);
        context.finish();

    }
}
