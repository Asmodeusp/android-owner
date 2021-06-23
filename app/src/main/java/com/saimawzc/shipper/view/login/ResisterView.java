package com.saimawzc.shipper.view.login;

import com.saimawzc.shipper.view.BaseView;

/**
 * Created by Administrator on 2020/7/30.
 */

public interface ResisterView extends BaseView {


    String getPhone();
    String getYzm();
    String getPassWord();
    void changeStatus( );
    String resiserType();
    void oncomplete(int type);


}
