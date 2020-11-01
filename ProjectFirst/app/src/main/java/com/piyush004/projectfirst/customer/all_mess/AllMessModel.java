package com.piyush004.projectfirst.customer.all_mess;

public class AllMessModel {

    private String title;
    private String address;
    private String mobile;
    private String city;
    private String img;

    public AllMessModel() {
    }


    public AllMessModel(String title, String address, String mobile, String city , String img) {
        this.title = title;
        this.address = address;
        this.mobile = mobile;
        this.city = city;
        this.img = img;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
