package com.saimawzc.shipper.view.mine.carleader;


import com.saimawzc.shipper.dto.carleader.CarLeaderListDto;
import com.saimawzc.shipper.view.BaseView;

public interface CarLeaderListView extends BaseView {

    void getList(CarLeaderListDto carLeaderListDto);

}
