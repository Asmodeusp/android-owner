package com.saimawzc.shipper.view.login;

import android.content.Context;

import com.saimawzc.shipper.view.BaseView;

/**
 * Created by Administrator on 2020/7/31.
 */

public interface LoginView extends BaseView {

    String getPhone();
    String getPass();
    Context getContext();
}
