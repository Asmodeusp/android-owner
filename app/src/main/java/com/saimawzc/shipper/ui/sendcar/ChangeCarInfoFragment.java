package com.saimawzc.shipper.ui.sendcar;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.order.ChangeCarAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.send.ChangeCarDto;
import com.saimawzc.shipper.presenter.order.sendcar.ChangeCarPresenter;
import com.saimawzc.shipper.view.order.sendcar.ChangeCarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*****
 * 换车信息
 * **/
public class ChangeCarInfoFragment extends BaseFragment implements ChangeCarView {

    private String id;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    private ChangeCarPresenter presenter;
    private ChangeCarAdapter adapter;
    private List<ChangeCarDto>mDatas=new ArrayList<>();

    @Override
    public int initContentView() {
        return R.layout.fragment_changecar;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"换车信息");
        id=getArguments().getString("id");
        presenter=new ChangeCarPresenter(this,mContext);
        layoutManager=new LinearLayoutManager(mContext);
        adapter=new ChangeCarAdapter(mDatas, mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        presenter.getData(id);
    }

    @Override
    public void initData() {

    }

    @Override
    public void getData(List<ChangeCarDto> dtos) {
        if(dtos!=null){
            adapter.addMoreData(dtos);
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
}
