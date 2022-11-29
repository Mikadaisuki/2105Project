package com.example.a2105project.CookPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a2105project.R;

public class cookPageActivity extends AppCompatActivity {
    private Button menulist,request;
    private String cookEmail;
    // enter to the cook interface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_page);

        menulist = (Button)findViewById(R.id.menulist);
        request =(Button)findViewById(R.id.request);

        Intent intent = getIntent();
        cookEmail = intent.getStringExtra("Email");

        menulist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Email", cookEmail);
                intent.setClass(cookPageActivity.this, cookMenuActivity.class);
                startActivity(intent);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Email", cookEmail);
                intent.setClass(cookPageActivity.this, cookRequests_Activity.class);
                startActivity(intent);
            }
        });
    }
}