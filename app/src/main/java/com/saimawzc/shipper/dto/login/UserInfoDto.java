package com.saimawzc.shipper.dto.login;

/**
 * Created by Administrator on 2020/7/31.
 */

public class UserInfoDto {
    private String token;
    private String id;
    private String userCode;
    private String userAccount;
    private int role;//1货主 2承运商 3司机 4收货人
    private String name;
    private int authState;//0 未认证 1已认证 2 认证中 3 认证失败
    private int trustFlag;//是否为托运公司(1.是 2.否)
    public int getTrustFlag() {
        return trustFlag;
    }

    public void setTrustFlag(int trustFlag) {
        this.trustFlag = trustFlag;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
