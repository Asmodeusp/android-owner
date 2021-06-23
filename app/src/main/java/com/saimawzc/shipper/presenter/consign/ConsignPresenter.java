package com.saimawzc.shipper.presenter.consign;

import android.content.Context;
import com.saimawzc.shipper.modle.consign.ConsignModel;
import com.saimawzc.shipper.modle.consign.ConsignModelImple;
import com.saimawzc.shipper.view.consign.ConsignView;
/**
 * Created by Administrator on 2020/7/30.
 * 忘记密码
 */

public class ConsignPresenter  {

    private Context mContext;
    ConsignView view;
    ConsignModel model;

    public ConsignPresenter(ConsignView iLoginView, Context context) {
        this.view = iLoginView;
        this.mContext = context;
        model=new ConsignModelImple();
    }

    public void getConsignList(int page,String searchtype,String searchvalue){
        model.getConsign(view,page,searchtype,searchvalue);
    }


}
