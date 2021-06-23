package com.saimawzc.shipper.presenter.mine;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.modle.mine.PersonCenterModel;
import com.saimawzc.shipper.view.mine.PersonCenterView;


/**
 * Created by Administrator on 2020/8/8.
 */

public class PersonCenterPresenter implements BaseListener {

    private Context mContext;
    PersonCenterModel model;
    PersonCenterView view;

    public PersonCenterPresenter(PersonCenterView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new PersonCenterModelImple();
    }

    public void showCamera(){
        model.showCamera(mContext,this);
    }

    public void changePic(){
        model.changePic(view,this);
    }

    @Override
    public void successful() {
        view.oncomplete();

    }

    @Override
    public void onFail(String str) {
        view.Toast(str);

    }

    @Override
    public void successful(int type) {
        view.OnDealCamera(type);
    }
}
