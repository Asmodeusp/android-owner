package com.saimawzc.shipper.presenter.mine.mysetment;

import android.content.Context;

import com.saimawzc.shipper.modle.mine.mysetment.WaitSetmentModel;
import com.saimawzc.shipper.modle.mine.mysetment.WaitSetmentModelImple;
import com.saimawzc.shipper.view.mysetment.WaitSetmentView;


/**
 * Created by Administrator on 2020/8/3.
 */

public class WaitSetmentPresenter {
    private Context mContext;
    WaitSetmentModel model;
    WaitSetmentView view;

    public WaitSetmentPresenter(WaitSetmentView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new WaitSetmentModelImple();
    }
    public void getData(int page){
        model.getList(page,view);
    }


}
