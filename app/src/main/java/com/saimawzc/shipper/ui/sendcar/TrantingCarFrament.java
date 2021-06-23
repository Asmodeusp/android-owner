package com.saimawzc.shipper.ui.sendcar;

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
import android.widget.LinearLayout;
import android.widget.TextView;


import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.SendCarAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.SendCarDto;
import com.saimawzc.shipper.dto.order.SignWeightDto;
import com.saimawzc.shipper.presenter.order.SendCarLsitPresenter;
import com.saimawzc.shipper.ui.baidu.TracingActivity;
import com.saimawzc.shipper.ui.baidu.utils.BitmapUtil;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.order.SendCarListView;
import com.saimawzc.shipper.weight.BottomDialogUtil;
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

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;

/***
 * 运输中
 * **/
public class TrantingCarFrament extends BaseFragment implements SendCarListView {
    @BindView(R.id.cy) RecyclerView rv;
    private SendCarAdapter adapter;
    private List<SendCarDto.SendCarData>mDatas=new ArrayList<>();
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private SendCarLsitPresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    private String status="6";//运输中
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.nodata)
    NoData noData;
    @BindView(R.id.tvpopuw)TextView tvPopuw;
    @BindView(R.id.llpopuw)LinearLayout llpopuw;
    private String searchType="";
    @OnClick({R.id.llSearch,R.id.llpopuw})
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch:
                page=1;
                presenter.getSendCarList(page,status,searchType,edSearch.getText().toString());
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
                break;
        }
    }
    @Override
    public int initContentView() {
        return R.layout.fragment_sendcar_mainindex;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        adapter=new SendCarAdapter(mDatas,mContext,"2");
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    presenter.getSendCarList(page,status,searchType,edSearch.getText().toString());
                    IS_RESH=true;
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);

        presenter=new SendCarLsitPresenter(this,mContext);
        presenter.getSendCarList(page,status,searchType,edSearch.getText().toString());
        edSearch.addTextChangedListener(textWatcher);
        edSearch.hiddenIco();
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getSendCarList(page,status,searchType,edSearch.getText().toString());
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
                readyGo(OrderMainActivity.class,bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        adapter.setOnTabClickListener(new SendCarAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, final int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle;
                if(type.equals("tab1")){//物流信息
                    bundle=new Bundle();
                    bundle.putString("from","logistsc");
                    bundle.putString("id",mDatas.get(position).getId());
                    readyGo(OrderMainActivity.class,bundle);

                }else if(type.equals("tab2")){//当前位置

                    if(mDatas.get(position).getStartTime()==0){
                        context.showMessage("当前运单尚未开始");
                        return;
                    }
                    BitmapUtil.init();
                    bundle=new Bundle();
                    bundle.putString("id",mDatas.get(position).getSjId());
                    bundle.putString("type","guiji");
                    bundle.putDouble("startTime",mDatas.get(position).getStartTime());
                    bundle.putDouble("endTime",mDatas.get(position).getEndTime());
                    bundle.putString("travelId",mDatas.get(position).getId());
                    readyGo(TracingActivity.class,bundle);
                }
            }
        });
    }

    @Override
    public void getSendCarList(List<SendCarDto.SendCarData> dtos) {
        if(page==1){
            mDatas.clear();
            if(dtos==null||dtos.size()<=0){
                noData.setVisibility(View.VISIBLE);
            }else {
                noData.setVisibility(View.GONE);
            }
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
    public void getSignWeight(SignWeightDto dto) {

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
