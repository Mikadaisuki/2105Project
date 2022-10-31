package com.example.a2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a2105project.Entity.Account;
import com.example.a2105project.Entity.Complaint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class complaintsView_Activity extends AppCompatActivity {
    private DatabaseReference accountRef;
    private DatabaseReference complaintRef;
    private List<String> customerIDs;
    private List<String> cookIDs;

    private Button random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_view);
        random = (Button) findViewById(R.id.random);

        customerIDs = new LinkedList<>();
        cookIDs = new LinkedList<>();

        accountRef = FirebaseDatabase.getInstance().getReference("Account");
        complaintRef = FirebaseDatabase.getInstance().getReference("Complaint");

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatComplaint(5);
            }
        });

        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customerIDs.clear();
                cookIDs.clear();
                for (DataSnapshot child : snapshot.getChildren()){
                    Account account = child.getValue(Account.class);
                    switch (account.getRole()){
                        case "Client":
                            customerIDs.add(account.getEmail());
                            break;
                        case  "Cook":
                            cookIDs.add(account.getEmail());
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public  void creatComplaint(int n){
        Random random = new Random();
        for (int i = 0; i < n; i++){
            Complaint complaint = new Complaint();
            complaint.setDescription("Taste like dog shit!!");
            String customerID = customerIDs.get(random.nextInt(customerIDs.size()));
            String cookID = cookIDs.get(random.nextInt(cookIDs.size()));
            complaint.setCookID(cookID);
            complaint.setCustomerID(customerID);
            complaintRef.push().setValue(complaint);
        }
    }
}