package com.example.a2105project.ClientPage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2105project.Entity.Order;
import com.example.a2105project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class clientRequsts_Activity extends AppCompatActivity {

    private ListView clientOrder;
    private DatabaseReference ClientOrderRef;
    private FirebaseDatabase firebase;
    private List<Order> orderList = new LinkedList<>();
    private String clienEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_requsts);

        Intent intent = getIntent();
        clienEmail = intent.getStringExtra("Email");
        System.out.println(clienEmail);

        clientOrder = (ListView)findViewById(R.id.clientRequestList);

        ClientOrderRef = firebase.getInstance().getReference("Order");

        ClientOrderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                List<Map<String, String >> data = new LinkedList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Order order = child.getValue(Order.class);
                    if(order.getClientEmail().equals(clienEmail)) {
                        orderList.add(order);

                        Map<String, String> dataMap = new HashMap<>();
                        dataMap.put("ClientId", order.getClientEmail());
                        dataMap.put("CookId", order.getCookEmail());
                        dataMap.put("id", order.getID());
                        dataMap.put("mealName", order.getMealName());
                        dataMap.put("Status",order.getStatus());
                        dataMap.put("orderStatus", order.getStatus());
                        data.add(dataMap);
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.order_list,
                        new String[]{"ClientId","CookId","id","mealName","Status"}, new int []{R.id.clientID,R.id.orderCook,R.id.orderID,R.id.orderMeal,R.id.orderStatus});

                clientOrder.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}