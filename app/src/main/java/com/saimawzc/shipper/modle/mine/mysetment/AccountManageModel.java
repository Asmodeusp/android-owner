package com.saimawzc.shipper.modle.mine.mysetment;

import com.saimawzc.shipper.dto.SearchValueDto;
import com.saimawzc.shipper.view.mysetment.AccountManageView;
import com.saimawzc.shipper.view.mysetment.MySetmentView;

import java.util.List;

public interface AccountManageModel {

    void getList(int page , List<SearchValueDto> searchValueDtos, AccountManageView view);
}
