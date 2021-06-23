package com.saimawzc.shipper.ui.homeindex;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.ui.homeindex.examine.PlanOrderExampleFragment;
import com.saimawzc.shipper.ui.homeindex.examine.WayBillExampleFragment;
import com.saimawzc.shipper.weight.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/****
 * 运单审核
 * **/
public class WaybillManageFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager_title)
    CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage)
    ViewPager viewPager;
    private PlanOrderExampleFragment planOrderExampleFragment;
    private WayBillExampleFragment wayBillExampleFragment;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;

    @Override
    public int initContentView() {
        return R.layout.fragment_waybillmanage;
    }

    @Override
    public void initView() {
        context.setToolbar(toolbar,"运单审核");
        planOrderExampleFragment=new PlanOrderExampleFragment();
        wayBillExampleFragment=new WayBillExampleFragment();
        list = new ArrayList<Fragment>();
        list.add(planOrderExampleFragment);
        list.add(wayBillExampleFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("计划单"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("预运单"));
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
}
