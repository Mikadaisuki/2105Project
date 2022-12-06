package com.example.a2105project.CookPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private DatabaseReference CookOrderRef;
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
                        dataMap.put("id", order.getTime());
                        dataMap.put("mealName", order.getMealName());
                        dataMap.put("orderStatus", order.getStatus());
                        dataMap.put("orderID", order.getID());
                        data.add(dataMap);
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.order_list,
                        new String[]{"ClientId","CookId","id","mealName","orderStatus","orderID"}, new int []{R.id.clientID,R.id.orderCook,R.id.orderTime,R.id.orderMeal,R.id.orderStatus,R.id.orderID});

                cookOrder.setAdapter(adapter);

                cookOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView orderId = view.findViewById(R.id.orderID);
                        String OrderId = orderId.getText().toString();
                        CookOrderRef = firebase.getInstance().getReference("Order/"+OrderId);
                        new AlertDialog.Builder(
                                cookRequests_Activity.this)
                                .setTitle("Change Status to Finish?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Map<String, Object> UpStatus = new HashMap<>();
                                        UpStatus.put("status",  "Finish");
                                        CookOrderRef.updateChildren(UpStatus);

                                    }
                                })
                                .setNegativeButton("No",null)
                                .show();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}