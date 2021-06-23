package com.saimawzc.shipper.dto.order;

/****
 * 货主签收量
 * **/
public class SignWeightDto {
    private String weight;//过磅量
    private String sjSignInWeight;//司机签收量

    public String getSjSignInWeight() {
        return sjSignInWeight;
    }

    public void setSjSignInWeight(String sjSignInWeight) {
        this.sjSignInWeight = sjSignInWeight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
