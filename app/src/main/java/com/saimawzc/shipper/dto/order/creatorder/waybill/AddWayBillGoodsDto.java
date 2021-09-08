package com.saimawzc.shipper.dto.order.creatorder.waybill;
import android.graphics.Paint;

import com.saimawzc.shipper.dto.order.creatorder.GoodsCompanyDto;

/**
 * 新增订单货物
 * */
public class AddWayBillGoodsDto {

    private GoodsCompanyDto goodsCompanyDto;
    private double goodNum;
    private  double goodPrice;
    private double goodPrice_two;
    private String util;
    private String utilName;
    private int bussType;

    public double getGoodPrice_two() {
        return goodPrice_two;
    }

    public void setGoodPrice_two(double goodPrice_two) {
        this.goodPrice_two = goodPrice_two;
    }

    public int getBussType() {
        return bussType;
    }

    public void setBussType(int bussType) {
        this.bussType = bussType;
    }

    public String getUtilName() {
        return utilName;
    }

    public void setUtilName(String utilName) {
        this.utilName = utilName;
    }

    public GoodsCompanyDto getGoodsCompanyDto() {
        return goodsCompanyDto;
    }

    public void setGoodsCompanyDto(GoodsCompanyDto goodsCompanyDto) {
        this.goodsCompanyDto = goodsCompanyDto;
    }

    public double getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(double goodNum) {
        this.goodNum = goodNum;
    }

    public double getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(double goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getUtil() {
        return util;
    }

    public void setUtil(String util) {
        this.util = util;
    }
}
