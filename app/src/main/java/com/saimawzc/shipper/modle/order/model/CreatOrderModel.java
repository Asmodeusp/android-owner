package com.saimawzc.shipper.modle.order.model;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.ui.order.OrderBasicInfoFragment;
import com.saimawzc.shipper.ui.order.OrderOptionalInfoFragment;
import com.saimawzc.shipper.view.order.CreatOrderView;

public interface CreatOrderModel {

    void creatOrder(OrderBasicInfoFragment basicInfoFragment,
                    OrderOptionalInfoFragment optionalInfoFragment, CreatOrderView view, BaseListener listener);

    void editOrder(OrderBasicInfoFragment basicInfoFragment,
                    OrderOptionalInfoFragment optionalInfoFragment, CreatOrderView view, BaseListener listener,String id);

   /****参照生成**/
    void consute(OrderBasicInfoFragment basicInfoFragment,
                    OrderOptionalInfoFragment optionalInfoFragment, CreatOrderView view, BaseListener listener);


}
