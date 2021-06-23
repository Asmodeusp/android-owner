package com.saimawzc.shipper.modle.mine.carrive;


import com.saimawzc.shipper.view.mine.carrive.SearchCarriveView;
import com.saimawzc.shipper.weight.utils.listen.carrive.SearchCarriveListener;

/**
 * Created by Administrator on 2020/8/10.
 */

public interface SearchCarrierModel {
    void getCarrier(SearchCarriveView view, SearchCarriveListener listener,String phone);
}
