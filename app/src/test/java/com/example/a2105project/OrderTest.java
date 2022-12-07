package com.example.a2105project;

import static org.junit.Assert.assertEquals;

import com.example.a2105project.Entity.Meal;
import com.example.a2105project.Entity.Order;

import org.junit.Test;

public class OrderTest {
    @Test
    public void checkMealName(){
        Order aOrder = new Order();
        aOrder.setMealName("Pizza");
        assertEquals("Check name of the Meal","Pizza",aOrder.getMealName());
    }

    @Test
    public void checkClientEmail(){
        Order aOrder = new Order();
        aOrder.setClientEmail("client1@client");
        assertEquals("Check Client Email","client1@client",aOrder.getClientEmail());
    }

    @Test
    public void checkCookEmail(){
        Order aOrder = new Order();
        aOrder.setCookEmail("cook1@cook");
        assertEquals("Check cook email","cook1@cook",aOrder.getCookEmail());
    }

    @Test
    public void checkStatus(){
        Order aOrder = new Order();
        aOrder.setStatus("Processing");
        assertEquals("Check Status ","Processing",aOrder.getStatus());
    }
}
