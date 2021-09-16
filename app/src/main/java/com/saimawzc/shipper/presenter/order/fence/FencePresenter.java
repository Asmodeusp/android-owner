package com.saimawzc.shipper.presenter.order.fence;

import android.content.Context;
import com.saimawzc.shipper.modle.order.Imple.fence.FenceModelImple;
import com.saimawzc.shipper.modle.order.model.fence.FenceModel;
import com.saimawzc.shipper.view.order.fence.FenceView;
import java.util.List;

public class FencePresenter   {

    private Context mContext;
    FenceModel model;
    FenceView view;

    public FencePresenter(FenceView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new FenceModelImple();
    }

    public void getFenceList(String pageNum,String name){
        model.getFenceList(view,pageNum,name);
    }






}
