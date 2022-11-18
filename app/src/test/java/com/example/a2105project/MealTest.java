package com.example.a2105project;

import com.example.a2105project.Entity.Meal;

import org.junit.Test;
import static org.junit.Assert.*;

public class MealTest {
    @Test
    public void checkMealName(){
        Meal aMeal = new Meal();
        aMeal.setMealName("Pizza");
        assertEquals("Check name of the Meal","Pizza",aMeal.getMealName());
    }

    @Test
    public void checkMealAmount(){
        Meal aMeal = new Meal();
        aMeal.setAmount(9);
        assertEquals("Check amount of the Meal",9,aMeal.getAmount());
    }

    @Test
    public void checkMealCookEmail(){
        Meal aMeal = new Meal();
        aMeal.setCookEmail("cook1@cook");
        assertEquals("Check cook email of the Meal","cook1@cook",aMeal.getCookEmail());
    }

    @Test
    public void checkMealtoString(){
        Meal aMeal = new Meal();
        aMeal.setIngredients("Potato");
        assertEquals("Check cook email of the Meal","Potato",aMeal.getIngredients());
    }
}
