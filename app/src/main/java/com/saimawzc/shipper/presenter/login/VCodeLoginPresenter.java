package com.saimawzc.shipper.presenter.login;

import android.content.Context;
import android.content.Intent;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.modle.login.VCodeLoginModel;
import com.saimawzc.shipper.modle.login.VCodeLoginModelImple;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.consignee.ConsigneeMainActivity;
import com.saimawzc.shipper.view.login.VCodeLoginView;

/**
 * Created by Administrator on 2020/7/30.
 * 验证码登录
 */

public class VCodeLoginPresenter implements BaseListener {

    private Context mContext;
    VCodeLoginView view;
    VCodeLoginModel model;
    public VCodeLoginPresenter(VCodeLoginView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new VCodeLoginModelImple();
    }

    public void login(int role){//角色 1货主 2承运商 3司机 4收货人
        model.login(view,this,role);
    }

    public void getCode(){//角色 1货主 2承运商 3司机 4收货人
        model.getCode(view,this);
    }

    @Override
    public void successful() {

    }
    @Override
    public void onFail(String str) {
        view.Toast(str);
    }

    @Override
    public void successful(int type) {
        if(type==1){
            Intent intent = new Intent(mContext, MainActivity.class);
            mContext.startActivity(intent);
        }else if(type==4){
            Intent intent = new Intent(mContext, ConsigneeMainActivity.class);
            mContext.startActivity(intent);
        }else if(type==3){
            view.changeStatus();
        }

    }
}
