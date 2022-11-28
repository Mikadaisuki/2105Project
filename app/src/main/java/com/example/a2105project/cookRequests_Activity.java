package com.example.a2105project;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2105project.Entity.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class cookRequests_Activity extends AppCompatActivity {

    private ListView clientOrder;
    private DatabaseReference ClientOrderRef;
    private FirebaseDatabase firebase;
    private List<Order> orderList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_requests);

        clientOrder = (ListView)findViewById(R.id.clientRequestList);

        ClientOrderRef = firebase.getInstance().getReference("Order");

        ClientOrderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Map<String, String >> data = new LinkedList<>();
                orderList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Order order = child.getValue(Order.class);
                    orderList.add(order);

                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("ClientId",order.getClientEmail());
                    dataMap.put("CookId",order.getCookEmail());
                    dataMap.put("id",order.getID());
                    dataMap.put("mealName",order.getMealName());
                    data.add(dataMap);
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.order_list,
                        new String[]{"ClientId","CookId","id","mealName"}, new int []{R.id.clientID,R.id.orderCook,R.id.orderID,R.id.orderMeal});

                clientOrder.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}