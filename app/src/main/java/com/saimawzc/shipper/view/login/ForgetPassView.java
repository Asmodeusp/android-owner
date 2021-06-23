package com.saimawzc.shipper.view.login;

import com.saimawzc.shipper.view.BaseView;

/**
 * Created by Administrator on 2020/7/31.
 * 忘记密码
 */

public interface ForgetPassView extends BaseView {

    String getPhone();

    String getCode();

    String getPass();
    void changeStatus( );
}
