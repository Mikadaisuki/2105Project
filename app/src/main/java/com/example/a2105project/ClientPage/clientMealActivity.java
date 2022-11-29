package com.example.a2105project.ClientPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2105project.Entity.Meal;
import com.example.a2105project.Entity.Order;
import com.example.a2105project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class clientMealActivity extends AppCompatActivity {
    private List<Meal> Menu = new LinkedList<>();
    DatabaseReference Orderlist;
    FirebaseDatabase firebaseDatabase;

    TextView clientMealname,clientMealcook,ingredientsText;
    Button Order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_meal);

        Orderlist = firebaseDatabase.getInstance().getReference("Order");

        ingredientsText = (TextView)findViewById(R.id.ingredientsText);
        Order = (Button)findViewById(R.id.orderBtn);

        Intent intent = getIntent();
        String mealName = intent.getStringExtra("mealName");
        String cookEmail = intent.getStringExtra("Cook");
        String clientEmail = intent.getStringExtra("clientEmail");

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
                break;
            }
        }

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = Orderlist.push().getKey();
                Order order = new Order();
                order.setID(key);
                order.setClientEmail(clientEmail);
                order.setCookEmail(cookEmail);
                order.setMealName(mealName);
                Orderlist.child(key).setValue(order);
                Toast.makeText(clientMealActivity.this, "Order successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}