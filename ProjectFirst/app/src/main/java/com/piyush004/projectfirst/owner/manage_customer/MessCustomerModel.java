package com.piyush004.projectfirst.owner.manage_customer;

public class MessCustomerModel {

    private String key;

    public MessCustomerModel() {
    }

    public MessCustomerModel(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "MessCustomerModel{" +
                "key='" + key + '\'' +
                '}';
    }

}
