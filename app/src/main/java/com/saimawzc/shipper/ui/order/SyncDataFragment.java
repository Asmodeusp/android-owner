package com.saimawzc.shipper.ui.order;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.presenter.order.SyncPresenter;
import com.saimawzc.shipper.view.order.SyncView;
import com.saimawzc.shipper.weight.TimeChooseDialogUtil;
import com.saimawzc.shipper.weight.utils.listen.TimeChooseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SyncDataFragment extends BaseFragment implements SyncView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvStartTime) TextView tvStartTime;
    @BindView(R.id.tvEndTime)TextView tvEndTime;
    @BindView(R.id.tvtype)TextView tvType;
    private SyncPresenter presenter;

    private TimeChooseDialogUtil timeChooseDialogUtil;
    private List<String>lists=new ArrayList<>();

    @Override
    public int initContentView() {
        return R.layout.fragment_sync;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"同步数据");
        presenter=new SyncPresenter(this,mContext);
        tvStartTime.setText(BaseActivity.getCurrentTime("yyyy-MM-dd"));
        tvEndTime.setText(BaseActivity.getCurrentTime("yyyy-MM-dd"));
    }

    @Override
    public void initData() {

    }
    @OnClick({R.id.tvStartTime,R.id.tvsybc,R.id.tvEndTime,R.id.tvtype})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvStartTime:
                showTime(1);
                break;
            case R.id.tvEndTime:
                showTime(2);
                break;
            case R.id.tvtype:
                if(lists.size()==0){
                    lists.add("同步销售通知单");
                    lists.add("同步采购通知单");
                    lists.add("同步调拨通知单");
                }
                initOptionPicker(lists);
                break;
            case R.id.tvsybc:
                if(context.isEmptyStr(tvStartTime)){
                    context.showMessage("请选择开始时间");
                    return;
                }
                if(context.isEmptyStr(tvEndTime)){
                    context.showMessage("请选择结束时间");
                    return;
                }
                if(context.isEmptyStr(tvType)){
                    context.showMessage("请选择同步类型");
                    return;
                }
                presenter.sync(tvStartTime.getText().toString(),tvEndTime.getText().toString(),
                        tvType.getText().toString());
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
        context.showMessage("同步成功");
        tvStartTime.setText(BaseActivity.getCurrentTime("yyyy-MM-dd"));
        tvEndTime.setText(BaseActivity.getCurrentTime("yyyy-MM-dd"));
        tvType.setText("");

    }
    private void showTime(final int type){
        if(timeChooseDialogUtil==null){
            timeChooseDialogUtil=new TimeChooseDialogUtil(mContext);
        }
        timeChooseDialogUtil.showDialog(new TimeChooseListener() {
            @Override
            public void getTime(String result) {
                if(type==1){
                    tvStartTime.setText(result);
                }else if(type==2){
                    tvEndTime.setText(result);

                }


            }
            @Override
            public void cancel() {
                timeChooseDialogUtil.dissDialog();
            }
        });
    }
    private OptionsPickerView optionsPickerView;//底部滚轮实现
    private void initOptionPicker(final List<String> optionList){

        optionsPickerView = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str = optionList.get(options1);
                tvType.setText(str);


            }
        }).setCancelColor(Color.GRAY).
                setSubmitColor(Color.RED).build();
        optionsPickerView.setNPicker(optionList,null,null);

        optionsPickerView.show();
    }
}
