package com.saimawzc.shipper.dto.order.creatorder;

import java.util.List;

/****
 * 对账管理
 * **/
public class FencePageDto {

    private boolean isLastPage;

    private List<DangerousFenceDto>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<DangerousFenceDto> getList() {
        return list;
    }

    public void setList(List<DangerousFenceDto> list) {
        this.list = list;
    }
}
