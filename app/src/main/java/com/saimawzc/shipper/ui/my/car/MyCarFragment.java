package com.saimawzc.shipper.ui.my.car;


import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.weight.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/8/1.
 * 我的车辆
 */

public class MyCarFragment extends BaseFragment{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager_title)CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage)
    ViewPager viewPager;
    private PassCarFragment passCarFragment;
    private UnPassCarFragment unPassCarFragment;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    @Override
    public int initContentView() {
        return R.layout.fragment_my_car;
    }
    @Override
    public void initView() {
        context.setToolbar(toolbar,"我的车辆");
        passCarFragment=new PassCarFragment();
        unPassCarFragment=new UnPassCarFragment();
        list = new ArrayList<Fragment>();
        list.add(passCarFragment);
        list.add(unPassCarFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("已通过"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("未通过"));
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
