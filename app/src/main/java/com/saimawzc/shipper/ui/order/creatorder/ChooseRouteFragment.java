package com.saimawzc.shipper.ui.order.creatorder;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.order.creatorder.ChooseRouteAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.creatorder.ChooseRouteDto;
import com.saimawzc.shipper.presenter.order.RoutePresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.order.route.RouteView;
import com.saimawzc.shipper.weight.ClearTextEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/****
 * 选择路径规划
 * ***/
public class ChooseRouteFragment extends BaseFragment implements RouteView, TextWatcher {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cy)
    RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private ChooseRouteAdapter adapter;
    private List<ChooseRouteDto>mDatas=new ArrayList<>();
    RoutePresenter presenter;
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @Override
    public int initContentView() {
        return R.layout.fragment_choose_route;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        adapter=new ChooseRouteAdapter(mDatas,mContext);
        context.setToolbar(toolbar,"路线选择");
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new RoutePresenter(this,mContext);
        presenter.getRoute(edSearch.getText().toString());
    }

    @Override
    public void initData() {
        edSearch.addTextChangedListener(this);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("data",mDatas.get(position));
                context.setResult(RESULT_OK, intent);
                context. finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.clear();
                presenter.getRoute(edSearch.getText().toString());
            }
        });
        adapter.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("from","routedelation");
                bundle.putString("id",mDatas.get(position).getId());
                readyGo(OrderMainActivity.class,bundle);

            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                presenter.getRoute(edSearch.getText().toString());
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
    public void getRoute(List<ChooseRouteDto> dtos) {
        llSearch.setVisibility(View.GONE);
        adapter.addMoreData(dtos);
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //控制登录按钮是否可点击状态
        if (!TextUtils.isEmpty(edSearch.getText().toString())) {
            llSearch.setVisibility(View.VISIBLE);
            tvSearch.setText(edSearch.getText().toString());
        } else {
            llSearch.setVisibility(View.GONE);
        }
    }
}
