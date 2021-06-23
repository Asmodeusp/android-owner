package com.saimawzc.shipper.presenter.mine.change;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.modle.mine.change.ChangeRoleModel;
import com.saimawzc.shipper.modle.mine.change.ChangeRoleModelImple;
import com.saimawzc.shipper.view.mine.change.ChangeRoleView;


/**
 * Created by Administrator on 2020/7/30.
 */

public class ChangeRolePresenter implements BaseListener {

    private Context mContext;
    ChangeRoleView view;
    ChangeRoleModel model;
    public ChangeRolePresenter(ChangeRoleView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new ChangeRoleModelImple() ;
    }

    public void changeRole(int role){//角色 1货主 2承运商 3司机 4收货人
        model.changeRole(view,this,role);
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


    }
}
