package com.piyush004.freshgreenery.Utilities.AdminHome;

public class AdminModel {

    private String order;
    private String UserName;
    private String Rate;
    private String Date;
    private String Mobile;
    private String Time;
    private String NoItems;
    private String Address;
    private String City;
    private String SocietyName;
    private String FlatNo;
    private String OrderMethod;

    private String ItemName;
    private String ItemWeight;
    private String ItemRate;

    public AdminModel(String orderId, String userName, String rate, String date, String mobile, String time, String noItems, String addres, String city, String societyName, String flatNo, String ordermethod) {
        order = orderId;
        UserName = userName;
        Rate = rate;
        Date = date;
        Mobile = mobile;
        Time = time;
        NoItems = noItems;
        Address = addres;
        City = city;
        SocietyName = societyName;
        FlatNo = flatNo;
        OrderMethod = ordermethod;
    }

    public AdminModel(String itemName, String itemWeight, String itemRate) {
        ItemName = itemName;
        ItemWeight = itemWeight;
        ItemRate = itemRate;
    }

    public AdminModel() {
    }

    public String getOrderMethod() {
        return OrderMethod;
    }

    public void setOrderMethod(String orderMethod) {
        OrderMethod = orderMethod;
    }

    public String getOrderId() {
        return order;
    }

    public void setOrderId(String orderId) {
        this.order = orderId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getNoItems() {
        return NoItems;
    }

    public void setNoItems(String noItems) {
        NoItems = noItems;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getSocietyName() {
        return SocietyName;
    }

    public void setSocietyName(String societyName) {
        SocietyName = societyName;
    }

    public String getFlatNo() {
        return FlatNo;
    }

    public void setFlatNo(String flatNo) {
        FlatNo = flatNo;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemWeight() {
        return ItemWeight;
    }

    public void setItemWeight(String itemWeight) {
        ItemWeight = itemWeight;
    }

    public String getItemRate() {
        return ItemRate;
    }

    public void setItemRate(String itemRate) {
        ItemRate = itemRate;
    }

    @Override
    public String toString() {
        return "AdminModel{" +
                "orderId='" + order + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Rate='" + Rate + '\'' +
                ", Date='" + Date + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Time='" + Time + '\'' +
                ", NoItems='" + NoItems + '\'' +
                ", Addres='" + Address + '\'' +
                ", City='" + City + '\'' +
                ", SocietyName='" + SocietyName + '\'' +
                ", FlatNo='" + FlatNo + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ItemWeight='" + ItemWeight + '\'' +
                ", ItemRate='" + ItemRate + '\'' +
                '}';
    }
}
