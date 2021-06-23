package com.saimawzc.shipper.dto.order;

import java.util.List;

public class PlanOrderPageDto {
    private int total;
    private boolean isLastPage;
    private List<OrderListDto>list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<OrderListDto> getList() {
        return list;
    }

    public void setList(List<OrderListDto> list) {
        this.list = list;
    }
}
