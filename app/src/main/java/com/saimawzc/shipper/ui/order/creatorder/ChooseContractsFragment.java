package com.saimawzc.shipper.ui.order.creatorder;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.constants.AppConfig.reshContact;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.order.creatorder.ContractsAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.creatorder.ContarctsDto;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 选择联系人
 * **/
public class ChooseContractsFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private ContractsAdapter adapter;
    List<ContarctsDto>mDatas=new ArrayList<>();
    @BindView(R.id.right_btn) TextView rightBtn;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Override
    public int initContentView() {
        return R.layout.fragment_choosecontarcts;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"选择联系人");
        adapter=new ContractsAdapter(mDatas,mContext);
        layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        initBroadCastReceiver();
        getData();
    }

    @Override
    public void initData() {
        rightBtn.setText("新增");
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("from","addcontracts");
                readyGo(OrderMainActivity.class,bundle);

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
                context.setResult(RESULT_OK, intent);
                context. finish();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                getData();
            }
        });

    }
    private void getData(){
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("name","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        context.showLoadingDialog();
        context.stopSwipeRefreshLayout(refreshLayout);
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.getContracts(body).enqueue(new CallBack<List<ContarctsDto>>() {
            @Override
            public void success(List<ContarctsDto> response) {
                adapter.addMoreData(response);
                context.dismissLoadingDialog();
            }

            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();

            }
        });

    }
    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(reshContact);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mDatas.clear();
                getData();

            }
        };
        context.registerReceiver(mReceiver, filter);
    }
}
