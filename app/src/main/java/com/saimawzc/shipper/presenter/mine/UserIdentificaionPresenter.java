package com.saimawzc.shipper.presenter.mine;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.modle.mine.identification.UserIdentificationModel;
import com.saimawzc.shipper.modle.mine.identification.UserIdentificationModelImple;
import com.saimawzc.shipper.view.mine.UserIdentificaionView;

/**
 * Created by Administrator on 2020/8/1.
 */

public class UserIdentificaionPresenter implements BaseListener {

    private Context mContext;
    UserIdentificationModel model;
    UserIdentificaionView view;

    public UserIdentificaionPresenter(UserIdentificaionView useView, Context context) {
        this.view = useView;
        this.mContext = context;
        model=new UserIdentificationModelImple();
    }

    @Override
    public void successful() {

    }

    @Override
    public void onFail(String str) {

    }

    @Override
    public void successful(int type) {

    }
}
