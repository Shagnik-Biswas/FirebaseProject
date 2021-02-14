package com.shagnik.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class OTP_Activity extends AppCompatActivity {
    EditText phone1, enter_otp;
    TextView send_otp, resend_otp;
    Button button_next;
    ProgressBar progressBar;
    CountryCodePicker country_code;
    FirebaseAuth auth;
    String verification_id;
    PhoneAuthProvider.ForceResendingToken token;
    boolean verificationInProgress = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone1 = findViewById(R.id.phone1);
        enter_otp = findViewById(R.id.enter_otp);
        send_otp = findViewById(R.id.send_otp);
        resend_otp = findViewById(R.id.resend_otp);
        button_next = findViewById(R.id.button_next);
        country_code = findViewById(R.id.country_code);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verificationInProgress) {

                }
            }
        });

    }

    private void verifyAuth(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(OTP_Activity.this,"Authentication Successful",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(OTP_Activity.this,"Authentication Failed",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void requestOTP(String phone_num) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_num, 60L, TimeUnit.SECONDS, OTP_Activity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                send_otp.setVisibility(View.GONE);
                enter_otp.setVisibility(View.VISIBLE);
                verification_id=s;
                token=forceResendingToken;
                button_next.setText("Verify");
                verificationInProgress = true;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {


            }
        });
    }
}