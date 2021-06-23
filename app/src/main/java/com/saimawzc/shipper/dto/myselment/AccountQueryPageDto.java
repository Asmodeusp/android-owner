package com.saimawzc.shipper.dto.myselment;

import java.util.List;

/****
 * 对账管理
 * **/
public class AccountQueryPageDto {

    private boolean isLastPage;

    private List<AccountManageDto>list;

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<AccountManageDto> getList() {
        return list;
    }

    public void setList(List<AccountManageDto> list) {
        this.list = list;
    }
}
