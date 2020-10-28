package com.piyush004.projectfirst.customer.all_mess;

public class AllMessModel {

    private String title;
    private String address;
    private String mobile;
    private String city;

    public AllMessModel() {
    }

    public AllMessModel(String title, String address, String mobile, String city) {
        this.title = title;
        this.address = address;
        this.mobile = mobile;
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getModile() {
        return mobile;
    }

    public void setModile(String modile) {
        this.mobile = modile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
