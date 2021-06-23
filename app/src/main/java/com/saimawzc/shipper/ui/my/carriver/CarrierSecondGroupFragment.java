package com.saimawzc.shipper.ui.my.carriver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.carrier.CarrierGroupAdapter;
import com.saimawzc.shipper.adapter.carrier.MyCarierGroupSecondAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.dto.carrier.MycarrierGroupSecondDto;
import com.saimawzc.shipper.presenter.mine.carrive.CarriveSecondGroupPresenter;
import com.saimawzc.shipper.ui.my.PersonCenterActivity;
import com.saimawzc.shipper.view.mine.carrive.CarriveSecondView;
import com.saimawzc.shipper.weight.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.saimawzc.shipper.constants.AppConfig.reshCarrive;

/**
 * Created by Administrator on 2020/8/11.
 * 承运商二级分组
 */
public class CarrierSecondGroupFragment extends BaseFragment
        implements CarriveSecondView {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.rv)SlideRecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    private MyCarierGroupSecondAdapter adapter;
    private List<MycarrierGroupSecondDto>mDatas=new ArrayList<>();
    private CarriveSecondGroupPresenter  presenter;
    private String id;
    @BindView(R.id.right_btn)TextView btnRight;

    @Override
    public int initContentView() {
        return R.layout.fragment_secondgroup;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,getArguments().getString("name"));
        layoutManager = new LinearLayoutManager(mContext);
        presenter=new CarriveSecondGroupPresenter(this,mContext);
        adapter = new MyCarierGroupSecondAdapter(mDatas, mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        id=getArguments().getString("id");
        presenter.getCarreListiv(id);
        initBroadCastReceiver();
    }
    @Override
    public void initData() {
        btnRight.setText("添加");
        btnRight.setVisibility(View.VISIBLE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                presenter.getCarreListiv(id);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("from","searchcarrive");
                bundle.putString("groupId",id);
                readyGo(PersonCenterActivity.class,bundle);

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
    public void showLoading() {
        context.showLoadingDialog();

    }

    @Override
    public void dissLoading() {
      context.dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
      context.isEmptyStr(str);
    }

    @Override
    public void oncomplete() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }

    @Override
    public void getCarriveList(List<MycarrierGroupSecondDto> groupDtos) {
        adapter.addMoreData(groupDtos);
    }

    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(reshCarrive);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mDatas.clear();
                presenter.getCarreListiv(id);
            }
        };
        context.registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
