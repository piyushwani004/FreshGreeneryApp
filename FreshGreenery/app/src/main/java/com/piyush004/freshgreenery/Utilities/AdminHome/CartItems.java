package com.piyush004.freshgreenery.Utilities.AdminHome;

public class CartItems {

    public String name;
    public String weight;
    public String rate;


    public CartItems(String name, String weight, String rate) {
        this.name = name;
        this.weight = weight;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "CartItems{" +
                "name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
