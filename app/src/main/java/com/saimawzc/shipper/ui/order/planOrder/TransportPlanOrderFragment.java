package com.saimawzc.shipper.ui.order.planOrder;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.OrderListAdapter;
import com.saimawzc.shipper.adapter.order.OrderTrantsportListAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.OrderListDto;
import com.saimawzc.shipper.presenter.order.PlanOrderPresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.order.PlanOrderView;
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

//订单列表
public class TransportPlanOrderFragment extends BaseFragment implements PlanOrderView {

    @BindView(R.id.cycle) RecyclerView rv;
    private OrderTrantsportListAdapter adapter;
    private List<OrderListDto> mDatas=new ArrayList<>();
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    private int page=1;
    private NormalDialog dialog;
    private LoadMoreListener loadMoreListener;
    private PlanOrderPresenter presenter;
    private int type=2;//划订单类型,1-待处理,2-运输中,3-已完成
    private int status=100;//1.未审核 2.未分配 3.审核失败 4.竞价中 5.已分配
    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @BindView(R.id.imgSelect) ImageView imgSelete;

    @OnClick({R.id.imgSelect,R.id.llSearch})
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch:
                page=1;
                presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
                break;
        }
    }
    @Override
    public int initContentView(){
        return R.layout.fragment_orderlist;
    }

    @Override
    public void initView() {
        mContext=getContext();
        adapter = new OrderTrantsportListAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new PlanOrderPresenter(this,mContext);
        imgSelete.setVisibility(View.GONE);
        edSearch.hiddenIco();
        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(IS_RESH==false){
                    page++;
                    isLoading = false;
                    presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
                    IS_RESH=true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
        edSearch.addTextChangedListener(textWatcher);
    }
    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getplanOrder(page,type,edSearch.getText().toString(),status);

            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("from","ordersh");
                bundle.putString("id",mDatas.get(position).getId());
                bundle.putString("type","delation");
                readyGo(OrderMainActivity.class,bundle);
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
      adapter.setOnTabClickListener(new OrderTrantsportListAdapter.OnTabClickListener() {
          @Override
          public void onItemClick(String type, final int position) {
              Bundle bundle;
              if(type.equals("tab1")){//分配详情
                  bundle =new Bundle();
                  bundle.putString("type","1");
                  bundle.putString("from","zpdelation");
                  bundle.putString("id",mDatas.get(position).getId());
                  readyGo(OrderMainActivity.class,bundle);
              }else if(type.equals("tab2")){//终止运单
                  bundle =new Bundle();
                  bundle.putString("from","stoptrant");
                  bundle.putInt("type",3);
                  bundle.putString("id",mDatas.get(position).getId());
                  readyGo(OrderMainActivity.class,bundle);
              }else if(type.equals("tab3")){//暂停运输
                  String tipStr="";
                  if(mDatas.get(position).getWayBillStatus()==10){
                      tipStr="确定恢复运输该订单吗?";
                  }else {
                      tipStr="确定暂停运输该订单吗?";
                  }

                  dialog = new NormalDialog(mContext).isTitleShow(false)
                          .content(tipStr)
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
                                  if(!context.isFinishing()){
                                      dialog.dismiss();
                                  }
                                  if(mDatas.get(position).getWayBillStatus()==10){//恢复运输
                                      presenter.stopTrant(mDatas.get(position).getId(),1,"");
                                  }else {//暂停运输
                                      presenter.stopTrant(mDatas.get(position).getId(),2,"");
                                  }
                              }
                          });
                  dialog.show();
              }

          }
      });
    }
    @Override
    public void getOrderList(List<OrderListDto> orderListDtos) {
        if(!context.isEmptyStr(edSearch)){
            llSearch.setVisibility(View.GONE);
        }
        if(page==1){
            mDatas.clear();
        }
        if(orderListDtos!=null){
            adapter.addMoreData(orderListDtos);
        }
        IS_RESH=false;

    }
    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }
    @Override
    public void isLastPage(Boolean isLastpage) {
        if(isLastpage){
            loadMoreListener.isLoading = true;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
        }else {
            loadMoreListener.isLoading = false;
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_MORE);
        }
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
        presenter.getplanOrder(page,type,edSearch.getText().toString(),status);

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
}
