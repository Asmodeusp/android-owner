package com.saimawzc.shipper.ui.tab;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseImmersionFragment;
import com.saimawzc.shipper.ui.order.bidd.ManageBiddFragment;
import com.saimawzc.shipper.ui.order.bidd.PlanBiddFragment;
import com.saimawzc.shipper.ui.order.bidd.WayBillBiddFragment;
import com.saimawzc.shipper.weight.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * Created by Administrator on 2020/7/31.
 * 竞价列表
 */
public class BiddingListFragment extends BaseImmersionFragment {

    @BindView(R.id.pager_title)
    CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage)
    ViewPager viewPager;
    private PlanBiddFragment planBiddFragment;
    private WayBillBiddFragment wayBillBiddFragment;
    private ManageBiddFragment manageBiddFragment;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;

    @Override
    public int initContentView() {
        return R.layout.fragment_bidd;
    }

    @Override
    public void initView() {
        planBiddFragment=new PlanBiddFragment();
        wayBillBiddFragment=new WayBillBiddFragment();
        manageBiddFragment=new ManageBiddFragment();
        list = new ArrayList<Fragment>();
        list.add(planBiddFragment);
        list.add(wayBillBiddFragment);
        list.add(manageBiddFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("大单"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("小单"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("合单"));
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
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar(pagerTitle).
                navigationBarColor(R.color.bg).
                statusBarDarkFont(true).init();
    }
}
