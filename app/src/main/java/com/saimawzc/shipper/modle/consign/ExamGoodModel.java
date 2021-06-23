package com.saimawzc.shipper.modle.consign;


import android.content.Context;



import com.saimawzc.shipper.view.consign.ExamGoodsView;

import java.util.List;

public interface ExamGoodModel {

    void showCamera(ExamGoodsView view,Context context);
   void examgood(ExamGoodsView view,String id,String pics,String extent);

}
