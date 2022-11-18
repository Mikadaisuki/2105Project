package com.example.a2105project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminPageActivity extends AppCompatActivity {
    Button ComBtn;
    //go to the admin interface
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        ComBtn = (Button) findViewById(R.id.comBtn);

        ComBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(adminPageActivity.this, complaintsView_Activity.class);
                startActivity(intent);
            }
        });
    }
}