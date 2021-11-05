package com.saimawzc.shipper.ui.sendcar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;


import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.weight.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**物流信息**/

public class YujingFragmeng extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager_title)
    CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage)
    ViewPager viewPager;
    private LogisticsInfoFragment logistacsInfoFragment;
    private WarninginfoFragmet warninginfoFragmet;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    @Override
    public int initContentView() {
        return  R.layout.activity_logistacsinfo;
    }

    @Override
    public void initView() {
        context.setToolbar(toolbar,"运输信息");
        logistacsInfoFragment=new LogisticsInfoFragment();
        warninginfoFragmet=new WarninginfoFragmet();
        logistacsInfoFragment.setArguments(getArguments());
        warninginfoFragmet.setArguments(getArguments());
        list = new ArrayList<Fragment>();
        list.add(logistacsInfoFragment);
        list.add(warninginfoFragmet);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("物流信息"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("预警信息"));
        pagerTitle.init(0, titleInfos, viewPager);

        mAdapter=new FragmentPagerAdapter(getChildFragmentManager()) {
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
