package com.example.a2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a2105project.Entity.Complaint;
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

public class cookMenuActivity extends AppCompatActivity {
    private DatabaseReference cookMenuRef;
    private String CookEmail,Meal_name,Meal_amount;

    private Button createBtn;
    private EditText meal_name,meal_amount;


    private ListView cookMenuList;
    private List<Meal> Menu = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_menu);

        Intent intent = getIntent();
        CookEmail = intent.getStringExtra("Email");

        createBtn = (Button)findViewById(R.id.createBtn);
        meal_name = (EditText)findViewById(R.id.meal_name);
        meal_amount = (EditText)findViewById(R.id.meal_amount);
        cookMenuList = (ListView)findViewById(R.id.cookMenuList);

        cookMenuRef = FirebaseDatabase.getInstance().getReference("Menu/"+CookEmail);

        meal_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                Meal_name = editable.toString();}
        });

        meal_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                Meal_amount = editable.toString();}
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j = Integer.parseInt(Meal_amount);
                Meal meal = new Meal(Meal_name);
                meal.setCookEmail(CookEmail);
                meal.setAmount(j);
                String key = cookMenuRef.push().getKey();
                for(Meal i: Menu){
                    if(meal.getMealName().equals(i.getMealName())){
                        Map<String, Object> Upamount = new HashMap<>();
                        Upamount.put("amount",i.getAmount()+j);
                        cookMenuRef = FirebaseDatabase.getInstance().getReference("Menu/"+CookEmail+"/"+Meal_name);
                        cookMenuRef.updateChildren(Upamount);
                        cookMenuRef = FirebaseDatabase.getInstance().getReference("Menu/"+CookEmail);
                        break;
                    }else{
                        cookMenuRef.child(Meal_name).setValue(meal);
                    }
                }

            }
        });



        cookMenuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Menu.clear();
                List<Map<String, String >> data = new LinkedList<>();
                for(DataSnapshot child : snapshot.getChildren()){
                    Meal meal = child.getValue(Meal.class);
                    Menu.add(meal);

                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("MealName",meal.getMealName());
                    dataMap.put("MealAmount","x"+meal.getAmount());
                    data.add(dataMap);
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.cook_menu_list,
                        new String[]{"MealName","MealAmount"}, new int []{R.id.Name,R.id.Amount});
                cookMenuList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}