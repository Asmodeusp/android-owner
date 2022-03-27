package com.saimawzc.shipper.ui.order.advancewaybill;


import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.order.advancewaybill.OrderInventoryAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.wallbill.OrderInventoryDto;
import com.saimawzc.shipper.dto.order.wallbill.OrderWayBillDto;
import com.saimawzc.shipper.presenter.order.waybill.WayBillInventoryPresenter;
import com.saimawzc.shipper.view.order.waybill.WayBillInventoryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/***
 * 预运单清单
 * **/

public class OrderWayBillInventoryFragment extends BaseFragment implements WayBillInventoryView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cy)
    RecyclerView rv;
    private OrderInventoryAdapter adapter;
    private List<OrderInventoryDto.qdData> mDatas=new ArrayList<>();
    private OrderWayBillDto.waybillData data;
    @BindView(R.id.tvSendAdress) TextView tvSendAdress;
    @BindView(R.id.tvReceiveAdress)TextView tvReceiveAdress;
    private WayBillInventoryPresenter presenter;
    @Override
    public int initContentView() {
        return R.layout.fragment_waybillinventory;
    }

    @Override
    public void initView() {
        mContext=getContext();
        context.setToolbar(toolbar,"清单");
        data= (OrderWayBillDto.waybillData) getArguments().getSerializable("data");
        adapter = new OrderInventoryAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        if(data!=null){
            tvSendAdress.setText(data.getFromProName());
            tvReceiveAdress.setText(data.getToProName());
            presenter=new WayBillInventoryPresenter(this,mContext);
            presenter.getInventoryList(data.getId());
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void getInventoryList(List<OrderInventoryDto.qdData> dtos) {
        adapter.addMoreData(dtos);

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
}
