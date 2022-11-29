package com.example.a2105project.AdminPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2105project.Entity.Account;
import com.example.a2105project.Entity.Complaint;
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
import java.util.Random;

public class complaintsView_Activity extends AppCompatActivity {

    private DatabaseReference accountRef;
    private DatabaseReference complaintRef;

    private List<String> customerIDs;
    private List<String> cookIDs;
    private List<Complaint> complaints;
    private List<Account> accounts;

    private ListView listView;

    private Button random;
    //definition
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_view);
        random = (Button) findViewById(R.id.random);
        listView = (ListView)findViewById(R.id.listview);

        System.out.println(listView);

        customerIDs = new LinkedList<>();
        cookIDs = new LinkedList<>();
        complaints = new LinkedList<>();
        accounts = new LinkedList<>();

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
                accounts.clear();
                for (DataSnapshot child : snapshot.getChildren()){
                    Account account = child.getValue(Account.class);
                    accounts.add(account);
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
                    complaint.setID(child.getKey());
                    complaints.add(complaint);

                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("id",complaint.getID());
                    dataMap.put("Client",complaint.getCustomerID());
                    dataMap.put("Cook",complaint.getCookID());
                    data.add(dataMap);
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.complain_list_layout,
                        new String[]{"id","Client","Cook"}, new int []{R.id.comIDtext,R.id.Client, R.id.Cook});
                System.out.println(adapter);
                System.out.println(data);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView comIDtext = (TextView) view.findViewById(R.id.comIDtext);
                        TextView Client = (TextView) view.findViewById(R.id.Client);
                        TextView Cook = (TextView) view.findViewById(R.id.Cook);
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), reviewsActivity.class);
                        intent.putExtra("ID",comIDtext.getText());
                        intent.putExtra("Client", Client.getText());
                        intent.putExtra("Cook", Cook.getText());
                        startActivity(intent);
                    }
                });
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