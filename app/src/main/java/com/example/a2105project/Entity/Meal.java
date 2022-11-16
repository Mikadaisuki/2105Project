package com.example.a2105project.Entity;

public class Meal {
    private String key;
    private String mealName;
    private String cookEmail;


    public Meal() {
    }

    public Meal(String mealName) {
        this.mealName = mealName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getCookEmail() {
        return cookEmail;
    }

    public void setCookEmail(String cookEmail) {
        this.cookEmail = cookEmail;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "key='" + key + '\'' +
                ", mealName='" + mealName + '\'' +
                ", cookEmail='" + cookEmail + '\'' +
                '}';
    }
}
