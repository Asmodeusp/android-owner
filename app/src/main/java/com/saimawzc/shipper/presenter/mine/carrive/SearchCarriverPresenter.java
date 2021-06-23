package com.saimawzc.shipper.presenter.mine.carrive;

import android.content.Context;
import android.view.View;

import com.saimawzc.shipper.dto.carrier.MyCarriveDto;
import com.saimawzc.shipper.modle.mine.carrive.MyCarrierModelImple;
import com.saimawzc.shipper.modle.mine.carrive.SearchCarrierModel;
import com.saimawzc.shipper.view.BaseView;
import com.saimawzc.shipper.view.mine.carrive.SearchCarriveView;
import com.saimawzc.shipper.weight.utils.listen.carrive.SearchCarriveListener;

import java.util.List;


/**
 * Created by Administrator on 2020/8/10.
 * 我的司机
 */

public class SearchCarriverPresenter implements BaseView,SearchCarriveListener {

    private Context mContext;
    SearchCarrierModel model;
    SearchCarriveView view;



    public SearchCarriverPresenter(SearchCarriveView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MyCarrierModelImple();



    }
    public void getcarList(String phone){
        model.getCarrier(view,this,phone);

    }


    @Override
    public void showLoading() {
        view.showLoading();

    }

    @Override
    public void dissLoading() {
      view.dissLoading();
    }

    @Override
    public void Toast(String str) {
      view.Toast(str);
    }

    @Override
    public void oncomplete() {

    }



    @Override
    public void callbackbrand(MyCarriveDto searchCarDtos) {

        view.compelete(searchCarDtos);
    }
}
