package com.example.a2105project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.a2105project.Entity.Meal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class clientMealActivity extends AppCompatActivity {
    private List<Meal> Menu = new LinkedList<>();

    TextView clientMealname,clientMealcook,ingredientsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_meal);

        ingredientsText = (TextView)findViewById(R.id.ingredientsText);

        Intent intent = getIntent();
        String mealName = intent.getStringExtra("mealName");
        String cookEmail = intent.getStringExtra("Cook");

        Menu = (List<Meal>)getIntent().getSerializableExtra("Menu");

        System.out.println(Menu);
        System.out.println(mealName+" of "+cookEmail);

        clientMealname = (TextView) findViewById(R.id.clientMealname);
        clientMealcook = (TextView) findViewById(R.id.clientMealcook);

        clientMealname.setText(mealName);
        clientMealcook.setText(cookEmail);

        String ClientMealname = clientMealname.getText().toString();
        String ClientMealcook = clientMealcook.getText().toString();

        for (Meal i: Menu){
            if(i.getMealName().equals(ClientMealname) && i.getCookEmail().equals(ClientMealcook)){
                ingredientsText.setText(i.getIngredients());
            }
        }

    }
}