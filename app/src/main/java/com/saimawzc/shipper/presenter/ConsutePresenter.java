package com.saimawzc.shipper.presenter;

import android.content.Context;
import com.saimawzc.shipper.modle.order.Imple.ConsultModleImple;
import com.saimawzc.shipper.modle.order.model.ConsultModel;
import com.saimawzc.shipper.view.order.ConsultView;
import java.util.List;

public class ConsutePresenter  {

    private Context mContext;
    ConsultModel model;
    ConsultView view;

    public ConsutePresenter(ConsultView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ConsultModleImple();
    }

    public void getConsute(String businessType,int page,String searchtype,String searchValue){
        model.getConsult(view,businessType,page,searchtype,searchValue);
    }

}
