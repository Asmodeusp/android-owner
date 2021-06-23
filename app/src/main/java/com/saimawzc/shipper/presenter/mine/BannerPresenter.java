package com.saimawzc.shipper.presenter.mine;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.modle.mine.BannerModel;
import com.saimawzc.shipper.modle.mine.BannerModelImple;
import com.saimawzc.shipper.modle.mine.PersonCenterModel;
import com.saimawzc.shipper.view.mine.BannerView;
import com.saimawzc.shipper.view.mine.PersonCenterView;


/**
 * Created by Administrator on 2020/8/8.
 */

public class BannerPresenter   {

    private Context mContext;
    BannerModel model;
    BannerView view;

    public BannerPresenter(BannerView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new BannerModelImple();
    }

    public void getBanner(){
        model.getBanner(view);
    }


}
