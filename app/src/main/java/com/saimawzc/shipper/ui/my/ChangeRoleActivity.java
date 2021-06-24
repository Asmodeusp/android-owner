package com.saimawzc.shipper.ui.my;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.presenter.mine.change.ChangeRolePresenter;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.consignee.ConsigneeMainActivity;
import com.saimawzc.shipper.view.mine.change.ChangeRoleView;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/8/13.
 * 切换角色
 */

public class ChangeRoleActivity extends BaseActivity implements ChangeRoleView {
    private int currentRole;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.btnChangRole)TextView btnChangRole;
    private ChangeRolePresenter presenter;

    @OnClick({R.id.llchangeRole,R.id.llchangeApp})
    public void click(View view){
        switch (view.getId()){
            case R.id.llchangeRole:
                if(TextUtils.isEmpty(currentRole+"")){
                  showMessage("为获取到当前角色信息");
                    return;
                }
                if(currentRole==1){
                    presenter.changeRole(4);
                }else if(currentRole==4){
                    presenter.changeRole(1);
                }
                break;
            case R.id.llchangeApp:
                if(context.checkPackInfo("com.saimawzc.freight")){
                    ComponentName componentName = new ComponentName
                            ("com.saimawzc.freight", "com.saimawzc.freight.ui.login.SplashActivity");//这里是 包名  以及 页面类的全称
                    Intent intent = new Intent();
                    intent.setComponent(componentName);
                    intent.putExtra("type", "112");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    context.showMessage("尚未安装该APP，请前往安装");
                }
                break;
        }
    }
    @Override
    protected int getViewId() {
        return R.layout.fragment_changerole;
    }

    @Override
    protected void init() {
        context.setToolbar(toolbar,"切换角色");
        presenter=new ChangeRolePresenter(this,this);
        if(currentRole==1){//司机
            btnChangRole.setText("收货人");
        }else if(currentRole==4){//承运商
            btnChangRole.setText("货主");
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        try{
            currentRole=bundle.getInt("currentrole");
        }catch (Exception e){
        }
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void dissLoading() {
      dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
         showMessage(str);
    }

    @Override
    public void oncomplete() {
        //showMessage("更换成功");
        Hawk.put(PreferenceKey.isChange_or_login,"true");
        if(currentRole==1){
            readyGo(ConsigneeMainActivity.class);
        }else {
            readyGo(MainActivity.class);
        }

    }

    @Override
    public Context getContext() {
        return this;
    }
}
