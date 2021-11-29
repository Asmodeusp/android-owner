package com.saimawzc.shipper.dto.carleader;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/8/5.
 * 地区
 */

public class TaxiAreaDto implements Serializable {

    private String id;
    private String name;
    private int pid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
