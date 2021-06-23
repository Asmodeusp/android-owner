package com.saimawzc.shipper.ui.my;
import android.os.Bundle;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.base.BaseFragment;
import com.saimawzc.shipper.ui.my.carriver.CarrierSecondGroupFragment;
import com.saimawzc.shipper.ui.my.carriver.CarriveGroupFragment;
import com.saimawzc.shipper.ui.my.carriver.SearchCarriveFragment;
import com.saimawzc.shipper.ui.my.identification.CargoOwnerFragment;
import com.saimawzc.shipper.ui.my.set.MySetFragment;
import com.saimawzc.shipper.ui.my.set.suggest.AddSuggetsFragment;
import com.saimawzc.shipper.ui.my.set.suggest.SuggestDealDelationFragment;
import com.saimawzc.shipper.ui.my.set.suggest.SuggestListFragment;

/**
 * Created by Administrator on 2020/7/31.
 */

public class PersonCenterActivity extends BaseActivity {

    String comeFrom = "";
    BaseFragment mCurrentFragment;

    @Override
    protected int getViewId() {
        return R.layout.activity_personcenter;
    }
    @Override
    protected void init() {
        if (comeFrom.equals("set")) {//设置
            mCurrentFragment=new MySetFragment();
        }
        if (comeFrom.equals("set")) {//设置
            mCurrentFragment=new MySetFragment();
        }
        if (comeFrom.equals("addsuggest")) {//新增建议
            mCurrentFragment=new AddSuggetsFragment();
        }
        if (comeFrom.equals("suggestlist")) {//建议列表
            mCurrentFragment=new SuggestListFragment();
        }
        if (comeFrom.equals("suggestdelation")) {//建议详情
            mCurrentFragment=new SuggestDealDelationFragment();
        }
        if (comeFrom.equals("mycarrier")) {//承运商分组
            mCurrentFragment=new CarriveGroupFragment();
        }
        if (comeFrom.equals("invitation")) {//企业邀请码
            mCurrentFragment=new InvitationCodeFragment();
        }

        if(comeFrom.equals("huozhucarrier")){//货主认证
            mCurrentFragment=new CargoOwnerFragment();
        }
        if(comeFrom.equals("carriersecond")){//承运商分组2
            mCurrentFragment=new CarrierSecondGroupFragment();
        }
        if(comeFrom.equals("searchcarrive")){//搜索承运商
            mCurrentFragment=new SearchCarriveFragment();
        }
        if(comeFrom.equals("personcenter")){//个人中心
            mCurrentFragment=new PersonCenerFragment();
        }
        if(mCurrentFragment!=null){
            mCurrentFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, mCurrentFragment)
                    .commit();
        }
    }
    @Override
    protected void initListener() {

    }
    @Override
    protected void onGetBundle(Bundle bundle) {
        comeFrom=bundle.getString("from");
    }
}
