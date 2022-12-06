package com.example.a2105project.Entity;

public class Order {
    private String status = "Processing";
    private String cookEmail;
    private String clientEmail;
    private String mealName;
    private String time;
    private String ID;


    public Order() {
    }

    public Order(String status, String cookEmail, String clientEmail, String mealName, String time, String ID) {
        this.status = status;
        this.cookEmail = cookEmail;
        this.clientEmail = clientEmail;
        this.mealName = mealName;
        this.time = time;
        this.ID = ID;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCookEmail() {
        return cookEmail;
    }

    public void setCookEmail(String cookEmail) {
        this.cookEmail = cookEmail;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Order{" +
                "status='" + status + '\'' +
                ", cookEmail='" + cookEmail + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", mealName='" + mealName + '\'' +
                ", ID='" + time + '\'' +
                '}';
    }
}
