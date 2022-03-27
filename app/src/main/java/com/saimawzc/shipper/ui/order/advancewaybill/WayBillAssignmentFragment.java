package com.saimawzc.shipper.ui.order.advancewaybill;

import static com.saimawzc.shipper.constants.AppConfig.reshWaybIllOrder;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.waybill.WayBillAssignAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.wallbill.WayBillAssignDto;
import com.saimawzc.shipper.presenter.order.waybill.WayBillAssignPresenter;
import com.saimawzc.shipper.view.order.waybill.WayBillAssignView;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 预运单指派
 * **/
public class WayBillAssignmentFragment extends BaseFragment
        implements WayBillAssignView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle)
    RecyclerView rv;
    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private WayBillAssignAdapter adapter;
    private String id;
    @BindView(R.id.price)TextView tvPrice;
    private List<WayBillAssignDto.waybillData>mDatas=new ArrayList<>();
    WayBillAssignPresenter presenter;
    private int page=1;
    private int currentTag=10000;
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.edPrice) EditText editPrice;

    @OnClick({R.id.llSearch,R.id.llOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch:
                if(edSearch.getText().toString().length()!=11){
                    context.showMessage("请输入正确的手机号码");
                    return;
                }
                page=1;
                presenter.getWayBillList(page,edSearch.getText().toString());
                break;
            case R.id.llOrder:
                if(currentTag==10000){
                    context.showMessage("请选择承运商");
                    return;
                }
                String  price;
                if(context.isEmptyStr(editPrice.getText().toString())){
                    price=getArguments().getString("allprice");
                }else {
                    price=editPrice.getText().toString();
                }
                presenter.wayVillAssgin(mDatas.get(currentTag),id,price,getArguments().getString("type"));
                break;
        }
    }
    @Override
    public int initContentView() {
        return R.layout.fragment_waybill_assign;
    }

    @Override
    public void initView() {
        id=getArguments().getString("id");
        tvPrice.setText("总价"+getArguments().getString("allprice")+"元");
        mContext=getActivity();
        context.setToolbar(toolbar,"指派");
        edSearch.hiddenIco();
        presenter=new WayBillAssignPresenter(this,mContext);
        adapter=new WayBillAssignAdapter(mDatas,mContext);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                presenter.getWayBillList(page,edSearch.getText().toString());

            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter.getWayBillList(page,edSearch.getText().toString());



    }
    @Override
    public void initData() {
        edSearch.setText("");
        edSearch.addTextChangedListener(textWatcher);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getWayBillList(page,edSearch.getText().toString());
            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                currentTag=position;
                adapter.setmPosition(position);
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
    public void getWayBillAssign(List<WayBillAssignDto.waybillData> dtos) {
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
        }
        adapter.addMoreData(dtos);
    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(refreshLayout);

    }

    @Override
    public void isLastPage(boolean isLastpage) {
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
        Intent intent = new Intent();
        intent.setAction(reshWaybIllOrder);
        context.sendBroadcast(intent);
        context.finish();

    }
}
