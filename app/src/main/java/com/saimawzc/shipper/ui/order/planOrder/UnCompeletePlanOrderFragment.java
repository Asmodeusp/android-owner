package com.saimawzc.shipper.ui.order.planOrder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.OrderListAdapter;
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
import com.saimawzc.shipper.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;
import static com.saimawzc.shipper.constants.AppConfig.reshPlanOrder;

//订单列表
public class UnCompeletePlanOrderFragment extends BaseFragment
        implements PlanOrderView {

    @BindView(R.id.cycle) RecyclerView rv;
    private OrderListAdapter adapter;
    private List<OrderListDto> mDatas=new ArrayList<>();
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    private int page=1;
    private NormalDialog dialog;
    private LoadMoreListener loadMoreListener;
    private PlanOrderPresenter presenter;
    private int type=1;//划订单类型,1-待处理,2-运输中,3-已完成
    private int status=100;//1.未审核 2.未分配 3.审核失败 4.竞价中 5.已分配
    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @BindView(R.id.imgSelect)ImageView imgSelect;

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
                                mDatas.clear();
                                presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlnosh://未审核
                                status=1;
                                mDatas.clear();
                                presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlnumach://未分配
                                status=2;
                                mDatas.clear();
                                presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlfailsh://审核失败
                                status=3;
                                mDatas.clear();
                                presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlbidd://竞价中
                                status=4;
                                mDatas.clear();
                                presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlalreadyfp://已分配
                                status=5;
                                mDatas.clear();
                                presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
                                popupWindowUtil.dismiss();
                                break;
                        }
                    }
                });
                break;
            case R.id.llSearch:
                page=1;
                status=100;
                presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
                break;
        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_orderlist;
    }

    @Override
    public void initView() {
        mContext=getContext();
        adapter = new OrderListAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        edSearch.hiddenIco();
        initBroadCastReceiver();
        presenter=new PlanOrderPresenter(this,mContext);
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

        adapter.setOnTabClickListener(new OrderListAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type,final int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle;
                int sendType=mDatas.get(position).getSendType();
                int checkStatus=mDatas.get(position).getCheckStatus();
                int wallBillStatue=mDatas.get(position).getWayBillStatus();
                if(checkStatus==1){//待审核
                    if(type.equals("tab1")){//编辑
                        bundle=new Bundle();
                        bundle.putString("id",mDatas.get(position).getId());
                        bundle.putString("from","editOrder");
                        readyGo(OrderMainActivity.class,bundle);
                    }else if(type.equals("tab2")){//删除
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
                                        presenter.deleteOrder(mDatas.get(position).getId());

                                    }
                                });
                        dialog.show();

                    }else if(type.equals("tab3")){//审核
                         bundle=new Bundle();
                        bundle.putString("from","ordersh");
                        bundle.putString("type","sh");
                        bundle.putString("id",mDatas.get(position).getId());
                        readyGo(OrderMainActivity.class,bundle);
                    }
                }else if(checkStatus==2){//审核通过
                    if(wallBillStatue==3){//已分配
                        if(type.equals("tab1")){//分配详情
                            bundle =new Bundle();
                            bundle.putString("from","zpdelation");
                            bundle.putString("type","1");
                            bundle.putString("id",mDatas.get(position).getId());
                            readyGo(OrderMainActivity.class,bundle);
                        }else if(type.equals("tab2")){//有剩余量继续指派
                            bundle =new Bundle();
                            bundle.putString("from","orderzp");
                            bundle.putString("type","");
                            bundle.putInt("isAppointTime",mDatas.get(position).getIsAppointTime());
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
                                               presenter.deleteOrder(mDatas.get(position).getId());
                                           }
                                       });
                               dialog.show();
                           } else if(type.equals("tab2")){//竞价
                               bundle=new Bundle();
                               bundle.putString("id",mDatas.get(position).getId());
                               bundle.putString("type","order");
                               bundle.putString("from","ordebidding");
                               readyGo(OrderMainActivity.class,bundle);
                           }else if(type.equals("tab3")){//指派
                               bundle =new Bundle();
                               bundle.putString("from","orderzp");
                               bundle.putString("type","");
                               bundle.putInt("isAppointTime",mDatas.get(position).getIsAppointTime());
                               bundle.putString("id",mDatas.get(position).getId());
                               readyGo(OrderMainActivity.class,bundle);
                           }
                        }else if(sendType==2){//竞价中
                            if(type.equals("tab1")){//竞价详情
                                bundle=new Bundle();
                                bundle.putString("id",mDatas.get(position).getId());
                                bundle.putString("from","biddfirst");
                                bundle.putString("type","orderplan");
                                readyGo(OrderMainActivity.class,bundle);
                            }else if(type.equals("tab2")){//有剩余量继续指派
                                bundle =new Bundle();
                                bundle.putString("from","orderzp");
                                bundle.putString("type","");
                                bundle.putInt("isAppointTime",mDatas.get(position).getIsAppointTime());
                                bundle.putString("id",mDatas.get(position).getId());
                                readyGo(OrderMainActivity.class,bundle);
                            }else if(type.equals("tab3")){//有余量继续竞价
                                bundle=new Bundle();
                                bundle.putString("id",mDatas.get(position).getId());
                                bundle.putString("type","order");
                                bundle.putString("from","ordebidding");
                                readyGo(OrderMainActivity.class,bundle);
                            }
                        }else if(sendType==1){//已经指派
                            if(type.equals("tab1")){//分配详情
                                bundle =new Bundle();
                                bundle.putString("from","zpdelation");
                                bundle.putString("type","1");
                                bundle.putString("id",mDatas.get(position).getId());
                                readyGo(OrderMainActivity.class,bundle);
                            }else if(type.equals("tab2")){//有剩余量继续指派
                                bundle =new Bundle();
                                bundle.putString("from","orderzp");
                                bundle.putString("type","");
                                bundle.putInt("isAppointTime",mDatas.get(position).getIsAppointTime());
                                bundle.putString("id",mDatas.get(position).getId());
                                readyGo(OrderMainActivity.class,bundle);
                            }else if(type.equals("tab3")){//有余量继续竞价
                                bundle=new Bundle();
                                bundle.putString("id",mDatas.get(position).getId());
                                bundle.putString("type","order");
                                bundle.putString("from","ordebidding");
                                readyGo(OrderMainActivity.class,bundle);
                            }
                        }
                    }
                }if(checkStatus==3){//审核未通过
                    if(type.equals("tab1")){//重新编辑
                        bundle=new Bundle();
                        bundle.putString("id",mDatas.get(position).getId());
                        bundle.putString("type","重新编辑");
                        bundle.putString("from","editOrder");
                        readyGo(OrderMainActivity.class,bundle);
                    }else if(type.equals("tab2")){//删除
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
                                        presenter.deleteOrder(mDatas.get(position).getId());
                                    }
                                });
                        dialog.show();
                    }


                }

            }
        });
    }

    @Override
    public void getOrderList(List<OrderListDto> orderListDtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(!context.isEmptyStr(edSearch)){
            llSearch.setVisibility(View.GONE);
        }
        adapter.addMoreData(orderListDtos);
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
        status=100;//初始化状态
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

    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(reshPlanOrder);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                page=1;
                presenter.getplanOrder(page,type,edSearch.getText().toString(),status);
            }
        };
        context.registerReceiver(mReceiver, filter);
    }


}
