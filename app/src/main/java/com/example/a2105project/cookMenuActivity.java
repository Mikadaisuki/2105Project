package com.example.a2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class  cookMenuActivity extends AppCompatActivity {
    private DatabaseReference cookMenuRef;
    private String CookEmail,Meal_name,Meal_amount,Meal_ingredients,Meal_price;

    private Button createBtn;
    private EditText meal_name,meal_amount,meal_ingredients,meal_price;

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
        meal_ingredients = (EditText)findViewById(R.id.meal_ingredients);
        meal_price = (EditText)findViewById(R.id.meal_price);
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

        meal_ingredients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                Meal_ingredients = editable.toString();
            }
        });

        meal_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                Meal_price = editable.toString();
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("reach0");
                if (Meal_name != null && Meal_amount != null && Meal_price != null && Meal_ingredients != null){
                    System.out.println("reach1");
                    int j = Integer.parseInt(Meal_amount);
                    Meal meal = new Meal(Meal_name);
                    meal.setCookEmail(CookEmail);
                    meal.setAmount(j);
                    meal.setIngredients(Meal_ingredients);
                    meal.setPrice(Meal_price);
                    if(Menu.isEmpty()){
                        cookMenuRef.child(Meal_name).setValue(meal);
                    }
                    for (Meal i : Menu) {
                        System.out.println("for loop");
                        if (meal.getMealName().equals(i.getMealName())) {
                            System.out.println("reach2");
                            Map<String, Object> Upamount = new HashMap<>();
                            Upamount.put("amount", i.getAmount() + j);
                            cookMenuRef = FirebaseDatabase.getInstance().getReference("Menu/" + CookEmail + "/" + Meal_name);
                            cookMenuRef.updateChildren(Upamount);
                            cookMenuRef = FirebaseDatabase.getInstance().getReference("Menu/" + CookEmail);
                            break;
                        }else{
                            System.out.println("reach3");
                            cookMenuRef.child(Meal_name).setValue(meal);
                        }
                    }
                }else{
                    Toast.makeText(cookMenuActivity.this, "Please enter a valid input", Toast.LENGTH_SHORT).show();
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
                    System.out.println(Menu);

                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("MealName",meal.getMealName());
                    dataMap.put("MealAmount","x"+meal.getAmount()+" each"+meal.getPrice()+"$");
                    dataMap.put("cookEmail",meal.getCookEmail());
                    data.add(dataMap);
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.menu_list,
                        new String[]{"MealName","MealAmount","cookEmail"}, new int []{R.id.Name,R.id.Amount,R.id.cookEmail});
                cookMenuList.setAdapter(adapter);

                for(Meal i : Menu){
                    if (i.getAmount()==0){
                        cookMenuRef.child(i.getMealName()).removeValue();
                    }
                }

                cookMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView MealName = (TextView) view.findViewById(R.id.Name);
                        String mealname = MealName.getText().toString();
                        new AlertDialog.Builder(
                                cookMenuActivity.this)
                                .setTitle("Delete this meal?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        cookMenuRef.child(mealname).removeValue();
                                    }
                                })
                                .setNegativeButton("No",null)
                                .show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

}