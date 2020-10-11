package com.piyush004.projectfirst.owner.messdetails;

public class MessDetailsModel {

    private String mess_name;
    private String mess_address;
    private String mess_mobile;
    private String mess_city;
    private String mess_email;
    private String mess_closed_days;

    public MessDetailsModel(String mess_name, String mess_address, String mess_mobile, String mess_city, String mess_email, String mess_closed_days) {
        this.mess_name = mess_name;
        this.mess_address = mess_address;
        this.mess_mobile = mess_mobile;
        this.mess_city = mess_city;
        this.mess_email = mess_email;
        this.mess_closed_days = mess_closed_days;
    }

    public String getMess_name() {
        return mess_name;
    }

    public void setMess_name(String mess_name) {
        this.mess_name = mess_name;
    }

    public String getMess_address() {
        return mess_address;
    }

    public void setMess_address(String mess_address) {
        this.mess_address = mess_address;
    }

    public String getMess_mobile() {
        return mess_mobile;
    }

    public void setMess_mobile(String mess_mobile) {
        this.mess_mobile = mess_mobile;
    }

    public String getMess_city() {
        return mess_city;
    }

    public void setMess_city(String mess_city) {
        this.mess_city = mess_city;
    }

    public String getMess_email() {
        return mess_email;
    }

    public void setMess_email(String mess_email) {
        this.mess_email = mess_email;
    }

    public String getMess_closed_days() {
        return mess_closed_days;
    }

    public void setMess_closed_days(String mess_closed_days) {
        this.mess_closed_days = mess_closed_days;
    }

    @Override
    public String toString() {
        return "MessDetailsModel{" +
                "mess_name='" + mess_name + '\'' +
                ", mess_address='" + mess_address + '\'' +
                ", mess_mobile='" + mess_mobile + '\'' +
                ", mess_city='" + mess_city + '\'' +
                ", mess_email='" + mess_email + '\'' +
                ", mess_closed_days='" + mess_closed_days + '\'' +
                '}';
    }


}
