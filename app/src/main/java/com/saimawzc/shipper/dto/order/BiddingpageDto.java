package com.saimawzc.shipper.dto.order;

import java.util.List;

/****
 * 竞价详情
 * **/
public class BiddingpageDto {

    private boolean isFirstPage;
    private boolean isLastPage;
    private List<BiddingDelationDto>list;

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<BiddingDelationDto> getList() {
        return list;
    }

    public void setList(List<BiddingDelationDto> list) {
        this.list = list;
    }
}
