package com.piyush004.freshgreenery.Utilities.AdminHome;

public class HomeModel {

    private String Name;
    private String Date;
    private String Price;
    private String Quantity;
    private String TotalQuantity;
    private String TotalWeight;
    private String ImgURL;
    private String ID;
    private double TotalCartPrice;

    //History Cart Item Recy..

    private String CartItemName;
    private String CartItemweight;
    private String CartItemRate;

    public HomeModel() {

    }

    public HomeModel(String name, String price, String quantity, String id) {
        Name = name;
        Price = price;
        Quantity = quantity;
        ID = id;
    }

    public HomeModel(String name, String date, String price, String quantity, String imgURL, String id, String TotQuant, String TotWeight) {
        Name = name;
        Date = date;
        Price = price;
        Quantity = quantity;
        ImgURL = imgURL;
        ID = id;
        TotalQuantity = TotQuant;
        TotalWeight = TotWeight;
    }

    public HomeModel(String cartItemName, String cartItemweight, String cartItemRate) {
        CartItemName = cartItemName;
        CartItemweight = cartItemweight;
        CartItemRate = cartItemRate;
    }

    public String getTotalWeight() {
        return TotalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        TotalWeight = totalWeight;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        TotalQuantity = totalQuantity;
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


    public double getTotalCartPrice() {
        return TotalCartPrice;
    }

    public void setTotalCartPrice(double totalCartPrice) {
        TotalCartPrice = totalCartPrice;
    }

    public String getCartItemName() {
        return CartItemName;
    }

    public void setCartItemName(String cartItemName) {
        CartItemName = cartItemName;
    }

    public String getCartItemweight() {
        return CartItemweight;
    }

    public void setCartItemweight(String cartItemweight) {
        CartItemweight = cartItemweight;
    }

    public String getCartItemRate() {
        return CartItemRate;
    }

    public void setCartItemRate(String cartItemRate) {
        CartItemRate = cartItemRate;
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
