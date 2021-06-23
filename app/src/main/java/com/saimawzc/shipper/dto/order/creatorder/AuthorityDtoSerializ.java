package com.saimawzc.shipper.dto.order.creatorder;

import org.json.JSONObject;

import java.io.Serializable;

/***
 * 组织机构
 * **/
public class AuthorityDtoSerializ implements Serializable {

    private String id;
    private String parentId;
    private String companyName;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
