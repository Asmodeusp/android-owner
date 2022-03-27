package com.saimawzc.shipper.ui.order.creatorder;

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.shipper.constants.AppConfig.reshPlanOrder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.order.creatorder.OrderCarrierGroupAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.creatorder.OrderCarrierGroupDto;
import com.saimawzc.shipper.presenter.order.OrderCarriveGroupPresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.order.OrderCarriveGroupView;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单指派
 **/
public class OrderAssignmentFragment extends BaseFragment
        implements OrderCarriveGroupView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private OrderCarrierGroupAdapter adapter;
    private List<OrderCarrierGroupDto> mDatas=new ArrayList<>();
    private OrderCarriveGroupPresenter presenter;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private String id;
    private String type="";
    private int isAppointTime=2;

    @Override
    public int initContentView() {
        return R.layout.fragment_orderassignemt;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        id=getArguments().getString("id");
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        try{
            type=getArguments().getString("type");
        }catch (Exception e){
        }
        try {
            isAppointTime=getArguments().getInt("isAppointTime");
        }catch (Exception e){

        }
        if(TextUtils.isEmpty(type)){
            context.setToolbar(toolbar,"指派");
        }else {
            context.setToolbar(toolbar,"竞价");
        }
        presenter=new OrderCarriveGroupPresenter(this,mContext);
        adapter = new OrderCarrierGroupAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter.getcarrive();
    }
    @Override
    public void initData() {
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                if(TextUtils.isEmpty(type)){
                    Bundle bundle=new Bundle();
                    bundle.putString("from","ordergroupsecond");
                    bundle.putString("id",mDatas.get(position).getId());
                    bundle.putString("name",mDatas.get(position).getName());
                    bundle.putInt("isAppointTime",isAppointTime);
                    bundle.putString("orderId",id);
                    readyGoForResult(OrderMainActivity.class,1005,bundle);
                }else if(type.equals("bidd")){//竞价反回数据
                    Intent intent=new Intent();
                    intent.putExtra("data",mDatas.get(position));
                    context.setResult(Activity.RESULT_OK,intent);
                    context.finish();
                }
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getcarrive();
            }
        });
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
    public void getCarriveList(List<OrderCarrierGroupDto> dtos) {
        mDatas.clear();
        adapter.addMoreData(dtos);

    }

    @Override
    public void stopResh() {
        context.stopSwipeRefreshLayout(swipeRefreshLayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1005&& resultCode == RESULT_OK){
            Intent intent = new Intent();
            intent.setAction(reshPlanOrder);
            context.sendBroadcast(intent);
            context.finish();
        }
    }
}
