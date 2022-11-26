package com.example.a2105project;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2105project.Entity.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class unblockActivity extends AppCompatActivity {

    private ListView unblockView;

    private List<Account> accounts = new LinkedList<>();
    private DatabaseReference susAccountRef;
    private FirebaseDatabase firebaseDatabase ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unblockView = (ListView)findViewById(R.id.unblockView);

        setContentView(R.layout.activity_unblock);
        susAccountRef = firebaseDatabase.getInstance().getReference("Account");

        susAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                List<Map<String, String >> data = new LinkedList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Account account = child.getValue(Account.class);
                    if(account.getStatus().equals("tempFalse")||account.getStatus().equals("False")){
                        accounts.add(account);
                    }
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("id",account.getEmail());
                    data.add(dataMap);
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.unblock_list,
                        new String[]{"id"}, new int []{R.id.SusAccounts});
                System.out.println(accounts);
                unblockView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}