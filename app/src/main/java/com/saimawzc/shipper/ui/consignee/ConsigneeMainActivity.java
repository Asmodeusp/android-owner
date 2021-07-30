package com.saimawzc.shipper.ui.consignee;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.dto.VersonDto;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.login.LoginActivity;
import com.saimawzc.shipper.ui.tab.consignee.ConsigneeMainIndexFragment;
import com.saimawzc.shipper.ui.tab.consignee.ConsigneeMineFragment;
import com.saimawzc.shipper.weight.utils.app.AppManager;
import com.gyf.immersionbar.ImmersionBar;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.saimawzc.shipper.weight.utils.dialog.UpdateDialog;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;
import com.werb.permissionschecker.PermissionChecker;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.saimawzc.shipper.constants.Constants.SHRCHANG_ROLE;

/**
 * Created by Administrator on 2020/8/14.
 * 收货人
 */

public class ConsigneeMainActivity extends BaseActivity implements OnTabSelectListener,
        BadgeDismissListener {

    @Titles
    private static final int[] mTitles = {R.string.tab1,R.string.tab5};

    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.ico_indexpaiche_blue,R.drawable.ico_index_minr_blue};
    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.ico_indexpaiche_gray, R.drawable.ico_indexmine_gray};

    @BindView(R.id.tabbar)JPTabBar mTabbar;
    private Fragment[] fragments;
    private ConsigneeMainIndexFragment mainIndexFragment;
    private ConsigneeMineFragment mineFragment;

    @Override
    protected int getViewId() {
        return R.layout.activity_consignee;
    }

    @Override
    protected void init() {
        if(!isLogin()){
            readyGo(LoginActivity.class);
        }
        mainIndexFragment = new ConsigneeMainIndexFragment();
        mineFragment = new ConsigneeMineFragment();
        mTabbar.setTabListener(this);
        initpermissionChecker();
        fragments = new Fragment[]{mainIndexFragment, mineFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mainIndexFragment)
                .add(R.id.fragmentContainer, mineFragment).hide(mineFragment).show(mainIndexFragment)
                .commit();
        mTabbar.setTabListener(this);
        //设置Badge消失的代理
        mTabbar.setDismissListener(this);
        initWithApiKey();

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindForApp();
    }

    private boolean isBreak = false;
    @Override
    public void onBackPressed() {
        if (isBreak) {
            AppManager.get().finishAllActivity();
        } else {
            isBreak = true;
            showMessage("再次点击退出应用!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBreak = false;
                }
            }, 3000);
        }
    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    @Override
    public void onDismiss(int position) {

    }

    int  currentTabIndex=0;
    @Override
    public void onTabSelect(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragmentContainer, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        currentTabIndex = index;
    }

    @Override
    public void onClickMiddle(View middleBtn) {

    }
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarDarkFont(true).
                navigationBarColor(R.color.bg).
                init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(Hawk.get(PreferenceKey.isChange_or_login, "")) &&
                (Hawk.get(PreferenceKey.isChange_or_login, "").equals("true"))) {
            Intent intent = new Intent();
            intent.setAction(SHRCHANG_ROLE);
            this.sendBroadcast(intent);
            Hawk.put(PreferenceKey.isChange_or_login, "false");
        }
    }
}
