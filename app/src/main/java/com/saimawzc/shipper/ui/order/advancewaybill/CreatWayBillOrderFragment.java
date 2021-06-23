package com.saimawzc.shipper.ui.order.advancewaybill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.presenter.order.waybill.CreatWayBillOrderPresenter;
import com.saimawzc.shipper.ui.order.OrderBasicInfoWayBillFragment;
import com.saimawzc.shipper.ui.order.OrderOptionalInfoFragment;
import com.saimawzc.shipper.view.BaseView;
import com.saimawzc.shipper.weight.CaterpillarIndicator;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

import static com.saimawzc.shipper.constants.AppConfig.reshPlanOrder;
import static com.saimawzc.shipper.constants.AppConfig.reshWaybIllOrder;

/***
 * 新增预订单
 * ***/

public class CreatWayBillOrderFragment extends BaseFragment implements BaseView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pager_title) CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage) ViewPager viewPager;
    private OrderBasicInfoWayBillFragment basicInfoFragment;
    private OrderOptionalInfoFragment optionalInfoFragment;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    private CreatWayBillOrderPresenter presenter;
    private String id="";

    @Override
    public int initContentView() {
        return R.layout.fragment_creatorder;
    }
    @Override
    public void initView() {
        try {
            id=getArguments().getString("id");
        }catch (Exception e){

        }
        basicInfoFragment=new OrderBasicInfoWayBillFragment();
        optionalInfoFragment=new OrderOptionalInfoFragment();
        if(TextUtils.isEmpty(id)){
            context.setToolbar(toolbar,"新增订单");
        }else {
            context.setToolbar(toolbar,"编辑订单");
            Bundle bundle=new Bundle();
            bundle.putString("id",id);
            bundle.putString("from","waybill");
            basicInfoFragment.setArguments(bundle);
            optionalInfoFragment.setArguments(bundle);
        }
        list = new ArrayList<Fragment>();
        list.add(basicInfoFragment);
        list.add(optionalInfoFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("基础信息"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("选填信息"));
        pagerTitle.init(0, titleInfos, viewPager);
        presenter=new CreatWayBillOrderPresenter(this,mContext);
        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }
            @Override
            public int getCount() {
                return list.size();
            }
        };
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvOrder://确认添加
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(TextUtils.isEmpty(id)){
                    presenter.creatOrder(basicInfoFragment,optionalInfoFragment,"add","");
                }else {//编辑

                    if(TextUtils.isEmpty(getArguments().getString("type"))){//
                        presenter.creatOrder(basicInfoFragment,optionalInfoFragment,"edit",id);
                    }else {//重新编辑
                        presenter.creatOrder(basicInfoFragment,optionalInfoFragment,"add","");
                    }
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
        intent.setAction(reshWaybIllOrder);
        context.sendBroadcast(intent);
        context.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Hawk.put(PreferenceKey.AuthorID,"");
    }
}
