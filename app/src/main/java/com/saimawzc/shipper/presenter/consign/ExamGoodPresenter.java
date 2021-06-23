package com.saimawzc.shipper.presenter.consign;

import android.content.Context;

import com.saimawzc.shipper.modle.consign.ExamGoodModel;
import com.saimawzc.shipper.modle.consign.ExamGoodModelImple;
import com.saimawzc.shipper.view.consign.ExamGoodsView;

import java.util.List;

public class ExamGoodPresenter {

    private Context mContext;
    ExamGoodModel model;
    ExamGoodsView view;

    public ExamGoodPresenter(ExamGoodsView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new ExamGoodModelImple();
    }

    public void showCamera(Context context){
        model.showCamera(view,context) ;
    }

    public void daka(String id , String pics,String exel){
        model.examgood(view,id,pics,exel);
    }

}
