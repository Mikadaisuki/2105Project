package com.example.a2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a2105project.Entity.Account;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.LinkedList;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {
    Button Loginbtn;
    Button Registerbtn;
    EditText email;
    EditText pwd;

    //Follow strings are used to get value of two Edittext
    String Email,Pwd,Role;
    boolean tempsus = false;

    RadioButton ClientRadio;
    RadioButton CookRadio;
    RadioButton AdminRadio;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase ;
    List<Account> accounts = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Loginbtn = (Button) findViewById(R.id.Login);
        Registerbtn = (Button) findViewById(R.id.Register);
        email = (EditText) findViewById(R.id.Emailtext);
        pwd = (EditText) findViewById(R.id.Pwdtext);
        ClientRadio = (RadioButton) findViewById(R.id.ClientChose);
        CookRadio = (RadioButton) findViewById(R.id.CookChose);
        AdminRadio = (RadioButton) findViewById(R.id.AdminChose);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getInstance().getReference("Account");

        //Email listener
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                Email = s.toString();
                System.out.println(Email);
            }
        });

        //Password listener
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                Pwd = s.toString();
                System.out.println(Pwd);
            }
        });

        //Radio buttons listener
        ClientRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Role = "Client";
                System.out.println(Role);
            }
        });
        CookRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Role = "Cook";
                System.out.println(Role);
            }
        });
        AdminRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Role = "Admin";
                System.out.println(Role);
            }
        });

        //Firebase Value listener
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accounts.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Account account = child.getValue(Account.class);
                    accounts.add(account);
                }
                System.out.println(accounts);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //Register method
    public void register(View view){
        if(checkRegisterInputsValid()){
            reference = FirebaseDatabase.getInstance().getReference("Account");
            Account account = new Account(Role, Email, Pwd);
            account.setID(Email);
            String key = reference.push().getKey();
            System.out.println(key);
            key = Email;
            reference.child(key).setValue(account);
            Toast.makeText(loginActivity.this, "Account created, please login", Toast.LENGTH_SHORT).show();
            System.out.println(accounts);
        }
    }

    //Login method
    public void login(View view) {
        if (checkLoginInputsValid()) {
            Intent intent = new Intent();
            if(tempsus){
                intent.setClass(loginActivity.this, tempSuspendActivity.class);}
            if(Role == "Client"){
            intent.setClass(loginActivity.this, clientPageActivity.class);}
            if(Role == "Cook"){
                intent.setClass(loginActivity.this, cookPageActivity.class);}
            if(Role == "Admin"){
                intent.setClass(loginActivity.this, adminPageActivity.class);}
            startActivity(intent);
        }
    }

    //Check if Register Inputs Valid
    private boolean checkRegisterInputsValid(){
        Account tempAccount = new Account(Role, Email, Pwd);
        if( Email == null || Email.equals("")){
            Toast.makeText(loginActivity.this, "Email can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if( Pwd == null || Pwd.equals("")){
            Toast.makeText(loginActivity.this, "Password can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if( Role == null || Role.equals("Admin")){
            Toast.makeText(loginActivity.this, "Role error", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (Account i: accounts) {
            System.out.println(i);
            if ( tempAccount.getEmail().equals(i.getEmail())) {
                Toast.makeText(loginActivity.this, "Account has been created,please login", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    //Check if login Inputs Valid
    private boolean checkLoginInputsValid() {
        Account tempAccount = new Account(Role, Email, Pwd);
        System.out.println(tempAccount);
        System.out.println(accounts);
        if (Email == null || Email.equals("")) {
            Toast.makeText(loginActivity.this, "Email can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Pwd == null || Pwd.equals("")) {
            Toast.makeText(loginActivity.this, "Password can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        for (Account i: accounts) {
            System.out.println(i);
            boolean equals = tempAccount.toString().equals(i.toString());
            boolean mailEqual = tempAccount.getEmail().equals(i.getEmail());
            System.out.println("mailequai?"+ mailEqual);
            System.out.println("allequal?"+equals);
            if ( equals == false ) {
                if(mailEqual && i.getStatus().equals("tempFalse")){
                Toast.makeText(loginActivity.this, "Your account is temporally suspend", Toast.LENGTH_SHORT).show();
                tempsus = true;
                return true;
                }
                if(mailEqual && i.getStatus().equals("False")) {
                Toast.makeText(loginActivity.this, "You are blocked", Toast.LENGTH_SHORT).show();
                return false;
                }
            }
            if ( equals && i.getStatus() != "tempFalse" && i.getStatus() != "False") {
                Toast.makeText(loginActivity.this, "Login", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        Toast.makeText(loginActivity.this, "Invalid input, or you are blocked", Toast.LENGTH_SHORT).show();
        return false;
    }
}