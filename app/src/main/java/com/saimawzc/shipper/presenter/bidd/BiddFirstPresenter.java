package com.saimawzc.shipper.presenter.bidd;

import android.content.Context;
import com.saimawzc.shipper.modle.bidd.imple.BiddFirstModelImple;
import com.saimawzc.shipper.modle.bidd.model.PBiddFirstModel;
import com.saimawzc.shipper.view.bidd.BiddFirstView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/3.
 */

public class BiddFirstPresenter {
    private Context mContext;
    PBiddFirstModel model;
    BiddFirstView view;
    public BiddFirstPresenter(BiddFirstView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new BiddFirstModelImple();
    }
    public void getPlanBiddList(String id){
        model.getBiddLsit(view,id);
    }
    public  void stopBidd(String id,int status,String type){
        model.stop(view,id,status,type);
    }
}
