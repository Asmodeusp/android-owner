package com.saimawzc.shipper.presenter.bidd;

import android.content.Context;

import com.saimawzc.shipper.modle.bidd.imple.BiddFirstModelImple;
import com.saimawzc.shipper.modle.bidd.imple.BiddRankModelImple;
import com.saimawzc.shipper.modle.bidd.model.PBiddFirstModel;
import com.saimawzc.shipper.modle.bidd.model.RankBiddModel;
import com.saimawzc.shipper.view.bidd.BiddFirstView;
import com.saimawzc.shipper.view.bidd.BiddRandView;

/**
 * Created by Administrator on 2020/8/3.
 */

public class BiddRankPresenter {
    private Context mContext;
    BiddRandView view;
    RankBiddModel model;

    public BiddRankPresenter(BiddRandView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new BiddRankModelImple();
    }

    public void getRankList(int page,String id){
        model.getRankLsit(view,page,id);
    }
}
