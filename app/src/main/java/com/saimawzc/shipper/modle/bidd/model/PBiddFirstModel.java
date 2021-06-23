package com.saimawzc.shipper.modle.bidd.model;
import com.saimawzc.shipper.view.bidd.BiddFirstView;

public interface PBiddFirstModel {

    void getBiddLsit(BiddFirstView view, String id);
    void stop(BiddFirstView view, String id,int status,String type);

}
