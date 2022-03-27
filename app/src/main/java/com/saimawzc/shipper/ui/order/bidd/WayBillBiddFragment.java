package com.saimawzc.shipper.ui.order.bidd;

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;

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
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.bidd.PlanBiddAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.bidd.PlanBiddDto;
import com.saimawzc.shipper.presenter.bidd.PlanBiddPresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.bidd.PlanBiddView;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.NoData;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;
import com.saimawzc.shipper.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 预运单竞价
 * */
public class WayBillBiddFragment extends BaseFragment
        implements PlanBiddView {

    @BindView(R.id.cy)
    RecyclerView rv;
    private PlanBiddAdapter adapter;
    private List<PlanBiddDto.planBiddData>mDatas=new ArrayList<>();
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.tvpopuw)TextView tvPopuw;
    @BindView(R.id.llpopuw)LinearLayout llpopuw;
    private PlanBiddPresenter presenter;
    private int page=1;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private LoadMoreListener loadMoreListener;
    private String searchType="";
    private String type=2+"";
    @BindView(R.id.nodata)
    NoData noData;

    @OnClick({R.id.llSearch,R.id.llpopuw})
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch:
                page=1;
                presenter.getPlanBiddList(type,page,searchType,edSearch.getText().toString());
                break;
            case R.id.llpopuw:
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext.getApplicationContext()) //设置 context
                        .setContentView(R.layout.dialog_bidd) //设置布局文件
                        .setOutSideCancel(true) //设置点击外部取消
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(llpopuw, Gravity.LEFT,0,0);
                popupWindowUtil.setOnClickListener(new int[]{R.id.rlall, R.id.rlwuliao,R.id.rlcarNo,
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
                            case R.id.rlwuliao://
                                tvPopuw.setText("物料");
                                searchType="materialsName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rldanhao://
                                tvPopuw.setText("单号");
                                searchType="waybillNo";
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
                break;
        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragmnt_planbidd;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        initBroadCastReceiver();
        adapter=new PlanBiddAdapter(mDatas,mContext,type);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new PlanBiddPresenter(this,mContext);
        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    isLoading = false;
                    presenter.getPlanBiddList(type,page,searchType,edSearch.getText().toString());
                    IS_RESH=true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getPlanBiddList(type,page,searchType,edSearch.getText().toString());
        edSearch.addTextChangedListener(textWatcher);
        edSearch.hiddenIco();
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getPlanBiddList(type,page,searchType,edSearch.getText().toString());
            }
        });
        adapter.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("type","waybill");
                bundle.putString("id",mDatas.get(position).getWaybillId());
                bundle.putString("from","biddfirst");
                readyGo(OrderMainActivity.class,bundle);
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
                bundle.putString("id",mDatas.get(position).getWaybillId());
                bundle.putString("type","delation");
                readyGo(OrderMainActivity.class,bundle);
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }
    /**
     * 监听输入框
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            //控制登录按钮是否可点击状态
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                llSearch.setVisibility(View.VISIBLE);
                tvSearch.setText(edSearch.getText().toString());
            } else {
                llSearch.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }

    @Override
    public void isLastpage(boolean islastpage) {
        if(islastpage){
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_MORE);
        }
    }

    @Override
    public void getPlanBiddList(List<PlanBiddDto.planBiddData> dtos) {
        llSearch.setVisibility(View.GONE);
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
            if(dtos==null||dtos.size()<=0){
                noData.setVisibility(View.VISIBLE);
            }else {
                noData.setVisibility(View.GONE);
            }
        }
        if(dtos!=null){
            adapter.addMoreData(dtos);
        }
        IS_RESH=false;
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
        if(TextUtils.isEmpty(PreferenceKey.HZ_IS_RZ)||!Hawk.get(PreferenceKey.HZ_IS_RZ,"").equals("1")){
            if(!str.contains("权限")){
                context.showMessage(str);
            }
        }else {
            context.showMessage(str);
            if(mDatas==null||mDatas.size()<=0){
                noData.setVisibility(View.VISIBLE);
            }else {
                noData.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void oncomplete() {
    }
    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("reshChange");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                page=1;
                presenter.getPlanBiddList(type,page,searchType,edSearch.getText().toString());
            }
        };
        context.registerReceiver(mReceiver, filter);
    }
}
