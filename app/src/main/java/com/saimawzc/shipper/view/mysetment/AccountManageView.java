package com.saimawzc.shipper.view.mysetment;

import com.saimawzc.shipper.dto.myselment.AccountManageDto;
import com.saimawzc.shipper.dto.myselment.AccountQueryPageDto;
import com.saimawzc.shipper.view.BaseView;

import java.util.List;

public interface AccountManageView extends BaseView {
    void getData(AccountQueryPageDto dtos);
    void stopRefresh();
}
