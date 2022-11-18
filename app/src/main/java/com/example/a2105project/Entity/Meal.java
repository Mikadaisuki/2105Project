package com.example.a2105project.Entity;

public class Meal {

    private String mealName;
    private String cookEmail;
    private int amount;
    private String price;
    private String ingredients;


    public Meal() {
    }

    public Meal(String mealName) {
        this.mealName = mealName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Meal{" + ", mealName='" + mealName + '\'' +
                ", cookEmail='" + cookEmail + '\'' +
                '}';
    }
}
