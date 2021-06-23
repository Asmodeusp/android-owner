package com.saimawzc.shipper.ui.order.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.manage.OrderManageAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.manage.OrderManageDto;
import com.saimawzc.shipper.presenter.order.ordermange.OrderManagePresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.order.manage.OrderManageView;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;
/****
 * 运单调度
 * */
public class OrderManageFragment extends BaseFragment implements OrderManageView {
    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    private OrderManageAdapter adapter;
    private List<OrderManageDto.manageData> mDatas=new ArrayList<>();
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.tvSearch) TextView tvSearch;
    private NormalDialog dialog;
    private OrderManagePresenter presenter;
    @OnClick({R.id.tvCannel,R.id.llSearch})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvCannel:
                context.finish();
                break;
            case R.id.llSearch:
                if(context.isEmptyStr(edSearch)){
                    context.showMessage("请输入货主名");
                    return;
                }
                page=1;
                presenter.getcarrive(page,edSearch.getText().toString());
                break;
        }
    }
    @Override
    public int initContentView() {
        return R.layout.fragment_ordermanage;
    }
    @Override
    public void initView() {
        mContext=getContext();
        adapter = new OrderManageAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(IS_RESH==false){
                    page++;
                    presenter.getcarrive(page,edSearch.getText().toString());
                    IS_RESH=true;
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new OrderManagePresenter(this,mContext);
        presenter.getcarrive(page,edSearch.getText().toString());
        edSearch.addTextChangedListener(textWatcher);
    }

    @Override
    public void initData() {
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("id",mDatas.get(position).getId());
                readyGo(OrderManageMapActivity.class,bundle);

            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getcarrive(page,edSearch.getText().toString());
            }
        });

        adapter.setOnTabClickListener(new OrderManageAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, final int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle;
                int status=mDatas.get(position).getStatus();
                int sendType=mDatas.get(position).getSendType();
                if(status==1||sendType==0){//未分配
                    if(type.equals("tab1")){//删除
                        dialog = new NormalDialog(mContext).isTitleShow(false)
                                .content("确定删除该订单吗?")
                                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                                .btnNum(2).btnText("取消", "确定");
                        dialog.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        if(!context.isFinishing()){
                                            dialog.dismiss();
                                        }
                                    }
                                },
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        presenter.delete(mDatas.get(position).getId());
                                        if(!context.isFinishing()){
                                            dialog.dismiss();
                                        }
                                    }
                                });
                        dialog.show();
                    }else if(type.equals("tab2")){//竞价
                        bundle=new Bundle();
                        bundle.putString("id",mDatas.get(position).getId());
                        bundle.putSerializable("data",mDatas.get(position));
                        bundle.putString("type","manageorder");
                        bundle.putString("from","ordebidding");
                        readyGo(OrderMainActivity.class,bundle);
                    }else if(type.equals("tab3")){//指派
                        bundle =new Bundle();
                        bundle.putString("from","waybillzp");
                        bundle.putString("type","3");
                        bundle.putString("allprice",mDatas.get(position).getAllPrice()+"");
                        bundle.putString("id",mDatas.get(position).getId());
                        readyGo(OrderMainActivity.class,bundle);
                    }
                }else if(status==3||status==4){//已分配
                    if(type.equals("tab1")){//分配详情
                        bundle =new Bundle();
                        bundle.putString("from","zpdelation");
                        bundle.putString("type","3");//1计划订单 2预运单  3 调度单
                        bundle.putString("id",mDatas.get(position).getId());
                        readyGo(OrderMainActivity.class,bundle);
                    }
                }else if(status==2){//已竞价和指派
                    if(sendType==1){//已指派
                        if(type.equals("tab1")){
                            bundle =new Bundle();
                            bundle.putString("from","zpdelation");
                            bundle.putString("type","3");//1计划订单 2预运单  3 调度单
                            bundle.putString("id",mDatas.get(position).getId());
                            readyGo(OrderMainActivity.class,bundle);
                        }
                    }else if(sendType==2){//竞价中
                        if(type.equals("tab1")){//竞价详情
                            bundle=new Bundle();
                            bundle.putString("id",mDatas.get(position).getId());
                            //bundle.putString("from","biddingdelation");
                            bundle.putString("type","manage");
                            bundle.putString("from","biddfirst");
                            readyGo(OrderMainActivity.class,bundle);
                        }
                    }
                }
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
    public void isLastPage(boolean isLastpage ) {
        if(isLastpage){
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_MORE);
        }
    }
    @Override
    public void getOrderManageList(List<OrderManageDto.manageData> dtos) {

        llSearch.setVisibility(View.GONE);
        if(page==1){
            mDatas.clear();
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
        context.showMessage(str);
    }
    @Override
    public void oncomplete() {
        page=1;
        presenter.getcarrive(page,edSearch.getText().toString());
    }
}