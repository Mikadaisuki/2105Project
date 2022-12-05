package com.example.a2105project.Entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Meal implements Serializable{

    private String mealName;
    private String cookEmail;
    private int amount;
    private String price;
    private String ingredients;
    private List<Rating> Ratings= new LinkedList<>();


    public Meal() {
    }

    public Meal(String mealName, String cookEmail, int amount, String price, String ingredients, List<Rating> ratings) {
        this.mealName = mealName;
        this.cookEmail = cookEmail;
        this.amount = amount;
        this.price = price;
        this.ingredients = ingredients;
        Ratings = ratings;
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

    public List<Rating> getRatings() {
        return Ratings;
    }

    public void setRatings(List<Rating> ratings) {
        Ratings = ratings;
    }

    public void addRating(Rating rating){
        Ratings.add(rating);
    }

    public void deletRating(Rating rating){
        Ratings.remove(rating);
    }

    @Override
    public String toString() {
        return "Meal{" + ", mealName='" + mealName + '\'' +
                ", cookEmail='" + cookEmail + '\'' +
                '}';
    }
}
