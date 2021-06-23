package com.saimawzc.shipper.presenter.mine.mysetment;

import android.content.Context;

import com.saimawzc.shipper.modle.mine.mysetment.AccountDelationModel;
import com.saimawzc.shipper.modle.mine.mysetment.AccountDelationModelImple;
import com.saimawzc.shipper.view.mysetment.AccountDelationView;

/**
 * Created by Administrator on 2020/8/10.
 */

public class AccountDelationPresenter {
    private Context mContext;
    AccountDelationModel model;
    AccountDelationView view;
    public AccountDelationPresenter(AccountDelationView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new AccountDelationModelImple();
    }

    public void getData(String id){
        model.datas(id,view);
    }



}
