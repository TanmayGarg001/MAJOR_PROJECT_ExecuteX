package com.tanmayGarg.executex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    //Member variables
    private EditText mRecoverEmailEditTxt;
    private Button mRecoverPasswordBtn;
    private TextView mGoBackToLoginFromForgotPass;

    //Firebase authentication object for resetting password of existing user
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Objects.requireNonNull(getSupportActionBar()).hide();//hide the title(Action) bar

        mRecoverEmailEditTxt = findViewById(R.id.recoverEmailEditTxt);
        mRecoverPasswordBtn = findViewById(R.id.recoverPasswordBtn);
        mGoBackToLoginFromForgotPass = findViewById(R.id.goBackToLoginFromForgotPass);

        //creating a new instance of FirebaseAuth class
        mFirebaseAuth = FirebaseAuth.getInstance();

        //On click listener for goBackToLogin text which will then maneuver back to login page i.e. MainActivity
        mGoBackToLoginFromForgotPass.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            Intent mainIntent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
            startActivity(mainIntent);
        });

        //On click listener for recoverPasswordBtn which will then send a verification email to the user.
        mRecoverPasswordBtn.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            String emailId = mRecoverEmailEditTxt.getText().toString().trim().toLowerCase();//we get the user entered emailId
            if (!Utilities.checkValidityEmail(emailId)) {
                //display a toast if user enters an invalid email
                Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            } else {
                //recover the password for entered email
                mFirebaseAuth.sendPasswordResetEmail(emailId).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "We have sent an email to reset password for your email address", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "The email is not registered", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}