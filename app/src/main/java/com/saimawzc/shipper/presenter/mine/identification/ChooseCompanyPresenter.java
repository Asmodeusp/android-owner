package com.saimawzc.shipper.presenter.mine.identification;

import android.content.Context;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.identification.CompanyDto;
import com.saimawzc.shipper.modle.mine.identification.ChooseCompanyModel;
import com.saimawzc.shipper.modle.mine.identification.ChooseCompanyModelImple;
import com.saimawzc.shipper.view.mine.identificaion.ChooseCompanyView;
import com.saimawzc.shipper.weight.utils.listen.identifiction.ChooseCopmanyListener;

import java.util.List;

/**
 * Created by Administrator on 2020/8/6.
 */

public class ChooseCompanyPresenter implements ChooseCopmanyListener {

    private Context mContext;
    ChooseCompanyModel model;
    ChooseCompanyView view;

    public ChooseCompanyPresenter(ChooseCompanyView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ChooseCompanyModelImple();
    }

    public void getCompanyList(String companyName){
        model.getCompanyList(companyName,this);

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
    public void getCompanyList(List<CompanyDto> companyDtoList) {
        view.getCompany(companyDtoList);
    }
}
