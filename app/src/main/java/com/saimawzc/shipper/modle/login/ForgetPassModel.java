package com.saimawzc.shipper.modle.login;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.login.ForgetPassView;

/**
 * Created by Administrator on 2020/7/31.
 * 忘记密码
 */

public interface ForgetPassModel {

    void getCode(ForgetPassView phone, final BaseListener listener);


    void updatePass(ForgetPassView view, final BaseListener listener);

}
