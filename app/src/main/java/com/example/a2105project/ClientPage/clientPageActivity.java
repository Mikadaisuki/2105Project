package com.example.a2105project.ClientPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a2105project.R;

public class clientPageActivity extends AppCompatActivity {
    private Button menulist,orderHis;
    private String clientEmail;
    // enter to the client interface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_page);

        menulist = (Button)findViewById(R.id.client_Menu);
        orderHis =(Button)findViewById(R.id.orderList);

        Intent intent = getIntent();
        clientEmail = intent.getStringExtra("Email");

        menulist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Email", clientEmail);
                intent.setClass(clientPageActivity.this, clientMenuActivity.class);
                startActivity(intent);
            }
        });

        orderHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Email", clientEmail);
                intent.setClass(clientPageActivity.this, clientRequsts_Activity.class);
                startActivity(intent);
            }
        });
    }
}