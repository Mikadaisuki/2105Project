package com.example.a2105project;

import static org.junit.Assert.assertEquals;

import com.example.a2105project.Entity.Order;
import com.example.a2105project.Entity.Rating;

import org.junit.Test;

public class RatingTest {
    @Test
    public void checkRatingMealName(){
        Rating aRating = new Rating();
        aRating.setMealName("Pizza");
        assertEquals("Check name of the Meal","Pizza",aRating.getMealName());
    }

    @Test
    public void checkRatingClientEmail(){
        Rating aRating = new Rating();
        aRating.setClientEmail("client1@client");
        assertEquals("Check Client Email","client1@client",aRating.getClientEmail());
    }

    @Test
    public void checkRatingCookEmail(){
        Rating aRating = new Rating();
        aRating.setCookEmail("cook1@cook");
        assertEquals("Check cook email","cook1@cook",aRating.getCookEmail());
    }

    @Test
    public void checkRatingStar(){
        Rating aRating = new Rating();
        aRating.setStar(5);
        assertEquals("Check Status ",5,aRating.getStar(),0);
    }
}
