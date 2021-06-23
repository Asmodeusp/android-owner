package com.saimawzc.shipper.modle.mine;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.mine.PersonCenterView;


/**
 * Created by Administrator on 2020/8/8.
 * 个人中心
 */

public interface PersonCenterModel {

    void changePic(PersonCenterView view, BaseListener listener);
    void changeSex(PersonCenterView view, BaseListener listener);
    void showCamera(Context context, final BaseListener listener);

}
