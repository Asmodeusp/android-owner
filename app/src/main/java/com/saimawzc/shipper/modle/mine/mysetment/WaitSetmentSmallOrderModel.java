package com.saimawzc.shipper.modle.mine.mysetment;




import com.saimawzc.shipper.view.mysetment.WaitSetmentSmallOrderView;

import java.util.List;

public interface WaitSetmentSmallOrderModel {

    void getList(int page, String id, WaitSetmentSmallOrderView view);

    void addsetment(List<String> idList, WaitSetmentSmallOrderView view);
}
