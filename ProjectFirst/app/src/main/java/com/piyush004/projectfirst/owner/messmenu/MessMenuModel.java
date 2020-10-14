package com.piyush004.projectfirst.owner.messmenu;

public class MessMenuModel {

    private String messMenuName;
    private String messMenuQuantity;
    private String messMenuPrice;


    public MessMenuModel() {

    }

    public MessMenuModel(String messMenuName, String messMenuQuantity, String messMenuPrice) {
        this.messMenuName = messMenuName;
        this.messMenuQuantity = messMenuQuantity;
        this.messMenuPrice = messMenuPrice;
    }

    public String getMessMenuName() {
        return messMenuName;
    }

    public void setMessMenuName(String messMenuName) {
        this.messMenuName = messMenuName;
    }

    public String getMessMenuQuantity() {
        return messMenuQuantity;
    }

    public void setMessMenuQuantity(String messMenuQuantity) {
        this.messMenuQuantity = messMenuQuantity;
    }

    public String getMessMenuPrice() {
        return messMenuPrice;
    }

    public void setMessMenuPrice(String messMenuPrice) {
        this.messMenuPrice = messMenuPrice;
    }
}
