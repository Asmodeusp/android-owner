package com.saimawzc.shipper.presenter.set;

import android.content.Context;

import com.saimawzc.shipper.modle.mine.set.AddSuggestModel;
import com.saimawzc.shipper.modle.mine.set.AddSuggestModelImple;
import com.saimawzc.shipper.view.mine.set.AddSuggetsView;


/**
 * Created by Administrator on 2020/7/30.
 */

public class AddSuggestPresenter  {

    private Context mContext;
    AddSuggetsView view;
    AddSuggestModel model;
    public AddSuggestPresenter(AddSuggetsView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new AddSuggestModelImple() ;
    }
    public void submit(String contect,String pic){
        model.submit(view,contect,pic);
    }

    public void showCamera(Context context){
        model.showCamera(view,context) ;
    }
}
