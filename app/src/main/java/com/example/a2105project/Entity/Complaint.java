package com.example.a2105project.Entity;

public class Complaint {
    private String description;
    private String CookID;
    private String CustomerID;
    private String ID;

    public Complaint() {
    }

    public Complaint(String description, String cookID, String customerID) {
        this.description = description;
        this.CookID = cookID;
        this.CustomerID = customerID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCookID() {
        return CookID;
    }

    public void setCookID(String cookID) {
        CookID = cookID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }
}
