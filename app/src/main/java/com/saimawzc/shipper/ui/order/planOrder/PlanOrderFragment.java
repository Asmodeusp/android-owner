package com.saimawzc.shipper.ui.order.planOrder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.ui.homeindex.examine.PlanOrderExampleFragment;
import com.saimawzc.shipper.ui.homeindex.examine.WayBillExampleFragment;
import com.saimawzc.shipper.ui.order.OrderMainActivity;
import com.saimawzc.shipper.weight.CaterpillarIndicator;
import com.saimawzc.shipper.weight.utils.dialog.PopupWindowUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 计划订单
 * ***/
public class PlanOrderFragment extends BaseFragment {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pager_title) CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage) ViewPager viewPager;
    private CompeletePlanOrderFragment compeletePlanOrderFragment;
    private TransportPlanOrderFragment transportPlanOrderFragment;
    private UnCompeletePlanOrderFragment unCompeletePlanOrderFragment;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    @BindView(R.id.rightImgBtn) ImageView rightBtn;
    @Override
    public int initContentView() {
        return R.layout.fragment_plan_order;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"大单");
        compeletePlanOrderFragment=new CompeletePlanOrderFragment();
        transportPlanOrderFragment=new TransportPlanOrderFragment();
        unCompeletePlanOrderFragment=new UnCompeletePlanOrderFragment();
        list = new ArrayList<Fragment>();
        list.add(unCompeletePlanOrderFragment);
        list.add(transportPlanOrderFragment);
        list.add(compeletePlanOrderFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("待处理"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("执行中"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("已完成"));
        pagerTitle.init(0, titleInfos, viewPager);

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
       // rightBtn.setText("新建");
        rightBtn.setImageResource(R.drawable.ico_addsetment);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sowhDialog();
            }
        });
    }

    private void sowhDialog(){
        final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                .setContext(mContext.getApplicationContext()) //设置 context
                .setContentView(R.layout.dialog_add) //设置布局文件
                .setOutSideCancel(true) //设置点击外部取消
                .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setFouse(true)
                .builder()
                .showAsLaction(rightBtn, Gravity.RIGHT,0,0);
        popupWindowUtil.setOnClickListener(new int[]{R.id.rladd, R.id.rtb,
                R.id.rlczsc}, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=null;
                switch (v.getId()){
                    case R.id.rladd:
                        bundle =new Bundle();
                        bundle.putString("from","addorder");
                        readyGo(OrderMainActivity.class,bundle);
                        popupWindowUtil.dismiss();
                        break;
                    case R.id.rtb://
                        bundle=new Bundle();
                        bundle.putString("from","sync");
                        readyGo(OrderMainActivity.class,bundle);
                        popupWindowUtil.dismiss();
                        break;
                    case R.id.rlczsc://
                        bundle=new Bundle();
                        bundle.putString("from","czsc");
                        readyGo(OrderMainActivity.class,bundle);
                        popupWindowUtil.dismiss();
                        break;
                }
            }
        });
    }
}
