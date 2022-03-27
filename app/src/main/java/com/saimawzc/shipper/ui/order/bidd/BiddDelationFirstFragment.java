package com.saimawzc.shipper.ui.order.bidd;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.adapter.BaseAdapter;
import com.saimawzc.shipper.adapter.order.bidd.BiddFirstAdapter;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.dto.order.bidd.BiddFirstDto;
import com.saimawzc.shipper.presenter.bidd.BiddFirstPresenter;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.view.bidd.BiddFirstView;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BiddDelationFirstFragment extends BaseFragment
        implements BiddFirstView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cy)
    RecyclerView rv;
    private BiddFirstPresenter presenter;
    private List<BiddFirstDto>mDatas=new ArrayList<>();
    private String id;
    private BiddFirstAdapter adapter;
    private String type="";//orderplan 1 waybill 2  manage 3
    @Override
    public int initContentView() {
        return R.layout.fragment_bidd_first;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"竞价详情");
        id=getArguments().getString("id");
        adapter = new BiddFirstAdapter(mDatas, mContext);
        layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        try{
            type=getArguments().getString("type");
        }catch (Exception e){
            type=getArguments().getString("");
        }
        presenter=new BiddFirstPresenter(this,mContext);
        presenter.getPlanBiddList(id);
    }
    private NormalDialog dialog;
    @Override
    public void initData() {
        adapter.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
            @Override
            public void onItemClick(String operType, final int position) {
                if(operType.equals("rank")){
                    Bundle bundle=new Bundle();
                    bundle.putString("from","biddrank");
                    bundle.putString("id",mDatas.get(position).getId());
                    readyGo(OrderMainActivity.class,bundle);
                }else if(operType.equals("match")){
                    Bundle bundle=new Bundle();
                    if(type.equals("orderplan")){
                        bundle.putString("from","biddingdelation");
                    }else {
                        bundle.putString("from","waybillbiddingdelation");
                    }
                    bundle.putString("id",mDatas.get(position).getId());
                    bundle.putString("type",type);
                    readyGo(OrderMainActivity.class,bundle);
                }else if(operType.equals("endbidd")){//终止竞价
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("确定终止竞价吗?")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("取消", "确定");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if(!context.isFinishing()){
                                        dialog.dismiss();
                                    }
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    presenter.stopBidd(mDatas.get(position).getId(),1,type);
                                    if(!context.isFinishing()){
                                        dialog.dismiss();
                                    }
                                }
                            });
                    dialog.show();

                }else if(operType.equals("endmach")){//终止分配
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("确定终止分配吗?")
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("取消", "确定");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if(!context.isFinishing()){
                                        dialog.dismiss();
                                    }
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    presenter.stopBidd(mDatas.get(position).getId(),2,type);
                                    if(!context.isFinishing()){
                                        dialog.dismiss();
                                    }
                                }
                            });
                    dialog.show();

                }

            }
        });

    }

    @Override
    public void getPlanBiddList(List<BiddFirstDto> dtos) {
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
        mDatas.clear();
        presenter.getPlanBiddList(id);


    }
}
