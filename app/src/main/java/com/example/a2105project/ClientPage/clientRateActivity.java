package com.example.a2105project.ClientPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2105project.Entity.Rating;
import com.example.a2105project.R;
import com.example.a2105project.loginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class clientRateActivity extends AppCompatActivity {

    private RatingBar starRate;
    private TextView CookEmail,MealName;
    private Button subBtn;
    private EditText rateContent;

    private String clienEmail;
    private String cookEmail;
    private String mealName;
    private String content;
    private float star;

    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_rate);

        rateContent = findViewById(R.id.rateContent);
        CookEmail = findViewById(R.id.rateCookEmail);
        MealName = findViewById(R.id.rateMealName);
        subBtn = findViewById(R.id.subBtn);

        starRate = findViewById(R.id.starBar);

        Intent intent = getIntent();
        clienEmail = intent.getStringExtra("clientEmail");
        cookEmail = intent.getStringExtra("cookEmail");
        mealName = intent.getStringExtra("mealName");

        System.out.println(clienEmail+cookEmail+mealName);

        MealName.setText(mealName);
        CookEmail.setText(cookEmail);

        reference = FirebaseDatabase.getInstance().getReference("Ratings/"+cookEmail+"/"+mealName);

        starRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                star = starRate.getRating();
            }
        });

        rateContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                content = editable.toString();
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String time = simpleDateFormat.format(date);

                Rating rating = new Rating();
                rating.setClientEmail(clienEmail);
                rating.setCookEmail(cookEmail);
                rating.setContent(content);
                rating.setStar(star);
                rating.setTime(time);
                String key = reference.push().getKey();
                key = time;
                reference.child(time).setValue(rating);
                Toast.makeText(clientRateActivity.this, "Thank you!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}