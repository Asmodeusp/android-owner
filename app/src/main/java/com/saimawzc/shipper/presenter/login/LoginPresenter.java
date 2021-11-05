package com.saimawzc.shipper.presenter.login;

import android.content.Context;
import android.content.Intent;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.modle.login.LoginModel;
import com.saimawzc.shipper.modle.login.LoginModelImple;
import com.saimawzc.shipper.ui.MainActivity;
import com.saimawzc.shipper.ui.consignee.ConsigneeMainActivity;
import com.saimawzc.shipper.view.login.LoginView;

/**
 * Created by Administrator on 2020/7/30.
 */

public class LoginPresenter implements BaseListener {

    private Context mContext;
    LoginView view;
    LoginModel model;
    public LoginPresenter(LoginView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new LoginModelImple();
    }

    public void login(int role,boolean ischeck){//角色 1货主 2承运商 3司机 4收货人
        model.login(view,
                this,role,ischeck);
    }

    @Override
    public void successful() {
        //view.oncomplete();
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
       }

    }
}
