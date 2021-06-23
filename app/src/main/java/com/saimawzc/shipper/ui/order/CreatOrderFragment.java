package com.saimawzc.shipper.ui.order;

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
import com.saimawzc.shipper.dto.EmptyDto;
import com.saimawzc.shipper.presenter.order.CreatOrderPresenter;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.view.order.CreatOrderView;
import com.saimawzc.shipper.weight.CaterpillarIndicator;
import com.saimawzc.shipper.weight.RepeatClickUtil;
import com.saimawzc.shipper.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.shipper.weight.utils.dialog.NormalDialog;
import com.saimawzc.shipper.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.shipper.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.saimawzc.shipper.constants.AppConfig.reshPlanOrder;
/***
 * 新增计划订单
 * ***/

public class CreatOrderFragment extends BaseFragment implements CreatOrderView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pager_title) CaterpillarIndicator pagerTitle;
    @BindView(R.id.viewpage) ViewPager viewPager;
    private OrderBasicInfoFragment basicInfoFragment;
    private OrderOptionalInfoFragment optionalInfoFragment;
    private ArrayList<Fragment> list;
    private FragmentPagerAdapter mAdapter;
    private CreatOrderPresenter presenter;
    /****编辑订单**/
    private String id="";
    /****参照生成**/
    private String orderCode;
    private String businessType;

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
        try{
            orderCode=getArguments().getString("orderCode");
            businessType=getArguments().getString("businessType");
        }catch (Exception e){

        }
        basicInfoFragment=new OrderBasicInfoFragment();
        optionalInfoFragment=new OrderOptionalInfoFragment();
        if(TextUtils.isEmpty(id)){
            if(TextUtils.isEmpty(orderCode)){
                context.setToolbar(toolbar,"新增订单");
            }else {//参照生成
                context.setToolbar(toolbar,"参照生成");
                Bundle bundle=new Bundle();
                bundle.putString("orderCode",orderCode);
                bundle.putString("businessType",businessType);
                bundle.putString("from","order");
                basicInfoFragment.setArguments(bundle);
                optionalInfoFragment.setArguments(bundle);
            }
        }else {
            context.setToolbar(toolbar,"编辑订单");
            Bundle bundle=new Bundle();
            bundle.putString("id",id);
            bundle.putString("from","order");
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
        presenter=new CreatOrderPresenter(this,mContext);
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
                    if(TextUtils.isEmpty(orderCode)){
                        presenter.creatOrder(basicInfoFragment,optionalInfoFragment,"add","");
                    }else {//参照生成
                        presenter.counsute(basicInfoFragment,optionalInfoFragment,"add","");
                    }
                }else {//编辑
                    if(TextUtils.isEmpty(getArguments().getString("type"))){//编辑
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
        intent.setAction(reshPlanOrder);
        context.sendBroadcast(intent);
        context.finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Hawk.put(PreferenceKey.AuthorID,"");
    }
}
