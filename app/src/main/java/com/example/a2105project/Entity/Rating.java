package com.example.a2105project.Entity;

public class Rating {
    private float Star;
    private String CookEmail;
    private String ClientEmail;
    private String Content;
    private String Time;
    private String mealName;

    public Rating() {
    }

    public Rating(float star, String cookEmail, String clientEmail, String content) {
        Star = star;
        CookEmail = cookEmail;
        ClientEmail = clientEmail;
        Content = content;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public float getStar() {
        return Star;
    }

    public void setStar(float star) {
        Star = star;
    }

    public String getCookEmail() {
        return CookEmail;
    }

    public void setCookEmail(String cookEmail) {
        CookEmail = cookEmail;
    }

    public String getClientEmail() {
        return ClientEmail;
    }

    public void setClientEmail(String clientEmail) {
        ClientEmail = clientEmail;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "Star=" + Star +
                ", CookEmail='" + CookEmail + '\'' +
                ", ClientEmail='" + ClientEmail + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}
