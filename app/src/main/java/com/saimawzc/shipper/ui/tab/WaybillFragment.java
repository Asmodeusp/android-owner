package com.saimawzc.shipper.ui.tab;

import android.os.Bundle;
import android.view.View;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.Collection;

import butterknife.OnClick;
/**
 * Created by Administrator on 2020/7/31.
 * 运单
 */
public class WaybillFragment extends BaseFragment{

    @OnClick({R.id.rlwaybill,R.id.rlplanbill,R.id.rlmanagebill})
    public void click(View view){
        Bundle bundle=null;
        switch (view.getId()){
            case R.id.rlplanbill://计划订单
                bundle=new Bundle();
                bundle.putString("from","planorder");
                readyGo(OrderMainActivity.class,bundle);
                break;
            case R.id.rlwaybill://预运单
                bundle=new Bundle();
                bundle.putString("from","mywaybill");
                readyGo(OrderMainActivity.class,bundle);
                break;
            case R.id.rlmanagebill:
                bundle=new Bundle();
                bundle.putString("from","ordermanage");
                readyGo(OrderMainActivity.class,bundle);
                break;
        }
    }
    @Override
    public int initContentView() {
        return R.layout.fragment_yundan;
    }

    @Override
    public void initView() {



    }
    @Override
    public void initData() {
    }
}
