package com.example.a2105project.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2105project.Components.MyRatingSimpleAdapter;
import com.example.a2105project.Entity.Meal;
import com.example.a2105project.Entity.Order;
import com.example.a2105project.Entity.Rating;
import com.example.a2105project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class clientMealActivity extends AppCompatActivity {
    private List<Rating> Ratings = new LinkedList<>();
    private List<Meal> Menu = new LinkedList<>();
    private DatabaseReference Orderlist;
    private DatabaseReference RatingsList;
    private FirebaseDatabase firebaseDatabase;
    private float total;
    private float totalCount;

    private TextView clientMealname,clientMealcook,ingredientsText;

    private ListView rateList;
    private Button Order;
    private RatingBar totalStar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_meal);



        Intent intent = getIntent();
        String mealName = intent.getStringExtra("mealName");
        String cookEmail = intent.getStringExtra("Cook");
        String clientEmail = intent.getStringExtra("clientEmail");

        RatingsList = firebaseDatabase.getInstance().getReference("Ratings/"+cookEmail+"/"+mealName);
        Orderlist = firebaseDatabase.getInstance().getReference("Order");
        rateList = findViewById(R.id.rateList);

        totalStar = (RatingBar)findViewById(R.id.totalStar);
        ingredientsText = (TextView)findViewById(R.id.ingredientsText);

        Order = (Button)findViewById(R.id.orderBtn);

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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String time = simpleDateFormat.format(date);

                String key = Orderlist.push().getKey();
                Order order = new Order();
                order.setID(key);
                order.setTime(time);
                order.setClientEmail(clientEmail);
                order.setCookEmail(cookEmail);
                order.setMealName(mealName);
                Orderlist.child(key).setValue(order);
                Toast.makeText(clientMealActivity.this, "Order successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        RatingsList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ratings.clear();
                List<Rating> data = new LinkedList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Rating rating = child.getValue(Rating.class);
                    Ratings.add(rating);
                }

                for(Rating i : Ratings){
                    total = total+i.getStar();
                }
                totalCount = (float) Ratings.size();

                rateList.setAdapter(new MyRatingSimpleAdapter(clientMealActivity.this,Ratings));

                totalStar.setRating(total/totalCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println(total+" "+totalCount);

    }

}

