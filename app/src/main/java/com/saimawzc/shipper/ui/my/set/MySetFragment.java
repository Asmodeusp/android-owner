package com.saimawzc.shipper.ui.my.set;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.ui.login.ForgetPassActivity;
import com.saimawzc.shipper.ui.my.PersonCenterActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MySetFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @Override
    public int initContentView() {
        return R.layout.fragment_set;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"系统设置");

    }

    @OnClick({R.id.rlforgetword,R.id.rlsuggest})
    public void click(View view){
        Bundle bundle;
        switch (view.getId()){
            case R.id.rlforgetword:
                readyGo(ForgetPassActivity.class);
                break;
            case R.id.rlsuggest:
                bundle=new Bundle();
                bundle.putString("from","addsuggest");
                readyGo(PersonCenterActivity.class,bundle);
                break;
        }

    }

    @Override
    public void initData() {

    }
}
