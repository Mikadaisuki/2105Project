package com.example.a2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a2105project.Entity.Account;
import com.example.a2105project.Entity.Complaint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class reviewsActivity extends AppCompatActivity {

    private DatabaseReference complaintRef;
    private DatabaseReference accountRef;
    private List<Complaint> complaints;
    private List<Account> accounts;
    //Follow string is used to get value of transport data
    private String comID, cookEmail;

    private TextView customer, cook, comText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_detail);

        complaints = new LinkedList<>();
        accounts = new LinkedList<>();

        customer = (TextView)findViewById(R.id.ClientDetail);
        cook = (TextView)findViewById(R.id.CookDetail);
        comText = (TextView)findViewById(R.id.ComText);

        complaintRef = FirebaseDatabase.getInstance().getReference("Complaint");
        accountRef = FirebaseDatabase.getInstance().getReference("Account");

        //get the transport data
        Intent intent = getIntent();
        comID = intent.getStringExtra("ID");
        String Client = intent.getStringExtra("Client");
        String Cook = intent.getStringExtra("Cook");
        cookEmail = intent.getStringExtra("Cook");

        //set transport data in layout
        customer.setText(Client);
        cook.setText(Cook);



        //use the ID to find specific complaint
        complaintRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    Complaint complaint = child.getValue(Complaint.class);
                    complaint.setID(child.getKey());
                    complaints.add(complaint);
                    if(complaint.getID().equals(comID)){
                        comText.setText(complaint.getDescription());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    //dismiss the complaints
    public void dismiss(View view){
        complaintRef.child(comID).removeValue();
        finish();
    }
    //suspend the cook
    public void suspend(View view){
        accountRef.child(cookEmail).child("status").setValue("False");
        finish();
    }
}