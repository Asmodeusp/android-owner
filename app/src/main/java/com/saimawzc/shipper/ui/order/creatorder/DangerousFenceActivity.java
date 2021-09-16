package com.saimawzc.shipper.ui.order.creatorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.creatorder.fence.FenceAdapter;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.order.creatorder.DangerousFenceDto;
import com.saimawzc.shipper.presenter.order.fence.FencePresenter;
import com.saimawzc.shipper.view.order.fence.FenceView;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;

public class DangerousFenceActivity extends BaseActivity implements FenceView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cy)
    RecyclerView rv;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    private FencePresenter presenter;
    private FenceAdapter adapter;
    private List<DangerousFenceDto>mDatas=new ArrayList<>();
    private LoadMoreListener loadMoreListener;
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    private int page=1;
    @Override
    protected int getViewId() {
        return R.layout.activity_dangerous_fence;
    }


    @OnClick({R.id.llSearch})
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch:
                page=1;
                presenter.getFenceList(page+"",edSearch.getText().toString());
                break;

        }
    }
    @Override
    protected void init() {
        setToolbar(toolbar,"高危围栏");
        adapter=new FenceAdapter(mDatas,mContext);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        loadMoreListener=new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if(!IS_RESH){
                    page++;
                    presenter.getFenceList(page+"",edSearch.getText().toString());
                    IS_RESH=true;
                }
            }
        };
        rv.setOnScrollListener(loadMoreListener);
        presenter=new FencePresenter(this,mContext);
        presenter.getFenceList(page+"",edSearch.getText().toString());
        edSearch.addTextChangedListener(textWatcher);
        edSearch.hiddenIco();
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getFenceList(page+"",edSearch.getText().toString());
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("data",mDatas.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    public void getFenceList(List<DangerousFenceDto> dtos) {
        llSearch.setVisibility(View.GONE);
        if(page==1){
            mDatas.clear();
            adapter.notifyDataSetChanged();
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
            adapter.changeMoreStatus(SectionedRecyclerViewAdapter.PULLUP_LOAD_MORE);
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
