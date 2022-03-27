package com.saimawzc.shipper.ui.order.manage;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.carrier.CarrierGroupAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.carrier.MyCarrierGroupDto;
import com.saimawzc.shipper.presenter.mine.carrive.CarriveGroupPresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.mine.carrive.CarriveGroupView;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/****
 *
 * 订单管理指派
 */
public class OrderManageAssignFragment extends BaseFragment  implements CarriveGroupView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private CarrierGroupAdapter adapter;
    private List<MyCarrierGroupDto> mDatas=new ArrayList<>();
    private CarriveGroupPresenter presenter;
    @Override
    public int initContentView() {
        return R.layout.fragment_manage_assign;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        context.setToolbar(toolbar,"指派");
        presenter=new CarriveGroupPresenter(this,mContext);
        adapter = new CarrierGroupAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter.getCarreListiv();
    }

    @Override
    public void initData() {
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putString("from","ordergroupsecond");
                bundle.putString("id",mDatas.get(position).getId());
                bundle.putString("name",mDatas.get(position).getCysName());
                readyGo(OrderMainActivity.class,bundle);
            }
            @Override
            public void onItemLongClick(View view, int position) {
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
    public void stopResh() {
    }
    @Override
    public void getCarriveList(List<MyCarrierGroupDto> groupDtos) {

        for (int i=0;i<3;i++){
            MyCarrierGroupDto my=new MyCarrierGroupDto();
            my.setCysName("张三");
            mDatas.add(my);
        }
        adapter.notifyDataSetChanged();
        // adapter.addMoreData(groupDtos);

    }
}
