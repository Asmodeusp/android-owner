package com.saimawzc.shipper.presenter.set;

import android.content.Context;

import com.saimawzc.shipper.modle.mine.set.MySuggestModel;
import com.saimawzc.shipper.modle.mine.set.MySuggestModelImple;
import com.saimawzc.shipper.view.mine.set.MySuggestListView;


/**
 * Created by Administrator on 2020/7/30.
 */

public class MySuggestPresenter {

    private Context mContext;
    MySuggestListView view;
    MySuggestModel model;
    public MySuggestPresenter(MySuggestListView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MySuggestModelImple() ;
    }

    public void getErrorType(){
        model.getSuggestList(view);
    }

}
