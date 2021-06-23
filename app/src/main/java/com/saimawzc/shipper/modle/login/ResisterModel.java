package com.saimawzc.shipper.modle.login;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.login.LoginView;
import com.saimawzc.shipper.view.login.ResisterView;

/**
 * Created by Administrator on 2020/7/30.
 */

public interface ResisterModel {

    void getCode(String phone, final BaseListener listener);
    void login(ResisterView view, String pass,BaseListener listener);

    void resiser(ResisterView view, final BaseListener listener);
}
