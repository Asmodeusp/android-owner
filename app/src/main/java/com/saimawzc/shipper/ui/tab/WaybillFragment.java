package com.saimawzc.shipper.ui.tab;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.selectEndStatuesDto;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.utils.api.OrderApi;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/7/31.
 * 运单
 */
public class WaybillFragment extends BaseFragment {
    @BindView(R.id.redMessage)
    TextView redMessage;
    public OrderApi orderApi = Http.http.createApi(OrderApi.class);

    @OnClick({R.id.rlwaybill, R.id.rlplanbill, R.id.rlmanagebill})
    public void click(View view) {
        Bundle bundle = null;
        switch (view.getId()) {
            case R.id.rlplanbill://计划订单
                bundle = new Bundle();
                bundle.putString("from", "planorder");
                readyGo(OrderMainActivity.class, bundle);
                break;
            case R.id.rlwaybill://预运单
                bundle = new Bundle();
                bundle.putString("from", "mywaybill");
                readyGo(OrderMainActivity.class, bundle);
                break;
            case R.id.rlmanagebill:
                bundle = new Bundle();
                bundle.putString("from", "ordermanage");
                readyGo(OrderMainActivity.class, bundle);
                break;
        }
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_yundan;
    }

    @Override
    public void initView() {
        getSelectEndStatues();

    }

    private void getSelectEndStatues() {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                orderApi.selectEndStatues().enqueue(new CallBack<selectEndStatuesDto>() {
                    @Override
                    public void success(final selectEndStatuesDto response) {
                        if (response != null) {
                            if (response.getEndStatus() != 1) {
                                redMessage.setVisibility(View.GONE);
                            } else {
                                redMessage.setVisibility(View.VISIBLE);
                                redMessage.setText(response.getNum()+"");
                            }
                        }


                    }

                    @Override
                    public void fail(String code, String message) {
                        context.showMessage(message);
                    }
                });
            }
        });
    }

    @Override
    public void initData() {
    }

}
