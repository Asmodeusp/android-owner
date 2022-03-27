package com.saimawzc.shipper.ui.order.creatorder;


import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.adapter.BaseAdapter.IS_RESH;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.SectionedRecyclerViewAdapter;
import com.saimawzc.shipper.adapter.order.creatorder.GoodsHzListAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.creatorder.GoodsHzListDto;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.utils.LoadMoreListener;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 发货商和收货商  客户类型(1.发货地址 2.收货地址)
 * **/

public class ChooseHzListFragment extends BaseFragment {
    private GoodsHzListAdapter adapter;
    private List<GoodsHzListDto.data> mDatas = new ArrayList<>();
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.right_btn)
    TextView tvRightBtn;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tvOrder)
    TextView tvOrder;
    @BindView(R.id.edsearch)
    ClearTextEditText edSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.viewSearch)
    View viewSearch;
    private String type;
    private int page = 1;
    private LoadMoreListener loadMoreListener;

    @Override
    public int initContentView() {
        return R.layout.fragment_choosegoodscompany;
    }

    @Override
    public void initView() {
        llSearch.setVisibility(View.GONE);
        viewSearch.setVisibility(View.GONE);
        type = getArguments().getString("type");
        edSearch.hiddenIco();
        mContext = getActivity();
        adapter = new GoodsHzListAdapter(mDatas, mContext);
        edSearch.addTextChangedListener(textWatcher);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        tvOrder.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(type)) {
            if (type.equals("9")) {
                llSearch.setVisibility(View.GONE);
                viewSearch.setVisibility(View.GONE);
                tvRightBtn.setVisibility(View.GONE);
                context.setToolbar(toolbar, "货主列表");
                getHzList();
            } else if (type.equals("14")) {
                llSearch.setVisibility(View.GONE);
                viewSearch.setVisibility(View.GONE);
                tvRightBtn.setVisibility(View.GONE);
                context.setToolbar(toolbar, "货主列表");
                getHzList();
            } else if (type.equals("17")) {
                llSearch.setVisibility(View.GONE);
                viewSearch.setVisibility(View.GONE);
                tvRightBtn.setVisibility(View.GONE);
                context.setToolbar(toolbar, "货主列表");
                getHzList();
            } else if (type.equals("20")) {
                llSearch.setVisibility(View.GONE);
                viewSearch.setVisibility(View.GONE);
                tvRightBtn.setVisibility(View.GONE);
                context.setToolbar(toolbar, "货主列表");
                getHzList();
            } else if (type.equals("23")) {
                llSearch.setVisibility(View.GONE);
                viewSearch.setVisibility(View.GONE);
                tvRightBtn.setVisibility(View.GONE);
                context.setToolbar(toolbar, "货主列表");
                getHzList();
            } else if (type.equals("10")) {
                llSearch.setVisibility(View.VISIBLE);
                viewSearch.setVisibility(View.VISIBLE);
                context.setToolbar(toolbar, "验货人列表");
                loadMoreListener = new LoadMoreListener(layoutManager) {
                    @Override
                    public void onLoadMore() {
                        if (!IS_RESH) {
                            page++;
                            isLoading = false;
                            getExample();
                            IS_RESH = true;
                        }
                    }
                };
                rv.setOnScrollListener(loadMoreListener);
                getExample();
                tvRightBtn.setVisibility(View.VISIBLE);
                tvRightBtn.setText("新建");
                edSearch.setInputType(InputType.TYPE_CLASS_PHONE);
                edSearch.setHint("请输入手机号码");
            }
        }
    }

    private ArrayList<String> idList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();

    @Override
    public void initData() {
        adapter.setOnItemCheckListener(new GoodsHzListAdapter.OnItemCheckListener() {
            @Override
            public void onItemClick(View view, int position, boolean isselect) {
                if (mDatas.size() <= position) {
                    return;
                }
                if (isselect == true) {
                    idList.add(mDatas.get(position).getId());
                    nameList.add(mDatas.get(position).getUserName());
                } else {
                    idList.remove(mDatas.get(position).getId());
                    nameList.remove(mDatas.get(position).getUserName());
                }
                adapter.setList(idList);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                page = 1;
                if (!TextUtils.isEmpty(type)) {
                    if (type.equals("9")) {
                        context.setToolbar(toolbar, "货主列表");
                        getHzList();
                    } else if (type.equals("14")) {
                        context.setToolbar(toolbar, "货主列表");
                        getHzList();
                    } else if (type.equals("17")) {
                        context.setToolbar(toolbar, "货主列表");
                        getHzList();
                    } else if (type.equals("20")) {
                        context.setToolbar(toolbar, "货主列表");
                        getHzList();
                    } else if (type.equals("23")) {
                        context.setToolbar(toolbar, "货主列表");
                        getHzList();
                    }  else if (type.equals("10")) {
                        context.setToolbar(toolbar, "验货人列表");
                        getExample();
                    }
                }
            }
        });

        if (type.equals("10")) {
            tvRightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "addshr");
                    readyGo(OrderMainActivity.class, bundle);

                }
            });

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.tvOrder)
    public void click() {
//        if (idList.size() <= 0) {
//            context.showMessage("请选择");
//            return;
//        }
        Intent intent = new Intent();
        intent.putExtra("data", idList);
        intent.putExtra("nameList", nameList);
        intent.putExtra("type", type + "");
        context.setResult(RESULT_OK, intent);
        context.finish();
    }

    private void getHzList() {
        context.showLoadingDialog();
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(Hawk.get(PreferenceKey.AuthorID, ""))) {
                jsonObject.put("id", Hawk.get(PreferenceKey.AuthorID, ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.stopSwipeRefreshLayout(refreshLayout);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        context.orderApi.getHzList(body).enqueue(new CallBack<GoodsHzListDto>() {
            @Override
            public void success(GoodsHzListDto response) {
                context.dismissLoadingDialog();
                adapter.addMoreData(response.getList());
            }

            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }


    private void getExample() {
        context.showLoadingDialog();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNum", page);
            if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                jsonObject.put("userAccount", edSearch.getText().toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.stopSwipeRefreshLayout(refreshLayout);
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        context.orderApi.getyhrlist(body).enqueue(new CallBack<GoodsHzListDto>() {
            @Override
            public void success(GoodsHzListDto response) {
                context.dismissLoadingDialog();
                llSearch.setVisibility(View.GONE);
                IS_RESH = false;
                if (response.isLastPage()) {
                    loadMoreListener.isLoading = true;
                    adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_FINISH);
                } else {
                    loadMoreListener.isLoading = false;
                    adapter.changeMoreStatus(SectionedRecyclerViewAdapter.LOADING_MORE);
                }
                adapter.addMoreData(response.getList());
            }

            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
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

    @OnClick({R.id.llSearch})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.llSearch:
                page = 1;
                mDatas.clear();
                getExample();
                break;
        }
    }


}
