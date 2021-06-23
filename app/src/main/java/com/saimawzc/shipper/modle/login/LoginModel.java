package com.saimawzc.shipper.modle.login;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.BaseView;
import com.saimawzc.shipper.view.login.LoginView;

/**
 * Created by Administrator on 2020/7/31.
 */

public interface LoginModel  {


   void login(LoginView view, BaseListener listener,int role,boolean ischeck);
}
