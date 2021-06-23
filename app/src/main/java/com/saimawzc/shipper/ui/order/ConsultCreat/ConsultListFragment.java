package com.saimawzc.shipper.ui.order.ConsultCreat;

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

public class ConsultListFragment extends BaseFragment  {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager_title)
    CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage)
    ViewPager viewPager;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    private SaleListQueryFragment saleListQueryFragment;
    private PurchaseListQueryFragment purchaseListQueryFragment;
    private AllotListQueryFragment allotListQueryFragment;
    @Override
    public int initContentView() {
        return R.layout.fragment_creatconsult;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"参照生成");
        saleListQueryFragment=new SaleListQueryFragment();
        purchaseListQueryFragment=new PurchaseListQueryFragment();
        allotListQueryFragment=new AllotListQueryFragment();
        list = new ArrayList<Fragment>();
        list.add(saleListQueryFragment);
        list.add(purchaseListQueryFragment);
        list.add(allotListQueryFragment);
        List<CaterpillarIndicator.TitleInfo> titleInfos = new ArrayList<>();
        titleInfos.add(new CaterpillarIndicator.TitleInfo("销售通知单"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("采购通知单"));
        titleInfos.add(new CaterpillarIndicator.TitleInfo("调拨通知单"));
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
