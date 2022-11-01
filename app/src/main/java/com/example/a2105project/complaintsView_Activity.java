package com.example.a2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
import java.util.Random;

public class complaintsView_Activity extends AppCompatActivity {

    private DatabaseReference accountRef;
    private DatabaseReference complaintRef;

    private List<String> customerIDs;
    private List<String> cookIDs;
    private List<Complaint> complaints;

    private ListView listView;

    private Button random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_view);
        random = (Button) findViewById(R.id.random);
        listView = (ListView)findViewById(R.id.listview);

        customerIDs = new LinkedList<>();
        cookIDs = new LinkedList<>();
        complaints = new LinkedList<>();

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
        complaintRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                List<Map<String, String >> data = new LinkedList<>();
                for(DataSnapshot child : snapshot.getChildren()){
                    Complaint complaint = child.getValue(Complaint.class);
                    complaints.add(complaint);

                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("Client",complaint.getCustomerID());
                    dataMap.put("Cook",complaint.getCookID());
                    data.add(dataMap);
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.complain_list_layout,
                        new String[]{"Client","Cook"}, new int []{R.id.Client, R.id.Cook});
                listView.setAdapter(adapter);
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