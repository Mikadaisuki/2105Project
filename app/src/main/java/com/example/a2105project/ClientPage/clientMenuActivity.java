package com.example.a2105project.ClientPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2105project.Entity.Meal;
import com.example.a2105project.R;
import com.example.a2105project.loginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class clientMenuActivity extends AppCompatActivity {
    private DatabaseReference clientMenuRef;
    private DatabaseReference MenuRef;

    private Button refresh;
    private Button searchBtn;
    private EditText searchField;

    private String ClientEmail;
    private ListView clientMenu;

    private String searchContent;

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
        searchBtn = (Button)findViewById(R.id.searchBtn);
        searchField = (EditText)findViewById(R.id.searchField);

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

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                searchContent = editable.toString();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu.clear();
                List<Map<String, String>> data = new LinkedList<>();
                for(String i: clientEmail) {
                    MenuRef = FirebaseDatabase.getInstance().getReference("Menu/" + i);

                    MenuRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                Meal meal = child.getValue(Meal.class);
                                if(meal.getMealName().equals(searchContent)){
                                    Menu.add(meal);

                                Map<String, String> dataMap = new HashMap<>();
                                dataMap.put("MealName", meal.getMealName());
                                dataMap.put("MealAmount", "x"+meal.getAmount()+" each"+meal.getPrice()+"$");
                                dataMap.put("cookEmail", meal.getCookEmail());
                                data.add(dataMap);
                                }else{
                                    Toast.makeText(clientMenuActivity.this, "No such item founded", Toast.LENGTH_SHORT).show();
                                }
                                System.out.println(Menu);
                                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.menu_list,
                                        new String[]{"MealName", "MealAmount", "cookEmail"}, new int[]{R.id.Name, R.id.Amount, R.id.cookEmail});
                                clientMenu.setAdapter(adapter);

                                clientMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Intent intent = new Intent();
                                        Bundle bundle = new Bundle();
                                        intent.putExtra("Menu", (Serializable) Menu);
                                        TextView mealName = (TextView) view.findViewById(R.id.Name);
                                        TextView Cook = (TextView) view.findViewById(R.id.cookEmail);
                                        intent.putExtra("mealName",mealName.getText());
                                        intent.putExtra("Cook", Cook.getText());
                                        intent.putExtra("clientEmail", ClientEmail);

                                        intent.setClass(clientMenuActivity.this, clientMealActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu.clear();
                List<Map<String, String>> data = new LinkedList<>();
                for(String i: clientEmail) {
                    MenuRef = FirebaseDatabase.getInstance().getReference("Menu/" + i);
                    MenuRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                Meal meal = child.getValue(Meal.class);
                                Menu.add(meal);

                                Map<String, String> dataMap = new HashMap<>();
                                dataMap.put("MealName", meal.getMealName());
                                dataMap.put("MealAmount", "x"+meal.getAmount()+" each"+meal.getPrice()+"$");
                                dataMap.put("cookEmail", meal.getCookEmail());
                                data.add(dataMap);
                            }
                            System.out.println(Menu);
                            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.menu_list,
                                    new String[]{"MealName", "MealAmount", "cookEmail"}, new int[]{R.id.Name, R.id.Amount, R.id.cookEmail});
                            clientMenu.setAdapter(adapter);

                            clientMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    intent.putExtra("Menu", (Serializable) Menu);
                                    TextView mealName = (TextView) view.findViewById(R.id.Name);
                                    TextView Cook = (TextView) view.findViewById(R.id.cookEmail);
                                    intent.putExtra("mealName",mealName.getText());
                                    intent.putExtra("Cook", Cook.getText());
                                    intent.putExtra("clientEmail", ClientEmail);

                                    intent.setClass(clientMenuActivity.this, clientMealActivity.class);
                                    startActivity(intent);
                                }
                            });

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