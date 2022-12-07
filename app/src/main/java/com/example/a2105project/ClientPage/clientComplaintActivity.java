package com.example.a2105project.ClientPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a2105project.Entity.Complaint;
import com.example.a2105project.R;
import com.example.a2105project.loginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class clientComplaintActivity extends AppCompatActivity {

    private Button SubmitComp;
    private EditText EnterReason;

    private String clienEmail;
    private String cookEmail;
    private String mealName;
    private String enterReason;

    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_complaint);

        EnterReason = findViewById(R.id.EnterReason);
        SubmitComp = findViewById(R.id.SubmitComp);


        Intent intent = getIntent();
        clienEmail = intent.getStringExtra("clientEmail");
        cookEmail = intent.getStringExtra("cookEmail");
        mealName = intent.getStringExtra("mealName");

        reference = FirebaseDatabase.getInstance().getReference("Complaint/");

        EnterReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                enterReason = editable.toString();}
        });

        SubmitComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Complaint complaint = new Complaint();
                complaint.setDescription(enterReason);
                String customerID = clienEmail;
                String cookID = cookEmail;
                complaint.setCookID(cookID);
                complaint.setCustomerID(customerID);
                reference.push().setValue(complaint);
                Toast.makeText(clientComplaintActivity.this, "You have successfully filed your complaint", Toast.LENGTH_SHORT).show();

            }
        });

    }


}