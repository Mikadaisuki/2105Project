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


    private ListView cookMenu;
    private List<Meal> menu = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_menu);

        Intent intent = getIntent();
        CookEmail = intent.getStringExtra("Email");

        createBtn = (Button)findViewById(R.id.createBtn);
        meal_name = (EditText)findViewById(R.id.meal_name);
        meal_amount = (EditText)findViewById(R.id.meal_amount);

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
                Meal meal = new Meal(Meal_name);
                meal.setCookEmail(CookEmail);
                String key = cookMenuRef.push().getKey();
                cookMenuRef.child(Meal_name).setValue(meal);
            }
        });



        cookMenuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}