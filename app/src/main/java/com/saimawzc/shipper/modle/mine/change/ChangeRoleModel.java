package com.saimawzc.shipper.modle.mine.change;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.view.mine.change.ChangeRoleView;

/**
 * Created by Administrator on 2020/8/13.
 */

public interface ChangeRoleModel {
    void changeRole(ChangeRoleView view, BaseListener listener, int role);
}
