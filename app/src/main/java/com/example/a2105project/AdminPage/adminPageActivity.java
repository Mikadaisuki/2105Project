package com.example.a2105project.AdminPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2105project.R;

public class adminPageActivity extends AppCompatActivity {
    Button ComBtn;
    Button SusBtn;
    // jump to the administration interface
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        ComBtn = (Button) findViewById(R.id.comBtn);
        SusBtn = (Button) findViewById(R.id.susBtn);

        ComBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(adminPageActivity.this, complaintsView_Activity.class);
                startActivity(intent);
            }
        });
        SusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(adminPageActivity.this, unblockActivity.class);
                startActivity(intent);
            }
        });
    }
}