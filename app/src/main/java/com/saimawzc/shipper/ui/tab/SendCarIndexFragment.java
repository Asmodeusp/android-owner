package com.saimawzc.shipper.ui.tab;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseImmersionFragment;
import com.saimawzc.shipper.ui.sendcar.AlreadySendCarFrament;
import com.saimawzc.shipper.ui.sendcar.CloseSendCarFrament;
import com.saimawzc.shipper.ui.sendcar.CompeleteCarFrament;
import com.saimawzc.shipper.ui.sendcar.TrantingCarFrament;
import com.saimawzc.shipper.weight.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2020/7/31.
 * 派车
 */
public class SendCarIndexFragment extends BaseImmersionFragment {

    @BindView(R.id.viewpage)
    ViewPager viewPager;
    @BindView(R.id.pager_title)
    CaterpillarIndicator pagerTitle;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    private AlreadySendCarFrament alreadySendCarFrament;
    private TrantingCarFrament trantingCarFrament;
    private CompeleteCarFrament compeleteCarFrament;
    private CloseSendCarFrament closeSendCarFrament;

    @Override
    public int initContentView() {
        return R.layout.fragment_sendcarindex;
    }

    @Override
    public void initView() {

        alreadySendCarFrament=new AlreadySendCarFrament();
        trantingCarFrament=new TrantingCarFrament();
        compeleteCarFrament=new CompeleteCarFrament();
        closeSendCarFrament=new CloseSendCarFrament();
        list = new ArrayList<Fragment>();
        list.add(alreadySendCarFrament);
        list.add(trantingCarFrament);
        list.add(compeleteCarFrament);
        list.add(closeSendCarFrament);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("已派车"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("运输中"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("未签收"));
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
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar(pagerTitle).
                navigationBarColor(R.color.bg).
                statusBarDarkFont(true).init();
    }
}
