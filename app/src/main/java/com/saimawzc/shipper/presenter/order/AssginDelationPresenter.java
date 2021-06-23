package com.saimawzc.shipper.presenter.order;

import android.content.Context;
import com.saimawzc.shipper.dto.order.creatorder.AssignDelationDto;
import com.saimawzc.shipper.modle.order.Imple.AssignDelationModleImple;
import com.saimawzc.shipper.modle.order.model.AssignDelationModel;
import com.saimawzc.shipper.view.order.AssignDelationView;
import com.saimawzc.shipper.weight.utils.listen.order.AssignDelationListener;
import java.util.List;

public class AssginDelationPresenter implements AssignDelationListener {

    private Context mContext;
    AssignDelationModel model;
    AssignDelationView view;

    public AssginDelationPresenter(AssignDelationView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new AssignDelationModleImple();
    }

    public void getcarrive(String id,int page,String type){
        model.getAssignDelationList(view,this,id,page, type);
    }





    @Override
    public void successful() {
        view.oncomplete();
    }

    @Override
    public void onFail(String str) {
    view.Toast(str);
    }
    @Override
    public void successful(int type) {

    }

    @Override
    public void back(List<AssignDelationDto.listdata> dtos) {
        view.getCarriveList(dtos);
    }
}
