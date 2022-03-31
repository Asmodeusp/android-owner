package com.saimawzc.shipper.dto.order;

public class selectEndStatuesDto {
    /***
     *    是否存在停运审核运单 0:否 1:是
     */

    private Integer endStatus;
    /***
     * 待审核数量
     */
    private Integer num;

    public Integer getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(Integer endStatus) {
        this.endStatus = endStatus;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
