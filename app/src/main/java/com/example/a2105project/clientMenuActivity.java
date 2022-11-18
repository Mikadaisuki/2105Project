package com.example.a2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a2105project.Entity.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class clientMenuActivity extends AppCompatActivity {
    private DatabaseReference clientMenuRef;
    private DatabaseReference MenuRef;

    private Button refresh;

    private String ClientEmail;
    private ListView clientMenu;

    private String temp;

    private List<Meal> Menu = new LinkedList<>();
    private List<String> clientEmail = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);

        Intent intent = getIntent();
        ClientEmail = intent.getStringExtra("Email");

        clientMenuRef = FirebaseDatabase.getInstance().getReference("Menu");
        MenuRef = FirebaseDatabase.getInstance().getReference("Menu");

        clientMenu = (ListView) findViewById(R.id.clientMenu);
        refresh = (Button) findViewById(R.id.refresh);

        clientMenuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientEmail.clear();
                List<Map<String, String >> data = new LinkedList<>();
                for(DataSnapshot child : snapshot.getChildren()){
                    String CookEmail = child.getKey();
                    clientEmail.add(CookEmail);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu.clear();
                List<Map<String, String>> data = new LinkedList<>();
                for(String i: clientEmail) {
                    System.out.println(i);
                    MenuRef = FirebaseDatabase.getInstance().getReference("Menu/" + i);
                    MenuRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                Meal meal = child.getValue(Meal.class);
                                Menu.add(meal);
                                System.out.println(Menu);

                                Map<String, String> dataMap = new HashMap<>();
                                dataMap.put("MealName", meal.getMealName());
                                dataMap.put("MealAmount", "x"+meal.getAmount()+" each"+meal.getPrice()+"$");
                                dataMap.put("cookEmail", meal.getCookEmail());
                                data.add(dataMap);
                            }
                            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.menu_list,
                                    new String[]{"MealName", "MealAmount", "cookEmail"}, new int[]{R.id.Name, R.id.Amount, R.id.cookEmail});
                            clientMenu.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}