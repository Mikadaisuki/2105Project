package com.example.a2105project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

    private ListView blockList;

    private List<Account> accounts = new LinkedList<>();

    private DatabaseReference susAccountRef;
    private DatabaseReference accountRef;
    private FirebaseDatabase firebaseDatabase ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unblock);

        susAccountRef = firebaseDatabase.getInstance().getReference("Account");

        blockList = (ListView) findViewById(R.id.blockList);

        System.out.println(blockList);

        susAccountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                List<Map<String, String >> data = new LinkedList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Account account = child.getValue(Account.class);
                    if(account.getStatus().equals("tempFalse")||account.getStatus().equals("False")){
                        accounts.add(account);

                        Map<String, String> dataMap = new HashMap<>();
                        dataMap.put("Email",account.getEmail());
                        data.add(dataMap);
                    }
                }
                System.out.println(accounts);
                System.out.println(data);
                SimpleAdapter sus = new SimpleAdapter(getApplicationContext(),data,R.layout.unblock_list,
                        new String[]{"Email"}, new int []{R.id.susEmail});
                System.out.println(sus);

                blockList.setAdapter(sus);
                blockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView userEmail = view.findViewById(R.id.susEmail);
                        String UserEmail = userEmail.getText().toString();
                        accountRef = firebaseDatabase.getInstance().getReference("Account/"+UserEmail);
                        new AlertDialog.Builder(
                                unblockActivity.this)
                                .setTitle("Unblock this user?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Map<String, Object> UpStatus = new HashMap<>();
                                        UpStatus.put("status",  "Active");
                                        accountRef.updateChildren(UpStatus);

                                    }
                                })
                                .setNegativeButton("No",null)
                                .show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}