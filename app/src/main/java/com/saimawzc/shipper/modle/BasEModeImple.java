package com.saimawzc.shipper.modle;

import com.saimawzc.shipper.weight.utils.api.OrderApi;
import com.saimawzc.shipper.weight.utils.api.auto.AuthApi;
import com.saimawzc.shipper.weight.utils.api.bms.BmsApi;
import com.saimawzc.shipper.weight.utils.api.mine.MineApi;
import com.saimawzc.shipper.weight.utils.http.Http;

/**
 * Created by Administrator on 2020/7/30.
 */

public class BasEModeImple {

    public AuthApi authApi= Http.http.createApi(AuthApi.class);
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    public OrderApi orderApi= Http.http.createApi(OrderApi.class);
    public BmsApi bmsApi= Http.http.createApi(BmsApi.class);
}
