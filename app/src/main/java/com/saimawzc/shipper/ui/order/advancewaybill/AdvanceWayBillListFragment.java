package com.saimawzc.shipper.ui.order.advancewaybill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.advancewaybill.WayBillListAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.wallbill.OrderWayBillDto;
import com.saimawzc.shipper.presenter.order.waybill.WayBillListPresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.order.PlanOrderView;
import com.saimawzc.shipper.view.order.waybill.WayBillListView;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;
import static com.saimawzc.shipper.constants.AppConfig.reshPlanOrder;
import static com.saimawzc.shipper.constants.AppConfig.reshWaybIllOrder;

/***
 *预运单
 * **/
public class AdvanceWayBillListFragment extends BaseFragment
        implements WayBillListView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cycle) RecyclerView rv;
    @BindView(R.id.right_btn) TextView rightBtn;
    private WayBillListAdapter adapter;
    private List<OrderWayBillDto.waybillData> mDatas=new ArrayList<>();
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    private WayBillListPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    private NormalDialog dialog;
    private int status=100;//1.未审核 2.未分配 3.审核失败 4.竞价中 5.已分配
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @BindView(R.id.imgSelect)
    ImageView imgSelect;

    @OnClick({R.id.imgSelect,R.id.llSearch})
    public void click(View view){
        switch (view.getId()){
            case R.id.imgSelect:
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext.getApplicationContext()) //设置 context
                        .setContentView(R.layout.dialog_order) //设置布局文件
                        .setOutSideCancel(true) //设置点击外部取消
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(imgSelect, Gravity.RIGHT,0,0);

                popupWindowUtil.setOnClickListener(new int[]{R.id.rlnosh, R.id.rlnumach,
                        R.id.rlfailsh,R.id.rlbidd,R.id.rlalreadyfp,R.id.rlall}, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.rlall:
                                status=11;
                                page=1;
                                presenter.getcarrive(page,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                            break;
                            case R.id.rlnosh://未审核
                                status=1;
                                page=1;
                                presenter.getcarrive(page,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlnumach://未分配
                                status=2;
                                page=1;
                                presenter.getcarrive(page,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlfailsh://审核失败
                                status=3;
                                page=1;
                                presenter.getcarrive(page,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlbidd://竞价中
                                status=4;
                                page=1;
                                presenter.getcarrive(page,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlalreadyfp://已分配
                                status=5;
                                page=1;
                                presenter.getcarrive(page,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                        }
                    }
                });

                break;
            case R.id.llSearch:
                status=100;
                page=1;
                presenter.getcarrive(page,edSearch.getText().toString(),status);
                break;
        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_advancelist;
    }
    @Override
    public void initView() {
        mContext=getContext();
        context.setToolbar(toolbar,"小单");
        initBroadCastReceiver();
        adapter = new WayBillListAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter=new WayBillListPresenter(this,mContext);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(IS_RESH==false){
                    page++;
                    presenter.getcarrive(page,edSearch.getText().toString(),status);
                    IS_RESH=true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getcarrive(page,edSearch.getText().toString(),status);
        edSearch.addTextChangedListener(textWatcher);
    }
    @Override
    public void initData() {
        rightBtn.setText("新建");
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("from","addwaybillorder");
                readyGo(OrderMainActivity.class,bundle);

            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getcarrive(page,edSearch.getText().toString(),status);
            }
        });

        adapter.setOnTabClickListener(new WayBillListAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, final int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle;
                int sendType=mDatas.get(position).getSendType();
                int checkStatus=mDatas.get(position).getCheckStatus();
                int wallBillStatue=mDatas.get(position).getWayBillStatus();
                if(checkStatus==1){//待审核
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
                    }else if(type.equals("tab2")){//清单
                        bundle=new Bundle();
                        bundle.putString("from","orderqingdan");
                        bundle.putSerializable("data",mDatas.get(position));
                        readyGo(OrderMainActivity.class,bundle);
                    }else if(type.equals("tab3")){//审核
                        bundle=new Bundle();
                        bundle.putString("id",mDatas.get(position).getId());
                        bundle.putString("from","waybillsh");
                        bundle.putString("type","sh");
                        readyGo(OrderMainActivity.class,bundle);
                    }

                }else if(checkStatus==2){//审核中

                    if(wallBillStatue==3){//已分配
                        if(type.equals("tab1")){//清单
                            bundle=new Bundle();
                            bundle.putString("from","orderqingdan");
                            bundle.putSerializable("data",mDatas.get(position));
                            readyGo(OrderMainActivity.class,bundle);
                        }else if(type.equals("tab2")){//分配详情
                            bundle =new Bundle();
                            bundle.putString("from","zpdelation");
                            bundle.putString("type","2");
                            bundle.putString("id",mDatas.get(position).getId());
                            readyGo(OrderMainActivity.class,bundle);
                        }
                    }else {
                        if(sendType==0){//未分配
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
                                                if(!context.isFinishing()){
                                                    dialog.dismiss();
                                                }
                                                presenter.delete(mDatas.get(position).getId());

                                            }
                                        });
                                dialog.show();
                            }else if(type.equals("tab2")){//清单
                                bundle=new Bundle();
                                bundle.putString("from","orderqingdan");
                                bundle.putSerializable("data",mDatas.get(position));
                                readyGo(OrderMainActivity.class,bundle);
                            }else if(type.equals("tab3")){//竞价
                                bundle=new Bundle();
                                bundle.putString("type","waybill");
                                bundle.putString("id",mDatas.get(position).getId());
                                bundle.putString("from","ordebidding");
                                readyGo(OrderMainActivity.class,bundle);
                            }else if(type.equals("tab4")){//指派
                                bundle =new Bundle();
                                bundle.putString("from","waybillzp");
                                bundle.putString("type","2");
                                bundle.putString("allprice",mDatas.get(position).getAllPrice());
                                bundle.putString("id",mDatas.get(position).getId());
                                readyGo(OrderMainActivity.class,bundle);
                            }

                        }else  if(sendType==1){//已经指派
                            if(type.equals("tab1")){//清单
                                bundle=new Bundle();
                                bundle.putString("from","orderqingdan");
                                bundle.putSerializable("data",mDatas.get(position));
                                readyGo(OrderMainActivity.class,bundle);
                            }else if(type.equals("tab2")){//分配详情
                                bundle =new Bundle();
                                bundle.putString("from","zpdelation");
                                bundle.putString("type","2");//1计划订单 2预运单  3 调度单
                                bundle.putString("id",mDatas.get(position).getId());
                                readyGo(OrderMainActivity.class,bundle);
                            }
                        }else if(sendType==2){//竞价中
                            if(type.equals("tab1")){//清单
                                bundle=new Bundle();
                                bundle.putString("from","orderqingdan");
                                bundle.putSerializable("data",mDatas.get(position));
                                readyGo(OrderMainActivity.class,bundle);
                            }else if(type.equals("tab2")){//竞价详情
                                bundle=new Bundle();
                                bundle.putString("id",mDatas.get(position).getId());
                                bundle.putString("type","waybill");
                                //bundle.putString("from","waybillbiddingdelation");
                                bundle.putString("from","biddfirst");
                                readyGo(OrderMainActivity.class,bundle);
                            }
                        }
                    }
                }else if(checkStatus==3){//未通过
                    if(type.equals("tab1")){//重新编辑
                        bundle=new Bundle();
                        bundle.putString("id",mDatas.get(position).getId());
                        bundle.putString("type","重新编辑");
                        bundle.putString("from","editWaybillOrder");
                        readyGo(OrderMainActivity.class,bundle);
                    }else if(type.equals("tab2")){
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
                                        if(!context.isFinishing()){
                                            dialog.dismiss();
                                        }
                                        presenter.delete(mDatas.get(position).getId());

                                    }
                                });
                        dialog.show();
                    }

                }

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
                bundle.putString("from","waybillsh");
                bundle.putString("type","delation");
                readyGo(OrderMainActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void getWayBillList(List<OrderWayBillDto.waybillData> dtos) {
        if(page==1){
            mDatas.clear();
        }
        llSearch.setVisibility(View.GONE);
        adapter.addMoreData(dtos);
        IS_RESH=false;
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }

    @Override
    public void isLastPage(boolean islastpage) {
        if(islastpage){
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
        presenter.getcarrive(page,edSearch.getText().toString(),status);
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

    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(reshWaybIllOrder);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                page=1;
                presenter.getcarrive(page,edSearch.getText().toString(),status);
            }
        };
        context.registerReceiver(mReceiver, filter);
    }
}
