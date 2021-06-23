package com.saimawzc.shipper.dto.identification;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/8/6.
 */

public class CompanyDto implements Serializable {

    private String companyName;
    private String companyCode;
    private String id;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
