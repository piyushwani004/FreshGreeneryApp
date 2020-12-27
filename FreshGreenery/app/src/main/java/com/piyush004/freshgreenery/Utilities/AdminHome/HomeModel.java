package com.piyush004.freshgreenery.Utilities.AdminHome;

public class HomeModel {

    private String Name;
    private String Date;
    private String Price;
    private String Quantity;
    private String ImgURL;
    private String ID;

    public HomeModel() {

    }

    public HomeModel(String name, String price, String quantity, String imgURL) {
        Name = name;
        Price = price;
        Quantity = quantity;
        ImgURL = imgURL;
    }

    public HomeModel(String name, String date, String price, String quantity, String imgURL, String id) {
        Name = name;
        Date = date;
        Price = price;
        Quantity = quantity;
        ImgURL = imgURL;
        ID = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getImgURL() {
        return ImgURL;
    }

    public void setImgURL(String imgURL) {
        ImgURL = imgURL;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "HomeModel{" +
                "Name='" + Name + '\'' +
                ", Date='" + Date + '\'' +
                ", Price='" + Price + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", ImgURL='" + ImgURL + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }
}
