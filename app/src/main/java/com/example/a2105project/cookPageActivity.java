package com.example.a2105project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class cookPageActivity extends AppCompatActivity {
    private Button menulist;
    private String cookEmail;
    //go to the cook interface

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_page);

        menulist = (Button)findViewById(R.id.menulist);

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
    }
}