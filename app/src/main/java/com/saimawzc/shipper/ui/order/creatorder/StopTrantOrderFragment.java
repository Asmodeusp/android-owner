package com.saimawzc.shipper.ui.order.creatorder;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.presenter.order.StopTrantOrderPresenter;
import com.saimawzc.shipper.view.BaseView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.saimawzc.shipper.constants.AppConfig.reshPlanOrder;
import static com.saimawzc.shipper.constants.AppConfig.reshTrangts;

/****
 * 终止运输
 * */
public class StopTrantOrderFragment extends BaseFragment implements BaseView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tvOrderId) TextView tvOrderId;
    @BindView(R.id.edReason) EditText edReason;
    private StopTrantOrderPresenter presenter;
    private String id;
    private int type;


    @Override
    public int initContentView() {
        return R.layout.fragment_stop_trantorder;
    }


    @Override
    public void initView() {
        mContext=getActivity();
        id=getArguments().getString("id");
        tvOrderId.setText(id);
        presenter=new StopTrantOrderPresenter(this,mContext);

        try {
            type=getArguments().getInt("type");
            if(type==2){
                context.setToolbar(toolbar,"暂停运输");
            }else if(type==3){
                context.setToolbar(toolbar,"终止运输");
            }else if(type==1){
                context.setToolbar(toolbar,"恢复运输");
            }
        }catch (Exception e){

        }



    }

    @Override
    public void initData() {


    }
    @OnClick({R.id.tvSunmit})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvSunmit:
                if(context.isEmptyStr(edReason)){
                    context.showMessage("请选择理由");
                    return;
                }
                if(!TextUtils.isEmpty(type+"")){
                    presenter.stopTrantOrder(id,type,edReason.getText().toString());

                }
                break;

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
        Intent intent = new Intent();
        intent.setAction(reshTrangts);
        context.sendBroadcast(intent);
        context.finish();
    }
}