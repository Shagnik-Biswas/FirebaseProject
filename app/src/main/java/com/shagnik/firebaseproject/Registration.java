package com.shagnik.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    EditText regemail, regpassword;
    Button regbtn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regemail = findViewById(R.id.regemail);
        regpassword = findViewById(R.id.regpassword);
        regbtn = findViewById(R.id.regbtn);

        auth = FirebaseAuth.getInstance();

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regemail.getText().toString().trim();
                String password = regpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Registration.this,"Empty Credentials",Toast.LENGTH_SHORT).show();

                }else if (password.length()<6) {
                    Toast.makeText(Registration.this,"password Too Short",Toast.LENGTH_SHORT).show();

                }
                else {
                   registerUser(email,password);
                }

            }


        });

    }

    private void registerUser(String email, String password) {
        


        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Registration.this,"Regisration user successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Registration.this,MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Registration.this,"Registration Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Registration.this,MainActivity.class));
        finish();
    }
}