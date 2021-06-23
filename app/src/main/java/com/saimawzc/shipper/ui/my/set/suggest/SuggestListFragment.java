package com.saimawzc.shipper.ui.my.set.suggest;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.set.MySuggestAdpater;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.set.SuggestDto;
import com.saimawzc.shipper.presenter.set.MySuggestPresenter;
import com.saimawzc.shipper.ui.my.PersonCenterActivity;
import com.saimawzc.shipper.view.mine.set.MySuggestListView;
import com.saimawzc.shipper.weight.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***
 * 我的意见反馈列表
 * **/
public class SuggestListFragment extends BaseFragment implements MySuggestListView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    private MySuggestPresenter presenter;
    @BindView(R.id.cv) RecyclerView rv;
    private List<SuggestDto>mDatas=new ArrayList<>();
    private MySuggestAdpater adpater;


    @Override
    public int initContentView() {
        return R.layout.fragment_my_error;
    }
    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"我的反馈");
        presenter=new MySuggestPresenter(this,mContext);
        adpater=new MySuggestAdpater(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adpater);
        presenter.getErrorType();
    }

    @Override
    public void initData() {
        adpater.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                if(mDatas.get(position).getReplyStatus()==1){
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("data",mDatas.get(position));
                    bundle.putString("from","suggestdelation");
                    readyGo(PersonCenterActivity.class,bundle);

                }

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
    public void getErrorList(List<SuggestDto> myErrDtos) {
        adpater.addMoreData(myErrDtos);

    }
}
