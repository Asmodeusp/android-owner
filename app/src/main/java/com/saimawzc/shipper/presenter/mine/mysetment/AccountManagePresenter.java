package com.saimawzc.shipper.presenter.mine.mysetment;

import android.content.Context;

import com.saimawzc.shipper.dto.SearchValueDto;
import com.saimawzc.shipper.modle.mine.mysetment.AccountManageModel;
import com.saimawzc.shipper.modle.mine.mysetment.AccountManageModelImple;
import com.saimawzc.shipper.modle.mine.mysetment.MySetmentModel;
import com.saimawzc.shipper.modle.mine.mysetment.MySetmentModelImple;
import com.saimawzc.shipper.view.mysetment.AccountManageView;
import com.saimawzc.shipper.view.mysetment.MySetmentView;

import java.util.List;

/**
 * Created by Administrator on 2020/8/3.
 */

public class AccountManagePresenter {
    private Context mContext;
    AccountManageModel model;
    AccountManageView view;

    public AccountManagePresenter(AccountManageView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new AccountManageModelImple();
    }
    public void getData(int page, List<SearchValueDto> list){
        model.getList(page,list,view);
    }


}
