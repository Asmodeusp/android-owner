package com.saimawzc.shipper.presenter.set;

import android.content.Context;
import com.saimawzc.shipper.modle.mine.set.SuggestDealtionModelImple;
import com.saimawzc.shipper.modle.mine.set.SuggestDelationModel;
import com.saimawzc.shipper.view.mine.set.MySuggestDelationView;
/**
 * Created by Administrator on 2020/7/30.
 */

public class SuggestDelationPresenter {

    private Context mContext;
    MySuggestDelationView view;
    SuggestDelationModel model;
    public SuggestDelationPresenter(MySuggestDelationView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new SuggestDealtionModelImple() ;
    }
    public void getSuggestDelation(String id){
        model.getSuggestDelaiton(view,id);
    }


}
