package com.saimawzc.shipper.dto.order.creatorder;

import java.io.Serializable;

public class AdressDto implements Serializable {
    private String id;
    private String allAddress;
    private String contactsName;
    private String contactsPhone;
    private String unitName;
    private String location;
    private String proName;
    private String cityName;


    private String radius;
    private String errorRange;
    private String enclosureType;
    private String addressTye;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getErrorRange() {
        return errorRange;
    }

    public void setErrorRange(String errorRange) {
        this.errorRange = errorRange;
    }

    public String getEnclosureType() {
        return enclosureType;
    }

    public void setEnclosureType(String enclosureType) {
        this.enclosureType = enclosureType;
    }

    public String getAddressTye() {
        return addressTye;
    }

    public void setAddressTye(String addressTye) {
        this.addressTye = addressTye;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAllAddress() {
        return allAddress;
    }
    public void setAllAddress(String allAddress) {
        this.allAddress = allAddress;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
