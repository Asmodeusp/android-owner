package com.saimawzc.shipper.ui.tab.consignee;

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;
import static com.saimawzc.shipper.constants.Constants.SHRCHANG_ROLE;
import static com.saimawzc.shipper.constants.Constants.resSHr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.consign.ConsignAdapter;
import com.saimawzc.shipper.base.BaseImmersionFragment;
import com.saimawzc.shipper.dto.consign.ConsignDto;
import com.saimawzc.shipper.presenter.consign.ConsignPresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.consign.ConsignView;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;
import com.saimawzc.shipper.weight.utils.dialog.PopupWindowUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/14.
 * 首页
 */

public class ConsigneeMainIndexFragment  extends BaseImmersionFragment
        implements ConsignView , TextWatcher {
    private ConsignAdapter adapter;
    private List<ConsignDto.data>mDatas=new ArrayList<>();
    @BindView(R.id.cy)
    RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private ConsignPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.tvpopuw)TextView tvPopuw;
    @BindView(R.id.llpopuw)LinearLayout llpopuw;
    private String searchType="";
    @BindView(R.id.rltop)
    RelativeLayout rlTop;
    @Override
    public int initContentView() {
        return R.layout.fragment_consignee_main;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        presenter=new ConsignPresenter(this,mContext);
        adapter=new ConsignAdapter(mDatas,mContext);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        initBroadCastReceiver();
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    presenter.getConsignList(page,searchType,edSearch.getText().toString());
                    IS_RESH=true;
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getConsignList(page,searchType,edSearch.getText().toString());
        edSearch.addTextChangedListener(this);
        edSearch.hiddenIco();
    }

    @OnClick({R.id.llSearch,R.id.llpopuw})
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch:
                page=1;
                presenter.getConsignList(page,searchType,edSearch.getText().toString());
                break;
            case R.id.llpopuw:
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext.getApplicationContext()) //设置 context
                        .setContentView(R.layout.dialog_paiche) //设置布局文件
                        .setOutSideCancel(true) //设置点击外部取消
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(llpopuw, Gravity.LEFT,0,0);
                popupWindowUtil.setOnClickListener(new int[]{R.id.rlall, R.id.rlwuliao,R.id.rlcarNo,R.id.rlsjName,
                        R.id.rldanhao,R.id.rlqidi,R.id.rlfahuoshang,R.id.rlend,R.id.rlshouhuo}, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.rlall://全部
                                tvPopuw.setText("全部");
                                searchType="";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlcarNo:
                                tvPopuw.setText("车牌号");
                                searchType="carNo";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlsjName:
                                tvPopuw.setText("司机姓名");
                                searchType="sjName";
                                popupWindowUtil.dismiss();
                                break;

                            case R.id.rlwuliao://
                                tvPopuw.setText("物料");
                                searchType="materialsName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rldanhao://
                                tvPopuw.setText("单号");
                                searchType="dispatchNo";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlqidi://
                                tvPopuw.setText("起始地");
                                searchType="fromUserAddress";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlend:
                                tvPopuw.setText("目的地");
                                searchType="toUserAddress";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlfahuoshang://
                                tvPopuw.setText("发货商");
                                searchType="fromName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlshouhuo://
                                tvPopuw.setText("收货商");
                                searchType="toName";
                                popupWindowUtil.dismiss();
                                break;
                        }
                    }
                });
        }
    }


    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getConsignList(page,searchType,edSearch.getText().toString());
            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("from","waybillsh");
                bundle.putString("id",mDatas.get(position).getId());
                bundle.putString("type","delation");
                bundle.putString("role","shr");
                readyGo(OrderMainActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        adapter.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("from","examgood");
                bundle.putString("id",mDatas.get(position).getId());
                readyGo(OrderMainActivity.class,bundle);
            }
        });

    }

    @Override
    public void getConsignList(List<ConsignDto.data> dots) {
        llSearch.setVisibility(View.GONE);
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        adapter.addMoreData(dots);
        IS_RESH=false;
    }

    @Override
    public void isLastPage(boolean isLastPage) {
        if(isLastPage){
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
        }
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

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar(rlTop).
                navigationBarColor(R.color.bg).
                statusBarDarkFont(true).init();
    }
    public BroadcastReceiver mReceiver;
    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(resSHr);
        filter.addAction(SHRCHANG_ROLE);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                page=1;
                presenter.getConsignList(page,searchType,edSearch.getText().toString());
            }
        };
        context.registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mReceiver!=null){
            context.unregisterReceiver(mReceiver);
        }
    }


}
