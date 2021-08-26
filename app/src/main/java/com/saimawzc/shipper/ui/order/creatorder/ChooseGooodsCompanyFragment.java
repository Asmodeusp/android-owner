package com.saimawzc.shipper.ui.order.creatorder;
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
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.order.creatorder.GoodsCompanyAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.creatorder.GoodsCompanyDto;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.ClearTextEditText;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.constants.AppConfig.reshWayBillAdd;

/***
 * 发货商和收货商  客户类型(1.发货地址 2.收货地址)
 * **/

public class ChooseGooodsCompanyFragment extends BaseFragment {
    private GoodsCompanyAdapter adapter;
    private List<GoodsCompanyDto>mDatas=new ArrayList<>();
    private String type;//1发货商 2收货商 3 运单类型 4 签收策略 5收货确认人 6货物名称 7运输方式
    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.right_btn) TextView tvRightBtn;
    @BindView(R.id.SwipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tvOrder)TextView  tvOrder;

    @BindView(R.id.edsearch) ClearTextEditText edSearch;
    @BindView(R.id.llSearch) LinearLayout llSearch;
    @BindView(R.id.tvSearch) TextView tvSearch;
    @BindView(R.id.viewSearch)View viewSearch;
    private String authorityId;//组织机构ID


    @Override
    public int initContentView() {
        return R.layout.fragment_choosegoodscompany;
    }
    @Override
    public void initView() {
        llSearch.setVisibility(View.GONE);
        viewSearch.setVisibility(View.GONE);
        edSearch.hiddenIco();
        initBroadCastReceiver();
        type=getArguments().getString("type");
        mContext=getActivity();

        if(type.equals("1")){
            context.setToolbar(toolbar,"发货商");
            authorityId=getArguments().getString("authorityId");
        }else if(type.equals("2")) {
            context.setToolbar(toolbar,"收货商");
            authorityId=getArguments().getString("authorityId");
        }else if(type.equals("3")){
            context.setToolbar(toolbar,"单据类型");
        }else if(type.equals("4")){
            context.setToolbar(toolbar,"签收策略");
        }else if(type.equals("5")){
            context.setToolbar(toolbar,"收货确认人");
            tvOrder.setVisibility(View.VISIBLE);
        }else if(type.equals("6")){
            context.setToolbar(toolbar,"货物名称");
            viewSearch.setVisibility(View.VISIBLE);
        }else if(type.equals("7")){
            context.setToolbar(toolbar,"运输方式");
        }else if(type.equals("8")){
            tvOrder.setVisibility(View.VISIBLE);
            context.setToolbar(toolbar,"接收对象");
        }
        if(type.equals("5")||type.equals("8")){
            adapter=new GoodsCompanyAdapter(mDatas,mContext,5);
        }else {
            adapter=new GoodsCompanyAdapter(mDatas,mContext);
        }
        edSearch.addTextChangedListener(textWatcher);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        if(type.equals("3")){//运单类型
            getBillType();
        }else if(type.equals("4")){//签收策略
            getSignStarge();
        }else if(type.equals("5")){//收货确认人
            getOrderpeopleList();
        }else if(type.equals("6")){//货物名称
            getGoodList();
        }else if(type.equals("7")){//运输方式
            getTrantWay();
        }else if(type.equals("8")){//接收对象
            getReceiceObj();
        }else {
            getGoosCompany();
        }
    }
    private ArrayList<String>idList=new ArrayList<>();
    private ArrayList<String>nameList=new ArrayList<>();
    @Override
    public void initData() {
        if(type.equals("1")||type.equals("2")){//新增收货商以及发货商
            tvRightBtn.setVisibility(View.VISIBLE);
            tvRightBtn.setText("新增");
            tvRightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putString("from","addgoodcompany");
                    bundle.putString("type",type);
                    readyGo(OrderMainActivity.class,bundle);
                }
            });
        }
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("data",mDatas.get(position));
                intent.putExtra("type",type);
                context.setResult(RESULT_OK, intent);
                context. finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        adapter.setOnItemCheckListener(new GoodsCompanyAdapter.OnItemCheckListener() {
            @Override
            public void onItemClick(View view, int position, boolean isselect) {
                if(mDatas.size()<=position){
                    return;
                }
                if(isselect==true){
                    idList.add(mDatas.get(position).getId());
                        nameList.add(mDatas.get(position).getName());
                }else {
                    idList.remove(mDatas.get(position).getId());
                    nameList.remove(mDatas.get(position).getName());
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(type.equals("3")){//运单类型
                    getBillType();
                }else if(type.equals("4")){//签收策略
                    getSignStarge();
                }else if(type.equals("5")){//收货确认人
                    getOrderpeopleList();
                }else if(type.equals("6")){
                    getGoodList();
                }else if(type.equals("8")){
                    context.stopSwipeRefreshLayout(refreshLayout);
                    getReceiceObj();
                }else if(type.equals("7")){
                    getTrantWay();
                } else {
                    getGoosCompany();
                }
            }
        });

    }
    private void getGoosCompany(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",type);
            jsonObject.put("companyId",authorityId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.stopSwipeRefreshLayout(refreshLayout);
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.getGoodsCompanyList(body).enqueue(new CallBack<List<GoodsCompanyDto>>() {
            @Override
            public void success(List<GoodsCompanyDto> response) {
                if(mDatas!=null){
                    mDatas.clear();
                }
                adapter.addMoreData(response);
                context.dismissLoadingDialog();
            }

            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
                context.showMessage(message);
            }
        });
    }

    private void getBillType(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.stopSwipeRefreshLayout(refreshLayout);
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.getBillType(body).enqueue(new CallBack<List<GoodsCompanyDto>>() {
            @Override
            public void success(List<GoodsCompanyDto> response) {
                if(mDatas!=null){
                    mDatas.clear();
                }
                context.dismissLoadingDialog();
                adapter.addMoreData(response);
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }
    /***
     * 获取签收策略
     * */
    private void getSignStarge(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.stopSwipeRefreshLayout(refreshLayout);
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.getsignStrageList(body).enqueue(new CallBack<List<GoodsCompanyDto>>() {
            @Override
            public void success(List<GoodsCompanyDto> response) {
                if(mDatas!=null){
                    mDatas.clear();
                }
                adapter.addMoreData(response);
                context.dismissLoadingDialog();
            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
                context.showMessage(message);

            }
        });
    }
    /**
     * 获取收货确认人
     * **/
    private void getOrderpeopleList(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.stopSwipeRefreshLayout(refreshLayout);
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.getOrderPeopleList(body).enqueue(new CallBack<List<GoodsCompanyDto>>() {
            @Override
            public void success(List<GoodsCompanyDto> response) {
                if(mDatas!=null){
                    mDatas.clear();
                }
                adapter.addMoreData(response);
                context.dismissLoadingDialog();
            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
                context.showMessage(message);
            }
        });
    }

    /***
     * 获取接收对象
     * **/
    private void getReceiceObj(){
        if(mDatas!=null){
            mDatas.clear();
        }
        GoodsCompanyDto companyDto1=new GoodsCompanyDto();
        companyDto1.setName("货主");
        companyDto1.setId("1");
        mDatas.add(companyDto1);
        GoodsCompanyDto companyDto2=new GoodsCompanyDto();
        companyDto2.setName("承运商");
        companyDto2.setId("2");
        mDatas.add(companyDto2);
        GoodsCompanyDto companyDto3=new GoodsCompanyDto();
        companyDto3.setName("司机");
        companyDto3.setId("3");
        mDatas.add(companyDto3);
        adapter.notifyDataSetChanged();
    }

    /***
     * 获取货物名称
     * **/
    private void getGoodList(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",type);
            jsonObject.put("name",edSearch.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.stopSwipeRefreshLayout(refreshLayout);
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.getGoodNameList(body).enqueue(new CallBack<List<GoodsCompanyDto>>() {
            @Override
            public void success(List<GoodsCompanyDto> response) {
                if(mDatas!=null){
                    mDatas.clear();
                }
                adapter.addMoreData(response);
                llSearch.setVisibility(View.GONE);
                context.dismissLoadingDialog();
            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
                context.showMessage(message);

            }
        });
    }

    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(reshWayBillAdd);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mDatas.clear();
                if(type.equals("3")){//运单类型
                    getBillType();
                }else if(type.equals("4")){//签收策略
                    getSignStarge();
                }else if(type.equals("5")){//收货确认人
                    getOrderpeopleList();
                }else if(type.equals("6")){//货物列表
                    getGoodList();
                }else {
                    getGoosCompany();
                }
            }
        };
        context.registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
    @OnClick(R.id.tvOrder)
    public void  click(){
        if(idList.size()<=0){
            context.showMessage("请选择收货确认人");
            return;
        }
        Intent intent=new Intent();
        intent.putExtra("data",idList);
        intent.putExtra("nameList",nameList);
        intent.putExtra("type",type);
        context.setResult(RESULT_OK, intent);
        context. finish();
    }

    private void getTrantWay(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.stopSwipeRefreshLayout(refreshLayout);
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.getTrantWay(body).enqueue(new CallBack<List<GoodsCompanyDto>>() {
            @Override
            public void success(List<GoodsCompanyDto> response) {
                if(mDatas!=null){
                    mDatas.clear();
                }
                context.dismissLoadingDialog();
                adapter.addMoreData(response);
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
    public void click(View view){
        switch (view.getId()){
            case R.id.llSearch:
                getGoodList();
                break;
        }
    }


}
