package com.saimawzc.shipper.view.travel;


import com.saimawzc.shipper.dto.travel.BeidouTravelDto;
import com.saimawzc.shipper.dto.travel.PresupTravelDto;
import com.saimawzc.shipper.view.BaseView;

public interface TravelView extends BaseView {

    void getBeiDouTravel(BeidouTravelDto dto);
    void getPreSupTravel(PresupTravelDto dto);

}
