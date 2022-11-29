package com.example.a2105project.CookPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class cookRequests_Activity extends AppCompatActivity {

    private ListView cookOrder;
    private DatabaseReference ClientOrderRef;
    private FirebaseDatabase firebase;
    private List<Order> orderList = new LinkedList<>();
    private String cookEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_requests);

        cookOrder = (ListView)findViewById(R.id.cookRequestList);

        ClientOrderRef = firebase.getInstance().getReference("Order");
        Intent intent = getIntent();
        cookEmail = intent.getStringExtra("Email");
        System.out.println(cookEmail);

        ClientOrderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Map<String, String >> data = new LinkedList<>();
                orderList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Order order = child.getValue(Order.class);
                    if(order.getCookEmail().equals(cookEmail)) {
                        orderList.add(order);

                        Map<String, String> dataMap = new HashMap<>();
                        dataMap.put("ClientId", order.getClientEmail());
                        dataMap.put("CookId", order.getCookEmail());
                        dataMap.put("id", order.getID());
                        dataMap.put("mealName", order.getMealName());
                        dataMap.put("orderStatus", order.getStatus());
                        data.add(dataMap);
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.order_list,
                        new String[]{"ClientId","CookId","id","mealName","orderStatus"}, new int []{R.id.clientID,R.id.orderCook,R.id.orderID,R.id.orderMeal,R.id.orderStatus});

                cookOrder.setAdapter(adapter);

                cookOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}