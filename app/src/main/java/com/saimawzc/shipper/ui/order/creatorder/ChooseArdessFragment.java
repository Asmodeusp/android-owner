package com.saimawzc.shipper.ui.order.creatorder;

import static com.saimawzc.shipper.constants.AppConfig.reshWayBillAdd;

import android.app.Activity;
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
import com.saimawzc.shipper.adapter.order.creatorder.AdressAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.creatorder.AdressDto;
import com.saimawzc.shipper.dto.order.creatorder.GoodsCompanyDto;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.utils.http.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
/***
 * 选择地址
 * **/
public class ChooseArdessFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.right_btn) TextView btnRight;
    private String type;
    private String unitId;//
    @BindView(R.id.rv)
    RecyclerView rv;
    private AdressAdapter adressAdapter;
    private List<AdressDto>mDatas=new ArrayList<>();

    private GoodsCompanyDto companyDto;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    @Override
    public int initContentView() {
        return R.layout.fragment_chooseadress;
    }

    @Override
    public void initView() {
        type=getArguments().getString("type");
        companyDto= (GoodsCompanyDto) getArguments().getSerializable("data");
        if(type.equals("1")){
            context.setToolbar(toolbar,"选择发货地址");
        }else {
            context.setToolbar(toolbar,"选择收货地址");
        }
        unitId=getArguments().getString("unitId");
        mContext=getActivity();
        layoutManager=new LinearLayoutManager(mContext);
        adressAdapter=new AdressAdapter(mDatas,mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adressAdapter);
        getAdress();
        initBroadCastReceiver();

    }

    @Override
    public void initData() {
        btnRight.setText("添加");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("from","addadress");
                bundle.putSerializable("data",companyDto);
                bundle.putString("type",type);
                readyGo(OrderMainActivity.class,bundle);
            }
        });

        adressAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("type",type);
                intent.putExtra("data",mDatas.get(position));
                context.setResult(Activity.RESULT_OK,intent);
                context.finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                getAdress();
            }
        });
    }

    private void getAdress(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("unitId",unitId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
          context.stopSwipeRefreshLayout(refreshLayout);
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        context.orderApi.getAdressList(body).enqueue(new CallBack<List<AdressDto>>() {
            @Override
            public void success(List<AdressDto> response) {
                adressAdapter.addMoreData(response);

            }

            @Override
            public void fail(String code, String message) {
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
                getAdress();
            }
        };
        context.registerReceiver(mReceiver, filter);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
