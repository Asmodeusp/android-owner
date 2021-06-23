package com.saimawzc.shipper.dto.identification;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/8/7.
 */

public class PersonCenterDto implements Serializable {
    private int authState;
    private String companyName;
    private String cysType;
    private String name;
    private String picture;
    private int roleType;
    private int trustFlag;//是否托运公司是否为托运公司(1.是 2.否)
    private String companyId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getTrustFlag() {
        return trustFlag;
    }

    public void setTrustFlag(int trustFlag) {
        this.trustFlag = trustFlag;
    }
    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCysType() {
        return cysType;
    }

    public void setCysType(String cysType) {
        this.cysType = cysType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }
}
