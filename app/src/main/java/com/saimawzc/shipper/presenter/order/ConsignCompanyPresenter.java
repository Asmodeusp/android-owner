package com.saimawzc.shipper.presenter.order;

import android.content.Context;
import com.saimawzc.shipper.dto.order.ConsignmentCompanyDto;
import com.saimawzc.shipper.modle.order.Imple.ConsignCompanyModelImple;
import com.saimawzc.shipper.modle.order.model.ConsignSignCompanyModel;
import com.saimawzc.shipper.view.order.ConsignCompanyView;
import com.saimawzc.shipper.weight.utils.listen.order.ConsignCompanyListener;
import java.util.List;
public class ConsignCompanyPresenter  implements ConsignCompanyListener {

    private Context mContext;
    ConsignSignCompanyModel model;
    ConsignCompanyView view;

    public ConsignCompanyPresenter(ConsignCompanyView useView, Context context) {
        this.view = useView;
        this.mContext = context;
        model=new ConsignCompanyModelImple();
    }
    public void getConsinList(){
        model.getCompanyList(view,this);
    }


    @Override
    public void successful() {
    }

    @Override
    public void onFail(String str) {
    view.Toast(str);
    }
    @Override
    public void successful(int type) {
    }
    @Override
    public void back(List<ConsignmentCompanyDto> dtos) {
        view.getCompany(dtos);

    }
}
