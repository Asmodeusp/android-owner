package com.saimawzc.shipper.presenter.order;

import android.content.Context;
import com.saimawzc.shipper.dto.order.SendCarDto;
import com.saimawzc.shipper.modle.order.Imple.SendCarLsitModelImple;
import com.saimawzc.shipper.modle.order.model.SendCarLsitModel;
import com.saimawzc.shipper.view.order.SendCarListView;
import com.saimawzc.shipper.weight.utils.listen.order.SendCarListListener;
import java.util.List;

/**
 * Created by Administrator on 2020/7/30.
 */
public class SendCarLsitPresenter implements SendCarListListener {

    private Context mContext;
    SendCarListView view;
    SendCarLsitModel model;

    public SendCarLsitPresenter(SendCarListView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new SendCarLsitModelImple() ;
    }
    public void getSendCarList(int page,String status,String searchType,String searchValue){
        model.getSendCarLsit(view,this,page,status,searchType,searchValue);
    }

    /***
     * 获取签收量
     * **/
    public void getSignWeight(String id){
        model.getsignWeight(view,id);

    }




    public void sign(String id,String num){
        model.sign(view,id,num);
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
    public void getManageOrderList(List<SendCarDto.SendCarData> dtos) {
        view.getSendCarList(dtos);

    }
}
