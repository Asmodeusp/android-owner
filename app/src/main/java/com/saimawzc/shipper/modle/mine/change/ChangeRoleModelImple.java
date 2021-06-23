package com.saimawzc.shipper.modle.mine.change;

import android.app.Activity;
import android.util.Log;

import com.saimawzc.shipper.base.BaseListener;
import com.saimawzc.shipper.dto.login.UserInfoDto;
import com.saimawzc.shipper.modle.BasEModeImple;
import com.saimawzc.shipper.view.mine.change.ChangeRoleView;
import com.saimawzc.shipper.weight.utils.hawk.Hawk;
import com.saimawzc.shipper.weight.utils.http.CallBack;
import com.saimawzc.shipper.weight.utils.http.Http;
import com.saimawzc.shipper.weight.utils.preference.PreferenceKey;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2020/8/13.
 */

public class ChangeRoleModelImple extends BasEModeImple implements ChangeRoleModel {

    @Override
    public void changeRole(final ChangeRoleView view, final BaseListener listener, int role) {
        view.showLoading();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("role",role+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.changeRole(body).enqueue(new CallBack<UserInfoDto>() {
            @Override
            public void success(UserInfoDto response) {
                view.dissLoading();
                Hawk.remove(PreferenceKey.ID);
                Hawk.remove(PreferenceKey.USER_INFO);
                Hawk.put(PreferenceKey.ID,response.getToken());
                Hawk.put(PreferenceKey.USER_INFO,response);
               UserInfoDto userInfoDto= (UserInfoDto)Hawk.get(PreferenceKey.USER_INFO);

                ((Activity)view.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Http.initHttp(view.getContext());
                    }
                });

                listener.successful();
            }

            @Override
            public void fail(String code, String message) {
                listener.onFail(message);
                view.dissLoading();
            }
        });
    }
}
