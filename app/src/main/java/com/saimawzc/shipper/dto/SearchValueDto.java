package com.saimawzc.shipper.dto;

import java.io.Serializable;

public class SearchValueDto implements Serializable {
    private String searchName;
    private String getSearchValue;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getGetSearchValue() {
        return getSearchValue;
    }

    public void setGetSearchValue(String getSearchValue) {
        this.getSearchValue = getSearchValue;
    }
}
