package com.saimawzc.shipper.ui.order.ConsultCreat;

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;

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
import com.saimawzc.shipper.adapter.order.creatorder.ConsultAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.ConsultDto;
import com.saimawzc.shipper.presenter.ConsutePresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.order.ConsultView;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;
import com.saimawzc.shipper.weight.utils.dialog.PopupWindowUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PurchaseListQueryFragment extends BaseFragment implements ConsultView, TextWatcher {
    @BindView(R.id.cycle)
    RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    private ConsutePresenter presenter;
    private int page=1;
    private LoadMoreListener loadMoreListener;
    private ConsultAdapter adapter;
    private List<ConsultDto.data> mDatas=new ArrayList<>();
    private String type=1+"";//
    private String searchType;
    @BindView(R.id.llpopuw)LinearLayout llpopuw;
    @BindView(R.id.tvpopuw)TextView tvPopuw;
    @Override
    public int initContentView() {
        return R.layout.fragment_consul;
    }
    @OnClick({R.id.llSearch,R.id.llpopuw})
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch:
                page=1;
                presenter.getConsute(type,page,searchType,edSearch.getText().toString());
                break;
            case R.id.llpopuw:
                final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                        .setContext(mContext.getApplicationContext()) //?????? context
                        .setContentView(R.layout.dialog_conslte) //??????????????????
                        .setOutSideCancel(true) //????????????????????????
                        .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setFouse(true)
                        .builder()
                        .showAsLaction(llpopuw, Gravity.LEFT,0,0);
                popupWindowUtil.setOnClickListener(new int[]{R.id.rlall, R.id.rlwuliao,R.id.rlfhcompany,
                        R.id.rlshcompany,R.id.rlno}, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.rlall://??????
                                tvPopuw.setText("??????");
                                searchType = "";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlwuliao://
                                tvPopuw.setText("??????");
                                searchType = "matericalName";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlfhcompany://
                                tvPopuw.setText("????????????");
                                searchType = "from_Name";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlshcompany://
                                tvPopuw.setText("????????????");
                                searchType = "sendTo_Name";
                                popupWindowUtil.dismiss();
                                break;
                            case R.id.rlno:
                                tvPopuw.setText("?????????");
                                searchType = "orderCode";
                                popupWindowUtil.dismiss();
                                break;
                        }
                    }
                });

                break;
        }

    }


    @Override
    public void initView() {
        presenter=new ConsutePresenter(this,mContext);
        mContext=getContext();
        adapter = new ConsultAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        edSearch.hiddenIco();
        loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(IS_RESH==false){
                    page++;
                    isLoading = false;
                    presenter.getConsute(type,page,searchType,edSearch.getText().toString());
                    IS_RESH=true;
                }

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getConsute(type,page,searchType,edSearch.getText().toString());
        edSearch.addTextChangedListener(this);
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getConsute(type,page,searchType,edSearch.getText().toString());
            }
        });
        adapter.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String type, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("orderCode",mDatas.get(position).getOrderCode());
                bundle.putString("businessType",1+"");
                bundle.putString("from","editOrder");
                readyGo(OrderMainActivity.class,bundle);
            }
        });
    }

    @Override
    public void getConsult(List<ConsultDto.data> dtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        if(!context.isEmptyStr(edSearch)){
            llSearch.setVisibility(View.GONE);
        }
        adapter.addMoreData(dtos);
        IS_RESH=false;
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);
    }

    @Override
    public void isLast(boolean islast) {
        if(islast){
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

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //???????????????????????????????????????
        if (!TextUtils.isEmpty(edSearch.getText().toString())) {
            llSearch.setVisibility(View.VISIBLE);
            tvSearch.setText(edSearch.getText().toString());
        } else {
            llSearch.setVisibility(View.GONE);
        }
    }
}
