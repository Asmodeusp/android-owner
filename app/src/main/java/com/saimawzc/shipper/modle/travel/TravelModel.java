package com.saimawzc.shipper.modle.travel;


import com.saimawzc.shipper.view.travel.TravelView;

public interface TravelModel {

    void getBeiDouTravel(TravelView view, String id);
    void getPreSupTravel(TravelView view, String id);
}
