package com.saimawzc.shipper.presenter.mine.carleader;

import android.content.Context;

import com.saimawzc.shipper.modle.mine.carleader.TaxiAdressModel;
import com.saimawzc.shipper.modle.mine.carleader.imple.TaxiAdressModelImple;
import com.saimawzc.shipper.view.mine.carleader.TaxiAdressView;


/**
 *申请撤销
 */

public class TaxiChooseAdressPresenter {

    private Context mContext;
    TaxiAdressView view;
    TaxiAdressModel model;

    public TaxiChooseAdressPresenter(TaxiAdressView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new TaxiAdressModelImple() ;
    }
    public void getAdress(String pid){
        model.getAdressList(view,pid);
    }
}
