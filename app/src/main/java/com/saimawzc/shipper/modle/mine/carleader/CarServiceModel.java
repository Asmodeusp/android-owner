package com.saimawzc.shipper.modle.mine.carleader;


import android.content.Context;

import com.saimawzc.shipper.view.mine.carleader.CarServiceView;


/**
 * Created by Administrator on 2020/7/31.
 */

public interface CarServiceModel {


   void edit(CarServiceView view);
   void showCamera(CarServiceView view, Context context, int type);
   void getCarType(CarServiceView view, CarTypeListener listener);
   void getBrand(CarServiceView view, CarBrandListener listener, String type);
   void getSfInfo(CarServiceView view, String carId, String cdzId, String sjId);

}
